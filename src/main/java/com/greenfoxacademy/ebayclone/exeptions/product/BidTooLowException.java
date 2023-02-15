package com.greenfoxacademy.ebayclone.exeptions.product;

public class BidTooLowException extends Exception {
    public BidTooLowException(String message) {
        super(message);
    }
}
