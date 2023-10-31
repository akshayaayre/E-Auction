package com.eauction.seller.controller;

import com.eauction.seller.entity.Product;
import com.eauction.seller.exception.InvalidDeleteRequestException;
import com.eauction.seller.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/e-auction/api/v1/seller")
public class SellerController {
@Autowired
SellerService service;
@PostMapping("/addProduct")
public ResponseEntity<?> addProduct(@Valid @RequestBody Product product){
    Product p1 = service.addProduct(product);
    if(p1 != null)
        return new ResponseEntity<>(p1, HttpStatus.OK);
    else
        return new ResponseEntity<>("Product cannot be added", HttpStatus.INTERNAL_SERVER_ERROR);
}
@PutMapping("/updateProduct")
public ResponseEntity<?> updateProduct(@Valid @RequestBody Product product){
    Product p1 = service.addProduct(product);
    if(p1 != null)
        return new ResponseEntity<>(p1, HttpStatus.OK);
    else
        return new ResponseEntity<>("Product cannot be updated", HttpStatus.INTERNAL_SERVER_ERROR);
}
@DeleteMapping("/delete/{productId}")
public ResponseEntity deleteProduct(@PathVariable Integer productId) throws InvalidDeleteRequestException {
    service.deleteProduct(productId);
    return null;
}
}
