package com.greenfoxacademy.ebayclone.exeptions.user;

public class PasswordInvalidException extends Exception {
    public PasswordInvalidException(String message) {
        super(message);
    }
}
