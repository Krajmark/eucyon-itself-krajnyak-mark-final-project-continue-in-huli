package com.greenfoxacademy.ebayclone.exeptions.product;

public class ProductAlreadySoldException extends Exception {
    public ProductAlreadySoldException(String message) {
        super(message);
    }
}
