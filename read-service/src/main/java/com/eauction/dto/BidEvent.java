package com.eauction.dto;

import com.eauction.readservice.entity.BidDetails;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class BidEvent {

    private String type;
    private BidDetails bidDetails;

    public BidEvent(){}

    @JsonCreator
    public BidEvent(@JsonProperty("type") String type, @JsonProperty("bidDetails") BidDetails bidDetails) {
        this.type = type;
        this.bidDetails = bidDetails;
    }
}
