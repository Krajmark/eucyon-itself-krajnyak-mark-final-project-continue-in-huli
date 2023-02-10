package com.greenfoxacademy.ebayclone.services;

import com.greenfoxacademy.ebayclone.dtos.product.ProductCreationDTO;
import com.greenfoxacademy.ebayclone.dtos.product.ProductDetailsDTO;
import com.greenfoxacademy.ebayclone.mappers.ProductMapper;
import com.greenfoxacademy.ebayclone.models.Product;
import com.greenfoxacademy.ebayclone.models.Seller;
import com.greenfoxacademy.ebayclone.repositories.ProductRepo;
import com.greenfoxacademy.ebayclone.repositories.SellerRepo;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.Optional;

@Service
public class ProductManagementServiceImpl implements ProductManagementService {

    private final SellerRepo sellerRepo;
    private final ProductRepo productRepo;
    private final BindingResultHandlerService bindingResultHandlerService;

    public ProductManagementServiceImpl(SellerRepo sellerRepo, ProductRepo productRepo, BindingResultHandlerService bindingResultHandlerService) {
        this.sellerRepo = sellerRepo;
        this.productRepo = productRepo;
        this.bindingResultHandlerService = bindingResultHandlerService;
    }

    @Override
    public ProductDetailsDTO createNewProduct(
            ProductCreationDTO productCreationDTO,
            BindingResult bindingResult,
            Authentication authentication) {
        this.bindingResultHandlerService.handleBindingResult(bindingResult);
        Optional<Seller> sellerOptional = this.sellerRepo.findSellerByUsername(authentication.getName());
        if (sellerOptional.isEmpty()) {
            throw new UsernameNotFoundException("No such seller");
        }
        Seller seller = sellerOptional.get();
        Product product = ProductMapper.INSTANCE.productCreationDtoToProduct(productCreationDTO);
        product.addAsSeller(seller);
        product = this.productRepo.save(product);
        this.sellerRepo.save(seller);
        return ProductMapper.INSTANCE.productToProductDetailsDto(product);
    }
}
