package com.greenfoxacademy.ebayclone.dtos.product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record ProductBidDTO(
        @NotBlank(message = "Bid cannot be blank")
        @NotNull(message = "Bid cannot be null")
        Integer bid,
        @NotBlank(message = "Currency cannot be blank")
        @NotNull(message = "Currency cannot be null")
        @Pattern(regexp = "EUR", message = "Currently we only accept EUR as currency")
        String currency
) {
}
