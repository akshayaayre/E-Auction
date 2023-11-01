package com.eauction.readservice;

import com.eauction.readservice.controller.ReadController;
import com.eauction.readservice.entity.BidDetails;
import com.eauction.readservice.entity.Product;
import com.eauction.readservice.service.ReadService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ReadControllerTest {

    @Mock
    private ReadService readService;

    @InjectMocks
    private ReadController readController;

    @Test
    public void testGetAllProducts() {
        List<Product> productList = Arrays.asList(new Product(), new Product());
        when(readService.getAllProducts()).thenReturn(productList);

        ResponseEntity<List<Product>> response = readController.getAllProducts();

        assertEquals(productList, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testGetProductDetails() {
        int productId = 1;
        Product product = new Product();
        when(readService.getProductDetails(productId)).thenReturn(product);

        ResponseEntity<Product> response = readController.getProductDetails(productId);

        assertEquals(product, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testGetAllBidsOnProduct() {
        int productId = 1;
        List<BidDetails> bidDetailsList = Arrays.asList(new BidDetails(), new BidDetails());
        when(readService.getAllBidsOnProduct(productId)).thenReturn(bidDetailsList);

        ResponseEntity<List<BidDetails>> response = readController.getAllBidsOnProduct(productId);

        assertEquals(bidDetailsList, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testGetBidFromUserOnProduct() {
        int productId = 1;
        String userEmail = "test@example.com";
        BidDetails bid = new BidDetails();
        when(readService.getBidFromUserOnProduct(productId, userEmail)).thenReturn(bid);

        ResponseEntity<BidDetails> response = readController.getBidFromUserOnProduct(productId, userEmail);

        assertEquals(bid, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testGetAllProductsForUser() {
        String userEmail = "test@example.com";
        List<Product> productList = Arrays.asList(new Product(), new Product());
        when(readService.getAllProductsForUser(userEmail)).thenReturn(productList);

        List<Product> result = readController.getAllProductsForUser(userEmail);

        assertEquals(productList, result);
    }
}