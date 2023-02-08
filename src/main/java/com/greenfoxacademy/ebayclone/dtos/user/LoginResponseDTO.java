package com.greenfoxacademy.ebayclone.dtos.user;

public record LoginResponseDTO(
        String token,
        Integer balance
) {
}
