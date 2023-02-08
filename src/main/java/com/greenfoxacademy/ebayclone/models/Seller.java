package com.greenfoxacademy.ebayclone.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Seller extends User {
    @OneToMany(cascade = CascadeType.REFRESH)
    List<Product> productList = new ArrayList<>();

    public Seller() {
    }

    public Seller(String username, String password, Integer balance) {
        super(username, password, balance);
    }

}
