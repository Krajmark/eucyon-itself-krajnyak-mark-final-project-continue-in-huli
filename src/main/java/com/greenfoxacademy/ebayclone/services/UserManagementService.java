package com.greenfoxacademy.ebayclone.services;

import com.greenfoxacademy.ebayclone.dtos.user.LoginResponseDTO;
import com.greenfoxacademy.ebayclone.dtos.user.UserDTO;
import com.greenfoxacademy.ebayclone.exeptions.user.PasswordInvalidException;
import com.greenfoxacademy.ebayclone.exeptions.user.UsernameAlreadyInUseException;
import org.springframework.validation.BindingResult;

public interface UserManagementService {

    LoginResponseDTO processLoginRequest(UserDTO userDTO, BindingResult bindingResult) throws PasswordInvalidException;

    void createNewUser(String userType, UserDTO userDTO, BindingResult bindingResult) throws UsernameAlreadyInUseException;

}
