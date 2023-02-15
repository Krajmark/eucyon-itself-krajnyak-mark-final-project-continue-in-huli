package com.greenfoxacademy.ebayclone.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Buyer extends User {
    @OneToMany(cascade = CascadeType.REFRESH)
    private List<Product> orderHistory = new ArrayList<>();

    @OneToMany(cascade = CascadeType.REFRESH)
    private List<Product> bidHistory = new ArrayList<>();

    public Buyer() {
    }

    public Buyer(String username, String password, Integer balance) {
        super(username, password, balance);
    }

    public void addToOrderHistory(Product product) {
        this.orderHistory.add(product);
        product.setBuyer(this);
    }

    public void addToBidHistory(Product product) {
        if (!this.bidHistory.contains(product)) {
            this.bidHistory.add(product);
        }
        product.setCurrentHighestBidder(this);
    }

    public List<Product> getBidHistory() {
        return bidHistory;
    }

    public void setBidHistory(List<Product> bidHistory) {
        this.bidHistory = bidHistory;
    }

    public List<Product> getOrderHistory() {
        return orderHistory;
    }

    public void setOrderHistory(List<Product> orderHistory) {
        this.orderHistory = orderHistory;
    }
}
