package com.eauction.readservice.entity;

import lombok.Data;
import org.hibernate.annotations.Formula;
//import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;

@Entity
@Table(name = "BID_DETAILS")
@Data
//@Document(collection = "bid_details")
public class BidDetails {

    @Id
    @Column(name = "BID_DETAILS_ID")
    Integer bidDetailsId;

//    @ManyToOne
//    @Column(name = "PRODUCT_ID")
//    Product product;

    @Column(name = "PRODUCT_ID")
    Integer productId;

    @Column(name = "BID_AMOUNT")
    Integer bidAmount;

    @Column(name = "USER_EMAIL")
    String userEmail;

    @Transient
//    @Formula(value = "select (b.*) from BID_DETAILS as a " +
//            "join USER_DETAILS b on a.user_email = b.email")
    UserDetails userDetails;
}

