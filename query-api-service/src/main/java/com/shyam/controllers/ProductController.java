package com.shyam.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shyam.documents.ProductDocument;
import com.shyam.services.ProductService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {
    
    private final ProductService productService;

    @GetMapping
    public ResponseEntity<List<ProductDocument>> allProducts() {
        return ResponseEntity.ok().body(productService.allProducts());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ProductDocument> getProduct(@PathVariable("id") int id) {
        return ResponseEntity.ok().body(productService.getProduct(id));
    }
    

}
