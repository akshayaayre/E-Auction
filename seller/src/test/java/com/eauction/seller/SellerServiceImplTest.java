package com.eauction.seller;

import com.eauction.dto.ProductEvent;
import com.eauction.seller.entity.Product;
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

import java.util.Date;
import static org.mockito.Mockito.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
public class SellerServiceImplTest {

    @Mock
    private ProductRepo productRepo;

    @Mock
    private KafkaTemplate<String, ProductEvent> kafkaTemplate;

    @InjectMocks
    private SellerServiceImpl sellerService;

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
}
