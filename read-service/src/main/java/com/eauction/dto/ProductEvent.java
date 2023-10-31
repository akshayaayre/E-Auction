package com.eauction.dto;

import com.eauction.readservice.entity.Product;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ProductEvent {

    private String type;
    private Product product;

    public ProductEvent(){}

    @JsonCreator
    public ProductEvent(@JsonProperty("type") String type, @JsonProperty("product") Product product) {
        this.type = type;
        this.product = product;
    }

}