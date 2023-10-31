package com.eauction.seller;

import com.eauction.seller.controller.SellerController;
import com.eauction.seller.entity.Product;
import com.eauction.seller.service.SellerServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Date;

@SpringBootTest
@AutoConfigureMockMvc
public class SellerControllerTest {

    @Mock
    private SellerServiceImpl sellerService;

    @InjectMocks
    private SellerController sellerController;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void init(){
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(sellerController).build();
    }

    @Test
    public void addProductSuccess() throws Exception{
        Product product = new Product();
        product.setProductId(1);
        product.setProductName("Product1");
        product.setProductDescription("First Product");
        product.setProductShortDescription("First Product");
        product.setProductCategory("Painting");
        product.setStartingPrice(100);
        product.setBidEndDate(new Date());
        product.setUserEmail("seller1@abc.com");

        when(sellerService.addProduct(any())).thenReturn(product);
        assertEquals(product.getProductId(),1);
        mockMvc.perform(MockMvcRequestBuilders.post("/e-auction/api/v1/seller/addProduct")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(product)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void addProductFailure() throws Exception{
        Product product = new Product();
        product.setProductId(1);
        product.setProductName("Product1");
        product.setProductDescription("First Product");
        product.setProductShortDescription("First Product");
        product.setProductCategory("Painting");
        product.setStartingPrice(100);
        product.setBidEndDate(new Date());
        product.setUserEmail("seller1@abc.com");

        when(sellerService.addProduct(any())).thenReturn(null);
        Product p1 = sellerService.addProduct(product);
        assertNull(p1);
        mockMvc.perform(MockMvcRequestBuilders.post("/e-auction/api/v1/seller/addProduct")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(product)))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError());
    }

    @Test
    public void updateProductSuccess() throws Exception{
        Product product = new Product();
        product.setProductId(1);
        product.setProductName("Product1");
        product.setProductDescription("First Product");
        product.setProductShortDescription("First Product");
        product.setProductCategory("Painting");
        product.setStartingPrice(100);
        product.setBidEndDate(new Date());
        product.setUserEmail("seller1@abc.com");

        when(sellerService.addProduct(any())).thenReturn(product);
        assertEquals(product.getProductId(),1);
        mockMvc.perform(MockMvcRequestBuilders.put("/e-auction/api/v1/seller/updateProduct")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(product)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void updateProductFailure() throws Exception{
        Product product = new Product();
        product.setProductId(1);
        product.setProductName("Product1");
        product.setProductDescription("First Product");
        product.setProductShortDescription("First Product");
        product.setProductCategory("Painting");
        product.setStartingPrice(100);
        product.setBidEndDate(new Date());
        product.setUserEmail("seller1@abc.com");

        when(sellerService.addProduct(any())).thenReturn(null);
        Product p1 = sellerService.addProduct(product);
        assertNull(p1);
        mockMvc.perform(MockMvcRequestBuilders.put("/e-auction/api/v1/seller/updateProduct")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(product)))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError());
    }
}
