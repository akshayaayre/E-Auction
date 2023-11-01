package com.eauction.buyer;

import com.eauction.buyer.entity.BidDetails;
import com.eauction.buyer.exception.InvalidBidRequestException;
import com.eauction.buyer.repository.BidDetailsRepo;
import com.eauction.buyer.service.BuyerServiceImpl;
import com.eauction.dto.BidEvent;
import com.eauction.dto.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyMap;

public class BuyerServiceImplTest {

    @InjectMocks
    private BuyerServiceImpl buyerService;

    @Mock
    private BidDetailsRepo bidDetailsRepo;

    @Mock
    private KafkaTemplate<String, BidEvent> kafkaTemplate;

    @Mock
    private RestTemplate restTemplate;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void init(){
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(buyerService).build();
    }

    @Test
    public void testAddBidOnProduct_Success() throws InvalidBidRequestException {
        BidDetails mockBidDetails = new BidDetails();
        mockBidDetails.setProductId(1);
        mockBidDetails.setBidAmount(110);
        mockBidDetails.setBidDetailsId(1);
        mockBidDetails.setUserEmail("aa@abc.com");

        Product mockProduct = new Product();
        mockProduct.setBidEndDate(new Date(System.currentTimeMillis() + 100000)); // Set a future date
        mockProduct.setProductId(1);
        mockProduct.setProductName("Product1");
        mockProduct.setProductDescription("First Product");
        mockProduct.setProductShortDescription("First Product");
        mockProduct.setProductCategory("Painting");
        mockProduct.setStartingPrice(100);
        mockProduct.setUserEmail("seller1@abc.com");

        Map<String, Integer> params = new HashMap<>();
        params.put("productId", mockBidDetails.getProductId());

        Mockito.when(restTemplate.getForObject(Mockito.anyString(), Mockito.eq(Product.class), anyMap()))
                .thenReturn(mockProduct);
        Mockito.when(bidDetailsRepo.findByProductIdAndUserEmail(Mockito.anyInt(), Mockito.anyString()))
                .thenReturn(null);

        buyerService.addBidOnProduct(mockBidDetails);

        Mockito.verify(bidDetailsRepo, Mockito.times(1)).save(mockBidDetails);
        Mockito.verify(kafkaTemplate, Mockito.times(1)).send(Mockito.anyString(), Mockito.any());
    }

    @Test
    public void testAddBidOnProduct_BidAfterEndDate() {
        BidDetails mockBidDetails = new BidDetails();
        mockBidDetails.setProductId(123); // Set necessary details

        Product mockProduct = new Product();
        mockProduct.setBidEndDate(new Date(System.currentTimeMillis() - 100000)); // Set a past date

        Mockito.when(restTemplate.getForObject(Mockito.anyString(), Mockito.eq(Product.class),  anyMap()))
                .thenReturn(mockProduct);

        assertThrows(InvalidBidRequestException.class, () -> buyerService.addBidOnProduct(mockBidDetails));
    }

    @Test
    public void testAddBidOnProduct_UpdateExistingBid() throws InvalidBidRequestException {
        BidDetails mockBidDetails = new BidDetails();
        mockBidDetails.setProductId(123); // Set necessary details
        mockBidDetails.setUserEmail("example@example.com");

        BidDetails existingBid = new BidDetails();
        existingBid.setBidDetailsId(1);

        Product mockProduct = new Product();
        mockProduct.setBidEndDate(new Date(System.currentTimeMillis() + 100000)); // Set a future date

        Mockito.when(restTemplate.getForObject(Mockito.anyString(), Mockito.eq(Product.class),  anyMap()))
                .thenReturn(mockProduct);
        Mockito.when(bidDetailsRepo.findByProductIdAndUserEmail(Mockito.anyInt(), Mockito.anyString()))
                .thenReturn(existingBid);

        buyerService.addBidOnProduct(mockBidDetails);

        Mockito.verify(bidDetailsRepo, Mockito.times(1)).save(mockBidDetails);
        Mockito.verify(kafkaTemplate, Mockito.times(1)).send(Mockito.anyString(), Mockito.any());
    }
}
