package com.eauction.seller.service;

import com.eauction.dto.ProductEvent;
import com.eauction.seller.entity.Product;
import com.eauction.seller.exception.InvalidDeleteRequestException;
import com.eauction.seller.repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class SellerServiceImpl implements SellerService{
    @Autowired
    ProductRepo pr;

    @Autowired
    private KafkaTemplate<String, ProductEvent> kafkaTemplate;

    private static final String PRODUCT_EVENT_TOPIC = "ProductEventTopic";

    @Autowired
    private RestTemplate restTemplate;

    @Transactional
    public Product addProduct(Product product){
        Product p1 = pr.save(product);

        //publish event to Kafka
        ProductEvent productEvent = new ProductEvent("PRODUCT_ADDED_UPDATED", p1);

        kafkaTemplate.send(PRODUCT_EVENT_TOPIC, productEvent);
        return p1;
    }
    public List<Product> getAllProducts(String email){
        return pr.findByUserEmail(email);
    }

    @Transactional
    public void deleteProduct(Integer productId) throws InvalidDeleteRequestException{
        Product p = pr.findById(productId).get();

        if(new Date().after(p.getBidEndDate())){
            throw new InvalidDeleteRequestException("Product cannot be deleted after bid end date");
        }

        String url = "http://localhost:8080/e-auction/api/v1/read/getAllBidsOnProduct?productId={productId}";
//        restTemplate = new RestTemplate();
        Map<String, Integer> params = Collections.singletonMap("productId", productId);
        List bidDetails=restTemplate.getForObject(url, List.class, params);

        if(bidDetails.size() > 0){
           throw new InvalidDeleteRequestException("Cannot delete product that has one or more bids placed on it");
        }
        pr.delete(p);

//        publish event to Kafka
        ProductEvent productEvent = new ProductEvent("PRODUCT_DELETED", p);
        kafkaTemplate.send(PRODUCT_EVENT_TOPIC, productEvent);
    }
}
