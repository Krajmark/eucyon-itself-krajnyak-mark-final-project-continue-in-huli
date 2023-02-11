package com.greenfoxacademy.ebayclone.dtos.product;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ProductDetailsDTO {
    private Integer id;
    private String name;
    private String type;
    @JsonProperty("purchase_price")
    private Integer purchasePrice;
    @JsonProperty("current_bid")
    private Integer currentBid;
    private String currency;
    private String description;
    @JsonProperty("picture_link")
    private String pictureLink;

    public ProductDetailsDTO(
            Integer id,
            String name,
            String type,
            Integer purchasePrice,
            Integer currentBid,
            String currency,
            String description,
            String pictureLink
    ) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.purchasePrice = purchasePrice;
        this.currentBid = currentBid;
        this.currency = currency;
        this.description = description;
        this.pictureLink = pictureLink;
    }

    public ProductDetailsDTO() {
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
}
