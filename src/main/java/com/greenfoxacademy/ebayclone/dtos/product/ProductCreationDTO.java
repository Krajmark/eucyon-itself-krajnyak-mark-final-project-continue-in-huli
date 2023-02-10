package com.greenfoxacademy.ebayclone.dtos.product;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record ProductCreationDTO(
        @Size(min = 6, message = "Name have to be longer than 5")
        @NotBlank(message = "Name cannot be blank")
        @NotNull(message = "Name cannot be null")
        String name,
        @NotBlank(message = "Type cannot be blank")
        @NotNull(message = "Type cannot be null")
        String type,
        @Min(value = 1, message = "Price have to be min 1")
        @NotNull(message = "Price cannot be null")
        @JsonProperty("purchase_price")
        Integer purchasePrice,
        @Min(value = 1, message = "Min bid have to be min 1")
        @NotNull(message = "Min bid cannot be null")
        @JsonProperty("min_bid")
        Integer bidingPrice,
        @NotBlank(message = "Description cannot be blank")
        @NotNull(message = "Description cannot be null")
        String description,
        @Pattern(regexp = "htt(p|ps)://.+\\.(jpg|jpeg|png|gif)", message = "Link has to be valid, regex: htt(p|ps)://.+\\.(jpg|jpeg|png|gif)")
        @NotNull(message = "Link cannot be null")
        @JsonProperty("picture_link")
        String pictureLink
) {
}
