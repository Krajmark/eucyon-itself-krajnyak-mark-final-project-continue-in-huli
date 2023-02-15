package com.greenfoxacademy.ebayclone.controllers;

import com.greenfoxacademy.ebayclone.dtos.product.ProductBidDTO;
import com.greenfoxacademy.ebayclone.dtos.product.ProductCreationDTO;
import com.greenfoxacademy.ebayclone.dtos.product.ProductDetailsDTO;
import com.greenfoxacademy.ebayclone.exeptions.product.ProductNotFoundException;
import com.greenfoxacademy.ebayclone.services.ProductManagementService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductManagementService productManagementService;

    public ProductController(ProductManagementService productManagementService) {
        this.productManagementService = productManagementService;
    }

    @PostMapping("/create")
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

    @GetMapping({"/", ""})
    public ResponseEntity<List<ProductDetailsDTO>> listAllProducts(
            @RequestParam(required = false) String page
    ) {
        return ResponseEntity.ok(this.productManagementService.listAllProducts(page));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDetailsDTO> getSpecificProductById(
            @PathVariable String id
    ) throws ProductNotFoundException {
        return ResponseEntity.ok(this.productManagementService.getProductById(id));
    }

    @GetMapping("/{id}/bid")
    public ResponseEntity<ProductDetailsDTO> bidOnSpecificItem(
            @PathVariable String id,
            @Valid @RequestBody ProductBidDTO productBidDTO,
            BindingResult bindingResult,
            Authentication authentication
    ) throws ProductNotFoundException {
        return ResponseEntity.ok(this.productManagementService.bidOnProduct(id, productBidDTO, bindingResult, authentication));
    }
}
