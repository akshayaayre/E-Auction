package com.eauction.readservice.service;

import com.eauction.readservice.entity.BidDetails;
import com.eauction.readservice.entity.Product;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface ReadService {

    public List<Product> getAllProducts();
    public BidDetails getBidFromUserOnProduct(Integer productId, String userEmail);
    public List<Product> getAllProductsForUser(String email);

    public List<BidDetails> getAllBidsOnProduct(Integer productId);

    public  Product getProductDetails(Integer productId);
}
