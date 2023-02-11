package com.greenfoxacademy.ebayclone.dtos.product;

import com.greenfoxacademy.ebayclone.dtos.user.BuyerDTO;

public class ProductDetailsWithBuyerDTO extends ProductDetailsDTO {
    private BuyerDTO buyer;

    public ProductDetailsWithBuyerDTO(Integer id, String name, String type, Integer purchasePrice, Integer currentBid, String currency, String description, String pictureLink, BuyerDTO buyer) {
        super(id, name, type, purchasePrice, currentBid, currency, description, pictureLink);
        this.buyer = buyer;
    }

    public ProductDetailsWithBuyerDTO() {
    }

    public BuyerDTO getBuyer() {
        return buyer;
    }

    public void setBuyer(BuyerDTO buyer) {
        this.buyer = buyer;
    }
}
