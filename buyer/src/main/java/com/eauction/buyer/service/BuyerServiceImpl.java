package com.eauction.buyer.service;

import com.eauction.buyer.entity.BidDetails;
//import com.eauction.buyer.entity.Product;
//import com.eauction.buyer.repository.ProductRepo;
import com.eauction.buyer.exception.InvalidBidRequestException;
import com.eauction.buyer.repository.BidDetailsRepo;
import com.eauction.dto.BidEvent;
import com.eauction.dto.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.Date;
import java.util.Map;

@Service
public class BuyerServiceImpl implements BuyerService{

    @Autowired
    BidDetailsRepo bidDetailsRepo;

    @Autowired
    private KafkaTemplate<String, BidEvent> kafkaTemplate;

    private static final String BID_EVENT_TOPIC = "BidEventTopic";

    @Autowired
    RestTemplate restTemplate;

    @Transactional
    public void addBidOnProduct(BidDetails details) throws InvalidBidRequestException {

//        restTemplate = new RestTemplate();
        String url = "http://localhost:8080/e-auction/api/v1/read/getProductDetails?productId={productId}";
        Map<String, Integer> params = Collections.singletonMap("productId", details.getProductId());
        Product product=restTemplate.getForObject(url, Product.class, params);

        if(new Date().after(product.getBidEndDate())){
            throw new InvalidBidRequestException("Bid cannot be placed/updated after bid end date");
        }

        BidDetails existingBid = bidDetailsRepo.findByProductIdAndUserEmail(details.getProductId(), details.getUserEmail());
        if(existingBid != null){
            details.setBidDetailsId(existingBid.getBidDetailsId());
        }
        bidDetailsRepo.save(details);

        //publish event to kafka
        BidEvent bidEvent = new BidEvent("BID_ADDED_UPDATED", details);

        kafkaTemplate.send(BID_EVENT_TOPIC, bidEvent);
    }
}
