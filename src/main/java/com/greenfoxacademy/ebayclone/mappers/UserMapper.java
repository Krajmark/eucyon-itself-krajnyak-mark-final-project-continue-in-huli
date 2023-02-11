package com.greenfoxacademy.ebayclone.mappers;

import com.greenfoxacademy.ebayclone.dtos.user.UserResponseDTO;
import com.greenfoxacademy.ebayclone.models.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserResponseDTO buyerToUserResponseDTO(User user);
}
