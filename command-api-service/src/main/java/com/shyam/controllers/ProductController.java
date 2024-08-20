package com.shyam.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shyam.dto.ProductDTO;
import com.shyam.entities.ProductEntity;
import com.shyam.services.ProductService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {
    
    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ProductEntity> addProduct(@RequestBody ProductDTO dto) {
        return ResponseEntity
                .status(HttpStatus.CREATED.value())
                .body(productService.addProduct(dto));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ProductEntity> updateProduct(
        @PathVariable("id") int id,
        @RequestBody ProductDTO dto
    ) {
        return ResponseEntity.ok().body(productService.updateProduct(id, dto));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteProduct(@PathVariable("id") int id) {
        productService.deleteProduct(id);

        return ResponseEntity.noContent().build();
    }

}
