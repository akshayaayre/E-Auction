package com.eauction.seller;


import com.eauction.seller.entity.Product;
import com.eauction.seller.repository.ProductRepo;
import com.netflix.discovery.converters.Auto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@AutoConfigureMockMvc
@DataJpaTest
public class ProductRepoTest {

    @Autowired
    private ProductRepo productRepo;

    private Product product = new Product();

    @BeforeEach
    public void init(){
        product.setProductId(1);
        product.setProductName("Product1");
        product.setProductDescription("First Product");
        product.setProductShortDescription("First Product");
        product.setProductCategory("Painting");
        product.setStartingPrice(100);
        product.setBidEndDate(new Date());
        product.setUserEmail("seller1@abc.com");
    }

    @Test
    public void saveSuccess() throws Exception{
        Product p1 = null;
        productRepo.save(product);

        p1 = productRepo.findById(product.getProductId()).get();
        assertEquals(product.getProductName(), p1.getProductName());
    }

    @Test
    public void saveFailure() throws Exception{
        Product p1 = null;

        if(productRepo.findAll().toString().isEmpty()){
            productRepo.save(product);
            p1 = productRepo.findById(product.getProductId()).get();
        }

        assertNull(p1);
    }

}
