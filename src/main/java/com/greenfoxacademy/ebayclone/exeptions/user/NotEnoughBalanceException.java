package com.greenfoxacademy.ebayclone.exeptions.user;

public class NotEnoughBalanceException extends Exception {
    public NotEnoughBalanceException(String message) {
        super(message);
    }
}
