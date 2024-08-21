package com.shyam.services;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.shyam.documents.ProductDocument;
import com.shyam.dto.EventDTO;
import com.shyam.dto.ProductEntity;
import com.shyam.repositories.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {
    
    private final ProductRepository productRepository;

    @KafkaListener(topics = "sb-cqrs-demo-topic", groupId = "sb-cqrs-demo-group")
    public void handleEvent(EventDTO eventDTO) {
        if (eventDTO.getEventType().equals("CreateProduct")) 
            addProduct(eventDTO.getProduct());
        
        if (eventDTO.getEventType().equals("UpdateProduct")) 
            updateProduct(eventDTO.getProduct());
        
        if (eventDTO.getEventType().equals("DeleteProduct")) 
            deleteProduct(eventDTO.getProduct());
        
    }

    public ProductDocument addProduct(ProductEntity dto) {
        ProductDocument product = new ProductDocument();
        BeanUtils.copyProperties(dto, product);

        return productRepository.save(product);
    }
   
    public void deleteProduct(ProductEntity dto) {
        ProductDocument product = new ProductDocument();
        BeanUtils.copyProperties(dto, product);

        productRepository.delete(product);
    }

    public ProductDocument updateProduct(ProductEntity dto) {
        ProductDocument product = new ProductDocument();
        BeanUtils.copyProperties(dto, product);

        BeanUtils.copyProperties(dto, product);

        if (dto.getDescription() != null) 
            product.setDescription(dto.getDescription());

        if (dto.getName() != null)
            product.setName(dto.getName());
        
        if (dto.getPrice() != 0.0) 
            product.setPrice(dto.getPrice());
        
        return productRepository.save(product);
    }

    public List<ProductDocument> allProducts() {
        return productRepository.findAll();
    }
    
    public ProductDocument getProduct(int id) {
        return productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
    }

}
