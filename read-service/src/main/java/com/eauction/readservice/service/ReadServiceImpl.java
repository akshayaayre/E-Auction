package com.eauction.readservice.service;

import com.eauction.readservice.entity.BidDetails;
import com.eauction.readservice.entity.Product;
import com.eauction.readservice.repository.BidDetailsRepository;
import com.eauction.readservice.repository.ProductRepository;
import com.eauction.readservice.repository.UserDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReadServiceImpl implements ReadService{

    @Autowired
    ProductRepository productRepository;

    @Autowired
    BidDetailsRepository bidDetailsRepository;

    @Autowired
    UserDetailsRepository userDetailsRepository;

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public BidDetails getBidFromUserOnProduct(Integer productId, String userEmail) {
        return bidDetailsRepository.getBidFromUserOnProduct(productId,userEmail);
    }

    @Override
    public List<Product> getAllProductsForUser(String email) {
        return productRepository.findByUserEmail(email);
    }

    @Override
    public List<BidDetails> getAllBidsOnProduct(Integer productId) {
        List<BidDetails> bidDetailsList = bidDetailsRepository.getAllBidsOnProduct(productId);

        for(BidDetails bidDetails: bidDetailsList){
            bidDetails.setUserDetails(userDetailsRepository.findById(bidDetails.getUserEmail()).get());
        }
        return bidDetailsList;
    }

    @Override
    public Product getProductDetails(Integer productId) {
        return productRepository.findById(productId).get();
    }
}
