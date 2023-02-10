package com.greenfoxacademy.ebayclone.dtos.product;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ProductDetailsDTO(
        Integer id,
        String name,
        String type,
        @JsonProperty("purchase_price")
        Integer purchasePrice,
        @JsonProperty("current_bid")
        Integer currentBid,
        String currency,
        String description,
        @JsonProperty("picture_link")
        String pictureLink
) {
}
