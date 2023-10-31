package com.eauction.buyer.exception;

public class InvalidBidRequestException extends Exception{
    public InvalidBidRequestException(String message) {
        super(message);
    }
}
