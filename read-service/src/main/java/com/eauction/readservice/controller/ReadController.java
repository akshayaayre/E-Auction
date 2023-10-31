package com.eauction.readservice.controller;

import com.eauction.readservice.entity.BidDetails;
import com.eauction.readservice.entity.Product;
import com.eauction.readservice.service.ReadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/e-auction/api/v1/read")
public class ReadController {

    @Autowired
    ReadService readService;

    @GetMapping("/products")
    public ResponseEntity<List<Product>> getAllProducts(){
        List<Product> productList = readService.getAllProducts();
        return new ResponseEntity<>(productList, HttpStatus.OK);
    }

    @GetMapping("/getProductDetails")
    public  ResponseEntity<Product> getProductDetails(@RequestParam Integer productId){
        Product product = readService.getProductDetails(productId);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @GetMapping("/getAllBidsOnProduct")
    public ResponseEntity<List<BidDetails>> getAllBidsOnProduct(@RequestParam Integer productId){
        List<BidDetails> bidDetailsList = readService.getAllBidsOnProduct(productId);
        return new ResponseEntity<>(bidDetailsList, HttpStatus.OK);
    }

    @GetMapping("/getBidFromUserOnProduct")
    public ResponseEntity<BidDetails> getBidFromUserOnProduct(@RequestParam Integer productId,
                                                              @RequestParam String userEmail){
        BidDetails bid = readService.getBidFromUserOnProduct(productId,userEmail);
        return new ResponseEntity<>(bid,HttpStatus.OK);
    }

    @GetMapping("/get-all-products-for-user")
    public List<Product> getAllProductsForUser(@RequestParam String email){
        return readService.getAllProductsForUser(email);
    }


}
