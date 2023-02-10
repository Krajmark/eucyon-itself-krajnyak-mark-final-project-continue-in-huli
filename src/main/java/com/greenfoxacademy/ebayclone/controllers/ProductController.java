package com.greenfoxacademy.ebayclone.controllers;

import com.greenfoxacademy.ebayclone.dtos.product.ProductCreationDTO;
import com.greenfoxacademy.ebayclone.dtos.product.ProductDetailsDTO;
import com.greenfoxacademy.ebayclone.exeptions.product.ProductNotFoundException;
import com.greenfoxacademy.ebayclone.services.ProductManagementService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {

    private final ProductManagementService productManagementService;

    public ProductController(ProductManagementService productManagementService) {
        this.productManagementService = productManagementService;
    }

    @PostMapping("/products/create")
    public ResponseEntity<ProductDetailsDTO> createNewProduct(
            @Valid @RequestBody ProductCreationDTO productCreationDTO,
            BindingResult bindingResult,
            Authentication authentication
    ) {
        //TODO: add uri to return
        return ResponseEntity.created(null).body(
                this.productManagementService.createNewProduct(productCreationDTO, bindingResult, authentication)
        );
    }

    @GetMapping("/products")
    public ResponseEntity<List<ProductDetailsDTO>> listAllProducts(
            @RequestParam(required = false) String page
    ) {
        return ResponseEntity.ok(this.productManagementService.listAllProducts(page));
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<ProductDetailsDTO> getSpecificProductById(
            @PathVariable String id
    ) throws ProductNotFoundException {
        return ResponseEntity.ok(this.productManagementService.getProductById(id));
    }
}
