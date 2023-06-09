package com.greenfoxacademy.ebayclone.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Integer id;

    private String name;

    private String type;

    @Column(name = "purchase_price")
    private Integer purchasePrice;

    @Column(name = "current_bid")
    private Integer currentBid;

    private String currency = "EUR";

    private String description;

    @Column(name = "picture_link")
    private String pictureLink;

    @Column(name = "is_sold")
    private Boolean isSold = false;

    @ManyToOne(cascade = CascadeType.REFRESH)
    private Seller seller;

    @ManyToOne(cascade = CascadeType.REFRESH)
    private Buyer buyer;

    @ManyToOne(cascade = CascadeType.REFRESH)
    private Buyer currentHighestBidder;

    public Product() {
    }

    public Product(String name, String type, Integer purchasePrice, Integer currentBid, String description, String pictureLink, Seller seller) {
        this.name = name;
        this.type = type;
        this.purchasePrice = purchasePrice;
        this.currentBid = currentBid;
        this.description = description;
        this.pictureLink = pictureLink;
        this.seller = seller;
    }

    public void addAsHighestBidder(Buyer buyer) {
        this.currentHighestBidder = buyer;
        buyer.addToBidHistory(this);
    }

    public void addAsBuyer(Buyer buyer) {
        this.buyer = buyer;
        buyer.addToOrderHistory(this);
    }

    public void addAsSeller(Seller seller) {
        this.seller = seller;
        seller.addProductToList(this);
    }

    public Buyer getCurrentHighestBidder() {
        return currentHighestBidder;
    }

    public void setCurrentHighestBidder(Buyer currentHighestBidder) {
        this.currentHighestBidder = currentHighestBidder;
    }

    public Boolean getIsSold() {
        return isSold;
    }

    public void setIsSold(Boolean isSold) {
        this.isSold = isSold;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(Integer purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public Integer getCurrentBid() {
        return currentBid;
    }

    public void setCurrentBid(Integer currentBid) {
        this.currentBid = currentBid;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPictureLink() {
        return pictureLink;
    }

    public void setPictureLink(String pictureLink) {
        this.pictureLink = pictureLink;
    }

    public Seller getSeller() {
        return seller;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
    }

    public Buyer getBuyer() {
        return buyer;
    }

    public void setBuyer(Buyer buyer) {
        this.buyer = buyer;
    }
}
