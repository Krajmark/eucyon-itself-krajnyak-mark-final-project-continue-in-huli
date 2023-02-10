package com.greenfoxacademy.ebayclone.controllers;

import com.greenfoxacademy.ebayclone.dtos.product.ProductCreationDTO;
import com.greenfoxacademy.ebayclone.services.ProductManagementService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {

    private final ProductManagementService productManagementService;

    public ProductController(ProductManagementService productManagementService) {
        this.productManagementService = productManagementService;
    }

    @PostMapping("/products/create")
    public ResponseEntity<?> createNewProduct(
            @Valid @RequestBody ProductCreationDTO productCreationDTO,
            BindingResult bindingResult,
            Authentication authentication
    ) {
        //TODO: add uri to return
        return ResponseEntity.created(null).body(
                this.productManagementService.createNewProduct(productCreationDTO, bindingResult, authentication)
        );
    }
}
