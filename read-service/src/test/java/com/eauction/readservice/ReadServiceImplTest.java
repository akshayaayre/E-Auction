package com.eauction.readservice;

import com.eauction.readservice.entity.BidDetails;
import com.eauction.readservice.entity.Product;
import com.eauction.readservice.entity.UserDetails;
import com.eauction.readservice.repository.BidDetailsRepository;
import com.eauction.readservice.repository.ProductRepository;
import com.eauction.readservice.repository.UserDetailsRepository;
import com.eauction.readservice.service.ReadServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ReadServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private BidDetailsRepository bidDetailsRepository;

    @Mock
    private UserDetailsRepository userDetailsRepository;

    @InjectMocks
    private ReadServiceImpl readService;

    @Test
    public void testGetAllProducts() {
        List<Product> productList = Collections.singletonList(new Product());
        when(productRepository.findAll()).thenReturn(productList);

        List<Product> result = readService.getAllProducts();

        assertEquals(productList, result);
    }

    @Test
    public void testGetBidFromUserOnProduct() {
        int productId = 1;
        String userEmail = "test@example.com";
        BidDetails expectedBid = new BidDetails();
        when(bidDetailsRepository.getBidFromUserOnProduct(productId, userEmail)).thenReturn(expectedBid);

        BidDetails result = readService.getBidFromUserOnProduct(productId, userEmail);

        assertEquals(expectedBid, result);
    }

    @Test
    public void testGetAllProductsForUser() {
        String userEmail = "test@example.com";
        List<Product> productList = Collections.singletonList(new Product());
        when(productRepository.findByUserEmail(userEmail)).thenReturn(productList);

        List<Product> result = readService.getAllProductsForUser(userEmail);

        assertEquals(productList, result);
    }

    @Test
    public void testGetAllBidsOnProduct() {
        int productId = 1;
        BidDetails expectedBid = new BidDetails();
        when(bidDetailsRepository.getAllBidsOnProduct(productId)).thenReturn(Collections.singletonList(expectedBid));
        when(userDetailsRepository.findById(any())).thenReturn(Optional.of(new UserDetails()));

        List<BidDetails> result = readService.getAllBidsOnProduct(productId);

        assertEquals(Collections.singletonList(expectedBid), result);
    }

    @Test
    public void testGetProductDetails() {
        int productId = 1;
        Product expectedProduct = new Product();
        when(productRepository.findById(productId)).thenReturn(Optional.of(expectedProduct));

        Product result = readService.getProductDetails(productId);

        assertEquals(expectedProduct, result);
    }
}