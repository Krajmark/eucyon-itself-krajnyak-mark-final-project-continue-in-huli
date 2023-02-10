package com.greenfoxacademy.ebayclone.exeptions.product;

public class ProductNotFoundException extends Exception {
    public ProductNotFoundException(String message) {
        super(message);
    }
}
