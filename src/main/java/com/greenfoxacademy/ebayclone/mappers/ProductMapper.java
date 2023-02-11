package com.greenfoxacademy.ebayclone.mappers;

import com.greenfoxacademy.ebayclone.dtos.product.ProductCreationDTO;
import com.greenfoxacademy.ebayclone.dtos.product.ProductDetailsDTO;
import com.greenfoxacademy.ebayclone.dtos.product.ProductDetailsWithBuyerDTO;
import com.greenfoxacademy.ebayclone.models.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProductMapper {

    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);


    @Mapping(target = "buyer", ignore = true)
    @Mapping(target = "isSold", ignore = true)
    @Mapping(target = "seller", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "currentBid", source = "bidingPrice")
    @Mapping(target = "currency", ignore = true)
    Product productCreationDtoToProduct(ProductCreationDTO productCreationDTO);

    ProductDetailsDTO productToProductDetailsDto(Product product);

    ProductDetailsWithBuyerDTO productToProductDetailsWithBuyerDto(Product product);
}
