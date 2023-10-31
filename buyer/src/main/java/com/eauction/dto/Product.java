package com.eauction.dto;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
public class Product {

    Integer productId;

    String productName;

    String productShortDescription;

    String productDescription;

    String productCategory;

    Integer startingPrice;

    Date bidEndDate;

    String userEmail;
}

