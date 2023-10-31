package com.eauction.seller.entity;

import lombok.Data;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Table(name = "PRODUCT")
@Data
public class Product {

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="seq")
    @SequenceGenerator(name="seq",sequenceName="SEQ_PRODUCT")
    @Column(name = "PRODUCT_ID")
    Integer productId;

    @Column(name = "PRODUCT_NAME")
    @NotNull(message = "Product Name cannot be null")
    @Size(min = 5, max = 30, message = "Product Name must have minimum 5 letters, max 30")
    String productName;

    @Column(name = "PRODUCT_SHORT_DESCRIPTION")
    String productShortDescription;

    @Column(name = "PRODUCT_DESCRIPTION")
    String productDescription;

//    @ManyToOne
//    @Column(name = "PRODUCT_CATEGORY_ID")
//    ProductCategory productCategory;
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
