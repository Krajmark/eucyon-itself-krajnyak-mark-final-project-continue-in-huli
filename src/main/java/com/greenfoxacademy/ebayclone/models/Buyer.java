package com.greenfoxacademy.ebayclone.models;

import jakarta.persistence.Entity;

@Entity
public class Buyer extends User {
    public Buyer() {
    }

    public Buyer(String username, String password, Integer balance) {
        super(username, password, balance);
    }

}
