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

    public void addProductToList(Product product) {
        this.productList.add(product);
        product.setSeller(this);
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }
}
