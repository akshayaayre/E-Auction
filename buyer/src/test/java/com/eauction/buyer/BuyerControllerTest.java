package com.eauction.buyer;
import com.eauction.buyer.controller.BuyerController;
import com.eauction.buyer.entity.BidDetails;
import com.eauction.buyer.exception.InvalidBidRequestException;
import com.eauction.buyer.service.BuyerServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class BuyerControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private BuyerController buyerController;

    @Mock
    private BuyerServiceImpl buyerService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(buyerController).build();
    }

    @Test
    public void testAddBidOnProduct_Success() throws Exception {
        BidDetails mockBidDetails = new BidDetails();
        mockBidDetails.setProductId(1);
        mockBidDetails.setBidAmount(100);
        mockBidDetails.setUserEmail("aa@abc.com");
        mockBidDetails.setBidDetailsId(1);

        // Mocking the service call to indicate successful bid addition
        Mockito.doNothing().when(buyerService).addBidOnProduct(any(BidDetails.class));

        mockMvc.perform(MockMvcRequestBuilders.post("/e-auction/api/v1/buyer/addBidOnProduct")
                        .contentType("application/json")
                        .content(asJsonString(mockBidDetails)))
                .andExpect(status().isOk());
    }

    // Helper method to convert object to JSON string
    private String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}