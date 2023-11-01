package com.eauction.buyer;

import com.eauction.buyer.entity.BidDetails;
import com.eauction.buyer.repository.BidDetailsRepo;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@AutoConfigureMockMvc
@DataJpaTest
public class BidDetailsRepoTest {

    @Mock
    private BidDetailsRepo bidDetailsRepo;

    @Test
    public void testFindByProductIdAndUserEmail() {
        Integer productId = 123;
        String userEmail = "test@example.com";
        BidDetails expectedBidDetails = new BidDetails();
        expectedBidDetails.setUserEmail("test@example.com");
        expectedBidDetails.setBidDetailsId(1);
        expectedBidDetails.setBidAmount(100);
        expectedBidDetails.setProductId(123);

        when(bidDetailsRepo.findByProductIdAndUserEmail(eq(productId), eq(userEmail))).thenReturn(expectedBidDetails);

        BidDetails actualBidDetails = bidDetailsRepo.findByProductIdAndUserEmail(productId, userEmail);

        assertEquals(expectedBidDetails, actualBidDetails);
        verify(bidDetailsRepo).findByProductIdAndUserEmail(eq(productId), eq(userEmail));
    }
}