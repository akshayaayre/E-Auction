package com.eauction.dto;

import com.eauction.buyer.entity.BidDetails;
import lombok.Data;

@Data
public class BidEvent {

    private String type;
    private BidDetails bidDetails;

    public BidEvent(){}
    public BidEvent(String type, BidDetails bidDetails) {
        this.type = type;
        this.bidDetails = bidDetails;
    }
}
