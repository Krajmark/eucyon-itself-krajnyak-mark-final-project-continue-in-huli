package com.greenfoxacademy.ebayclone.services;

import com.greenfoxacademy.ebayclone.dtos.product.ProductBidDTO;
import com.greenfoxacademy.ebayclone.dtos.product.ProductCreationDTO;
import com.greenfoxacademy.ebayclone.dtos.product.ProductDetailsDTO;
import com.greenfoxacademy.ebayclone.exeptions.product.BidTooLowException;
import com.greenfoxacademy.ebayclone.exeptions.product.ProductAlreadySoldException;
import com.greenfoxacademy.ebayclone.exeptions.product.ProductNotFoundException;
import com.greenfoxacademy.ebayclone.exeptions.user.NotEnoughBalanceException;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;

import java.util.List;

public interface ProductManagementService {
    ProductDetailsDTO createNewProduct(ProductCreationDTO productCreationDTO, BindingResult bindingResult, Authentication authentication);

    List<ProductDetailsDTO> listAllProducts(String page);

    ProductDetailsDTO getProductById(String id) throws ProductNotFoundException;

    ProductDetailsDTO bidOnProduct(
            String id,
            ProductBidDTO productBidDTO,
            BindingResult bindingResult,
            Authentication authentication
    ) throws ProductNotFoundException, BidTooLowException, ProductAlreadySoldException, NotEnoughBalanceException;
}
