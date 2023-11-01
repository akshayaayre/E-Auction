package com.eauction.seller;

import com.eauction.dto.ProductEvent;
import com.eauction.seller.entity.Product;
import com.eauction.seller.exception.InvalidDeleteRequestException;
import com.eauction.seller.repository.ProductRepo;
import com.eauction.seller.service.SellerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
public class SellerServiceImplTest {

    @Mock
    private ProductRepo productRepo;

    @Mock
    private KafkaTemplate<String, ProductEvent> kafkaTemplate;

    @InjectMocks
    private SellerServiceImpl sellerService;

    @Mock
    private RestTemplate restTemplate;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void init(){
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(sellerService).build();
    }

    @Test
    public void addProductSuccess(){
        Product product = new Product();
        product.setProductId(1);
        product.setProductName("Product1");
        product.setProductDescription("First Product");
        product.setProductShortDescription("First Product");
        product.setProductCategory("Painting");
        product.setStartingPrice(100);
        product.setBidEndDate(new Date());
        product.setUserEmail("seller1@abc.com");

        when(productRepo.save(any())).thenReturn(product);
        when(kafkaTemplate.send(any(),any())).thenReturn(null);

        Product p1 = sellerService.addProduct(product);
        assertEquals(product,p1);
    }

    @Test
    public void addProductFailure(){
        Product product = new Product();
        product.setProductId(1);
        product.setProductName("Product1");
        product.setProductDescription("First Product");
        product.setProductShortDescription("First Product");
        product.setProductCategory("Painting");
        product.setStartingPrice(100);
        product.setBidEndDate(new Date());
        product.setUserEmail("seller1@abc.com");

        when(productRepo.save(any())).thenReturn(null);
        when(kafkaTemplate.send(any(),any())).thenReturn(null);

        Product p1 = sellerService.addProduct(product);
        assertNull(p1);
    }

    @Test
    public void testDeleteProduct_Success() throws InvalidDeleteRequestException {
        Product product = new Product();/* Set up a Product with a bid end date in the future */
        product.setProductName("Product1");
        product.setProductCategory("Painting");
        product.setProductDescription("Product1");
        product.setProductShortDescription("Product1");
        product.setProductId(1);
        product.setStartingPrice(100);
        product.setUserEmail("aa@abc.com");
        product.setBidEndDate(new Date(new Date().getTime() + 5 * 24 * 3600 * 1000));

        when(productRepo.findById(1)).thenReturn(Optional.of(product));
        when(restTemplate.getForObject(anyString(), eq(List.class), anyMap())).thenReturn(Collections.emptyList());

        sellerService.deleteProduct(1);

        verify(productRepo).delete(product);
        verify(kafkaTemplate).send(eq("ProductEventTopic"), any(ProductEvent.class));
    }

    @Test
    public void testDeleteProduct_AfterBidEndDate() {
        Product product = new Product(); /* Set up a Product with a bid end date in the past */
        product.setProductName("Product1");
        product.setProductCategory("Painting");
        product.setProductDescription("Product1");
        product.setProductShortDescription("Product1");
        product.setProductId(1);
        product.setStartingPrice(100);
        product.setUserEmail("aa@abc.com");
        product.setBidEndDate(new Date(new Date().getTime() - 5 * 24 * 3600 * 1000));

        when(productRepo.findById(1)).thenReturn(Optional.of(product));

        assertThrows(InvalidDeleteRequestException.class, () -> sellerService.deleteProduct(1));

        verify(productRepo, never()).delete(any());
        verify(kafkaTemplate, never()).send(anyString(), any());
    }

    @Test
    public void testDeleteProduct_WithBidsPlaced() {
        Product product = new Product();
        product.setProductName("Product1");
        product.setProductCategory("Painting");
        product.setProductDescription("Product1");
        product.setProductShortDescription("Product1");
        product.setProductId(1);
        product.setStartingPrice(100);
        product.setUserEmail("aa@abc.com");
        product.setBidEndDate(new Date());

        when(productRepo.findById(1)).thenReturn(Optional.of(product));
        when(restTemplate.getForObject(anyString(), eq(List.class), anyMap()))
                .thenReturn(Collections.singletonList("Bid1"));

        assertThrows(InvalidDeleteRequestException.class, () -> sellerService.deleteProduct(1));

        verify(productRepo, never()).delete(any());
        verify(kafkaTemplate, never()).send(anyString(), any());
    }
}
