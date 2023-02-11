package com.greenfoxacademy.ebayclone.mappers;

import com.greenfoxacademy.ebayclone.dtos.user.BuyerDTO;
import com.greenfoxacademy.ebayclone.models.Buyer;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    BuyerDTO buyerToBuyerDTO(Buyer buyer);
}
