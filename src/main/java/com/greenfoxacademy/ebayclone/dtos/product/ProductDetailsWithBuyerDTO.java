package com.greenfoxacademy.ebayclone.dtos.product;

import com.greenfoxacademy.ebayclone.dtos.user.UserResponseDTO;

public class ProductDetailsWithBuyerDTO extends ProductDetailsDTO {
    private UserResponseDTO buyer;

    public ProductDetailsWithBuyerDTO(Integer id, String name, String type, Integer purchasePrice, Integer currentBid, String currency, String description, String pictureLink, UserResponseDTO buyer) {
        super(id, name, type, purchasePrice, currentBid, currency, description, pictureLink);
        this.buyer = buyer;
    }

    public ProductDetailsWithBuyerDTO() {
    }

    public UserResponseDTO getBuyer() {
        return buyer;
    }

    public void setBuyer(UserResponseDTO buyer) {
        this.buyer = buyer;
    }
}
