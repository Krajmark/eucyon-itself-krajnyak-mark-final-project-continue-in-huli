package com.greenfoxacademy.ebayclone.controllers;

import com.greenfoxacademy.ebayclone.dtos.user.TokenDTO;
import com.greenfoxacademy.ebayclone.dtos.user.UserDTO;
import com.greenfoxacademy.ebayclone.security.JwtProviderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final JwtProviderService jwtProviderService;

    public AuthController(JwtProviderService jwtProviderService) {
        this.jwtProviderService = jwtProviderService;
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDTO> sendToken(@RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(new TokenDTO(this.jwtProviderService.generateTokenByUserLoginRequest(userDTO)));
    }
}
