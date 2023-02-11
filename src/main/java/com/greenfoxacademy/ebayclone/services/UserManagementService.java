package com.greenfoxacademy.ebayclone.services;

import com.greenfoxacademy.ebayclone.dtos.user.LoginResponseDTO;
import com.greenfoxacademy.ebayclone.dtos.user.UserCreationDTO;
import com.greenfoxacademy.ebayclone.exeptions.user.PasswordInvalidException;
import com.greenfoxacademy.ebayclone.exeptions.user.UsernameAlreadyInUseException;
import org.springframework.validation.BindingResult;

public interface UserManagementService {

    LoginResponseDTO processLoginRequest(UserCreationDTO userCreationDTO, BindingResult bindingResult) throws PasswordInvalidException;

    boolean createNewUser(String userType, UserCreationDTO userCreationDTO, BindingResult bindingResult) throws UsernameAlreadyInUseException;

}
