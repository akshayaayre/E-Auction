package com.eauction.readservice.event;

import com.eauction.dto.BidEvent;
import com.eauction.dto.ProductEvent;
import com.eauction.dto.UserEvent;
import com.eauction.readservice.entity.BidDetails;
import com.eauction.readservice.entity.Product;
import com.eauction.readservice.entity.UserDetails;
import com.eauction.readservice.repository.BidDetailsRepository;
import com.eauction.readservice.repository.ProductRepository;
import com.eauction.readservice.repository.UserDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class ReadEventConsumer {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    BidDetailsRepository bidDetailsRepository;

    @Autowired
    UserDetailsRepository userDetailsRepository;

    @KafkaListener(topics = "ProductEventTopic", groupId = "read-group")
    public void handleProductEvent(ProductEvent productEvent){
        //create and save product model
        System.out.println("productEvent: " + productEvent);
        Product product;

        if(productEvent != null && productEvent.getType() != null && productEvent.getProduct() != null){
            product = productEvent.getProduct();

            if(productEvent.getType().trim().equalsIgnoreCase("PRODUCT_DELETED")){
                productRepository.delete(product);
            }
            else{
                productRepository.save(product);
            }
        }
    }

    @KafkaListener(topics = "BidEventTopic", groupId = "read-group")
    public void handleBidEvent(BidEvent bidEvent){
        //create and save bid details model
        System.out.println("bidEvent: " + bidEvent);
        BidDetails bidDetails;

        if(bidEvent != null && bidEvent.getType() != null && bidEvent.getBidDetails() != null){
            bidDetails = bidEvent.getBidDetails();

            if(bidEvent.getType().trim().equalsIgnoreCase("BID_DELETED")){
                bidDetailsRepository.delete(bidDetails);
            }
            else{
                bidDetailsRepository.save(bidDetails);
            }
        }
    }

    @KafkaListener(topics = "UserEventTopic", groupId = "read-group")
    public void handleUserEvent(UserEvent userEvent){
        //create and save bid details model
        System.out.println("userEvent: " + userEvent);
        UserDetails userDetails;

        if(userEvent != null && userEvent.getType() != null && userEvent.getUserDetails() != null){
            userDetails = userEvent.getUserDetails();

            if(userEvent.getType().trim().equalsIgnoreCase("USER_DELETED")){
                userDetailsRepository.delete(userDetails);
            }
            else{
                userDetailsRepository.save(userDetails);
            }
        }
    }
}
