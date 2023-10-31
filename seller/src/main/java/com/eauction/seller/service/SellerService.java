package com.eauction.seller.service;

import com.eauction.seller.entity.Product;
import com.eauction.seller.exception.InvalidDeleteRequestException;

import java.util.List;

public interface SellerService {

    public Product addProduct(Product product);

    public List<Product> getAllProducts(String email);

    public void deleteProduct(Integer productId) throws InvalidDeleteRequestException;
}
