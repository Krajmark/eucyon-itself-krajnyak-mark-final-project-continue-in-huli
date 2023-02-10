package com.greenfoxacademy.ebayclone.services;

import com.greenfoxacademy.ebayclone.dtos.product.ProductCreationDTO;
import com.greenfoxacademy.ebayclone.dtos.product.ProductDetailsDTO;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;

public interface ProductManagementService {
    ProductDetailsDTO createNewProduct(ProductCreationDTO productCreationDTO, BindingResult bindingResult, Authentication authentication);
}
