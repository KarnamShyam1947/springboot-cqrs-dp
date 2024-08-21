package com.shyam.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.shyam.documents.ProductDocument;

@Repository
public interface ProductRepository extends MongoRepository<ProductDocument, Integer> {
    
}
