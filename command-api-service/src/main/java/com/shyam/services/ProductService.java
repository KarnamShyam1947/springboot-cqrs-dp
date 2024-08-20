package com.shyam.services;

import org.springframework.beans.BeanUtils;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.shyam.dto.EventDTO;
import com.shyam.dto.ProductDTO;
import com.shyam.entities.ProductEntity;
import com.shyam.repositories.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {
    
    private final ProductRepository productRepository;
    private final KafkaTemplate<String, EventDTO> kafkaTemplate;

    public ProductEntity addProduct(ProductDTO dto) {
        ProductEntity newProduct = new ProductEntity();
        BeanUtils.copyProperties(dto, newProduct);
        ProductEntity product = productRepository.save(newProduct);

        EventDTO event=new EventDTO("CreateProduct", product);
        kafkaTemplate.send("product-event-topic", event);

        return product;
    }
   
    public void deleteProduct(int id) {
        ProductEntity product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
        productRepository.delete(product);

        EventDTO event=new EventDTO("DeleteProduct", product);
        kafkaTemplate.send("product-event-topic", event);
    }

    public ProductEntity updateProduct(int id, ProductDTO dto) {
        ProductEntity product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
        BeanUtils.copyProperties(dto, product);

        if (dto.getDescription() != null) 
            product.setDescription(dto.getDescription());

        if (dto.getName() != null)
            product.setName(dto.getName());
        
        if (dto.getPrice() != 0.0) 
            product.setPrice(dto.getPrice());
        
        ProductEntity updatedProduct = productRepository.save(product);

        EventDTO event=new EventDTO("UpdateProduct", updatedProduct);
        kafkaTemplate.send("product-event-topic", event);
        return updatedProduct;
    }

}
