package com.eauction.buyer.controller;

import com.eauction.buyer.entity.BidDetails;
//import com.eauction.buyer.entity.Product;
import com.eauction.buyer.exception.InvalidBidRequestException;
import com.eauction.buyer.service.BuyerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/e-auction/api/v1/buyer")
//@CrossOrigin("*")
public class BuyerController {

    @Autowired
    BuyerServiceImpl service;

    @PostMapping("/addBidOnProduct")
    public ResponseEntity addBidOnProduct(@RequestBody BidDetails details) throws InvalidBidRequestException {
        service.addBidOnProduct(details);
        return ResponseEntity.ok(null);
    }

}
