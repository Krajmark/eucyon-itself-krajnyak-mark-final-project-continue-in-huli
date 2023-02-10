package com.greenfoxacademy.ebayclone.services;

import com.greenfoxacademy.ebayclone.dtos.product.ProductCreationDTO;
import com.greenfoxacademy.ebayclone.dtos.product.ProductDetailsDTO;
import com.greenfoxacademy.ebayclone.exeptions.product.ProductNotFoundException;
import com.greenfoxacademy.ebayclone.mappers.ProductMapper;
import com.greenfoxacademy.ebayclone.models.Product;
import com.greenfoxacademy.ebayclone.models.Seller;
import com.greenfoxacademy.ebayclone.repositories.ProductRepo;
import com.greenfoxacademy.ebayclone.repositories.SellerRepo;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.List;
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

    @Override
    public List<ProductDetailsDTO> listAllProducts(String page) {
        if (page == null) {
            page = "0";
        }
        int pageNumber = Integer.parseInt(page);
        Pageable pageable = Pageable.ofSize(20).withPage(pageNumber);
        return this.productRepo.findAll(pageable).stream()
                .map(ProductMapper.INSTANCE::productToProductDetailsDto)
                .toList();
    }

    @Override
    public ProductDetailsDTO getProductById(String id) throws ProductNotFoundException {
        int intId = Integer.parseInt(id);
        Optional<Product> productOptional = this.productRepo.findById(intId);
        if (productOptional.isPresent()) {
            return ProductMapper.INSTANCE.productToProductDetailsDto(productOptional.get());
        }
        throw new ProductNotFoundException("No product under such(".concat(id).concat(") id"));
    }
}
