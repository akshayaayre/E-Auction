package com.eauction.readservice;

import com.eauction.dto.BidEvent;
//import com.eauction.dto.Product;
import com.eauction.dto.ProductEvent;
import com.eauction.dto.UserEvent;
import com.eauction.readservice.entity.BidDetails;
import com.eauction.readservice.entity.Product;
import com.eauction.readservice.entity.UserDetails;
import com.eauction.readservice.repository.BidDetailsRepository;
import com.eauction.readservice.repository.ProductRepository;
import com.eauction.readservice.repository.UserDetailsRepository;
import com.eauction.readservice.event.ReadEventConsumer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

public class ReadEventConsumerTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private BidDetailsRepository bidDetailsRepository;

    @Mock
    private UserDetailsRepository userDetailsRepository;

    @InjectMocks
    private ReadEventConsumer readEventConsumer;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testHandleProductEvent_ProductDeleted() {
        ProductEvent productEvent = new ProductEvent("PRODUCT_DELETED", new Product());
        readEventConsumer.handleProductEvent(productEvent);

        verify(productRepository).delete(any(Product.class));
    }

    @Test
    public void testHandleProductEvent_ProductNotDeleted() {
        Product product = new Product();
        ProductEvent productEvent = new ProductEvent("PRODUCT_ADDED", product);
        readEventConsumer.handleProductEvent(productEvent);

        verify(productRepository).save(product);
    }

    @Test
    public void testHandleBidEvent_BidDeleted() {
        BidEvent bidEvent = new BidEvent("BID_DELETED", new BidDetails());
        readEventConsumer.handleBidEvent(bidEvent);

        verify(bidDetailsRepository).delete(any(BidDetails.class));
    }

    @Test
    public void testHandleBidEvent_BidNotDeleted() {
        BidDetails bidDetails = new BidDetails();
        BidEvent bidEvent = new BidEvent("BID_ADDED", bidDetails);
        readEventConsumer.handleBidEvent(bidEvent);

        verify(bidDetailsRepository).save(bidDetails);
    }

    @Test
    public void testHandleUserEvent_UserDeleted() {
        UserEvent userEvent = new UserEvent("USER_DELETED", new UserDetails());
        readEventConsumer.handleUserEvent(userEvent);

        verify(userDetailsRepository).delete(any(UserDetails.class));
    }

    @Test
    public void testHandleUserEvent_UserNotDeleted() {
        UserDetails userDetails = new UserDetails();
        UserEvent userEvent = new UserEvent("USER_ADDED", userDetails);
        readEventConsumer.handleUserEvent(userEvent);

        verify(userDetailsRepository).save(userDetails);
    }
}
