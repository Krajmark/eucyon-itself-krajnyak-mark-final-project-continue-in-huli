package com.greenfoxacademy.ebayclone.exeptions.user;

public class UsernameAlreadyInUseException extends Exception {
    @Override
    public String getMessage() {
        return "Username already in use!";
    }
}
