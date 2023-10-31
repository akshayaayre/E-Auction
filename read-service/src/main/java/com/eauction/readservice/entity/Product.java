package com.eauction.readservice.entity;

import lombok.Data;
//import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "PRODUCT")
@Data
//@Document(collection = "products")
public class Product {

    @Id
    @Column(name = "PRODUCT_ID")
    Integer productId;

    @Column(name = "PRODUCT_NAME")
    String productName;

    @Column(name = "PRODUCT_SHORT_DESCRIPTION")
    String productShortDescription;

    @Column(name = "PRODUCT_DESCRIPTION")
    String productDescription;

    @Column(name = "PRODUCT_CATEGORY")
    String productCategory;

    @Column(name = "STARTING_PRICE")
    Integer startingPrice;

    @Temporal(TemporalType.DATE)
    @Column(name = "BID_END_DATE")
    Date bidEndDate;

    @Column(name = "USER_EMAIL")
    String userEmail;
}