package com.shyam.dto;

import com.shyam.entities.ProductEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventDTO {
    
    private String eventType;
    private ProductEntity product;
}
