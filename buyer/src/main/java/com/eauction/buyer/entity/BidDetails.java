package com.eauction.buyer.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "BID_DETAILS")
@Data
public class BidDetails {

    @Id
    @Column(name = "BID_DETAILS_ID")
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="seq")
    @SequenceGenerator(name="seq",sequenceName="SEQ_BID_DETAILS")
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
}
