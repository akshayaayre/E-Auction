package com.eauction.buyer;

import com.eauction.buyer.entity.BidDetails;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BidDetailsTest {

    @Test
    public void testGetBidDetailsId() {
        BidDetails bidDetails = new BidDetails();
        bidDetails.setBidDetailsId(123);
        assertEquals(123, bidDetails.getBidDetailsId());
    }

    @Test
    public void testSetBidDetailsId() {
        BidDetails bidDetails = new BidDetails();
        bidDetails.setBidDetailsId(123);
        assertEquals(123, bidDetails.getBidDetailsId());
    }

    @Test
    public void testGetProductId() {
        BidDetails bidDetails = new BidDetails();
        bidDetails.setProductId(456);
        assertEquals(456, bidDetails.getProductId());
    }

    @Test
    public void testSetProductId() {
        BidDetails bidDetails = new BidDetails();
        bidDetails.setProductId(456);
        assertEquals(456, bidDetails.getProductId());
    }

    @Test
    public void testGetBidAmount() {
        BidDetails bidDetails = new BidDetails();
        bidDetails.setBidAmount(789);
        assertEquals(789, bidDetails.getBidAmount());
    }

    @Test
    public void testSetBidAmount() {
        BidDetails bidDetails = new BidDetails();
        bidDetails.setBidAmount(789);
        assertEquals(789, bidDetails.getBidAmount());
    }

    @Test
    public void testGetUserEmail() {
        BidDetails bidDetails = new BidDetails();
        bidDetails.setUserEmail("test@email.com");
        assertEquals("test@email.com", bidDetails.getUserEmail());
    }

    @Test
    public void testSetUserEmail() {
        BidDetails bidDetails = new BidDetails();
        bidDetails.setUserEmail("test@email.com");
        assertEquals("test@email.com", bidDetails.getUserEmail());
    }
}