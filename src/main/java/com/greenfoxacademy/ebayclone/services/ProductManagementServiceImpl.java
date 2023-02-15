package com.greenfoxacademy.ebayclone.services;

import com.greenfoxacademy.ebayclone.dtos.product.ProductBidDTO;
import com.greenfoxacademy.ebayclone.dtos.product.ProductCreationDTO;
import com.greenfoxacademy.ebayclone.dtos.product.ProductDetailsDTO;
import com.greenfoxacademy.ebayclone.dtos.product.ProductDetailsWithBuyerDTO;
import com.greenfoxacademy.ebayclone.exeptions.product.BidTooLowException;
import com.greenfoxacademy.ebayclone.exeptions.product.ProductNotFoundException;
import com.greenfoxacademy.ebayclone.mappers.ProductMapper;
import com.greenfoxacademy.ebayclone.models.Buyer;
import com.greenfoxacademy.ebayclone.models.Product;
import com.greenfoxacademy.ebayclone.models.Seller;
import com.greenfoxacademy.ebayclone.repositories.BuyerRepo;
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
    private final BuyerRepo buyerRepo;
    private final ProductRepo productRepo;
    private final BindingResultHandlerService bindingResultHandlerService;

    public ProductManagementServiceImpl(SellerRepo sellerRepo, BuyerRepo buyerRepo, ProductRepo productRepo, BindingResultHandlerService bindingResultHandlerService) {
        this.sellerRepo = sellerRepo;
        this.buyerRepo = buyerRepo;
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
                .filter(product -> !product.getIsSold())
                .map(ProductMapper.INSTANCE::productToProductDetailsDto)
                .toList();
    }

    @Override
    public ProductDetailsDTO getProductById(String id) throws ProductNotFoundException {
        int intId = Integer.parseInt(id);
        Optional<Product> productOptional = this.productRepo.findById(intId);
        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            if (product.getIsSold()) {
                return ProductMapper.INSTANCE.productToProductDetailsWithBuyerDto(product);
            }
            return ProductMapper.INSTANCE.productToProductDetailsDto(product);
        }
        throw new ProductNotFoundException("No product under such(".concat(id).concat(") id"));
    }

    @Override
    public ProductDetailsWithBuyerDTO bidOnProduct(String id, ProductBidDTO productBidDTO, BindingResult bindingResult, Authentication authentication) throws ProductNotFoundException, BidTooLowException {
        this.bindingResultHandlerService.handleBindingResult(bindingResult);
        int productId = Integer.parseInt(id);
        Optional<Product> productOptional = this.productRepo.findById(productId);
        if (productOptional.isEmpty()) {
            throw new ProductNotFoundException("No such product found by ID(".concat(id).concat(") to bid on!"));
        }
        Optional<Buyer> buyerOptional = this.buyerRepo.findBuyerByUsername(authentication.getName());
        if (buyerOptional.isEmpty()) {
            throw new UsernameNotFoundException("No such buyer with these credentials");
        }
        Product product = productOptional.get();
        Buyer buyer = buyerOptional.get();
        int bid = productBidDTO.bid();
        int currentBid = product.getCurrentBid();
        if (bid <= currentBid) {
            throw new BidTooLowException(
                    "Given bid of "
                            .concat(String.valueOf(bid))
                            .concat(" ")
                            .concat(productBidDTO.currency())
                            .concat(" is lower than the current bid of ")
                            .concat(String.valueOf(currentBid))
                            .concat(productBidDTO.currency())
            );
        }
        product.addAsBuyer(buyer);
        product.setIsSold(true);
        product = this.productRepo.save(product);
        this.buyerRepo.save(buyer);
        return ProductMapper.INSTANCE.productToProductDetailsWithBuyerDto(product);
    }
}
