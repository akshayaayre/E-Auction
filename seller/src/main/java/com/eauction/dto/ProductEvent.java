package com.eauction.dto;

import com.eauction.seller.entity.Product;
import lombok.Data;

@Data
public class ProductEvent {

    private String type;
    private Product product;

    public ProductEvent(){}
    public ProductEvent(String type, Product product) {
        this.type = type;
        this.product = product;
    }
}
