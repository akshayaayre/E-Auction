package com.eauction.buyer.service;

import com.eauction.buyer.entity.BidDetails;
import com.eauction.buyer.exception.InvalidBidRequestException;

public interface BuyerService {
    public void addBidOnProduct(BidDetails details) throws InvalidBidRequestException;
}
