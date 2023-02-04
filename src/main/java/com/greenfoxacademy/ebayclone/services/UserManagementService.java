package com.greenfoxacademy.ebayclone.services;

import com.greenfoxacademy.ebayclone.dtos.user.UserDTO;
import com.greenfoxacademy.ebayclone.exeptions.user.UsernameAlreadyInUseException;

public interface UserManagementService {
    void createNewUser(String userType, UserDTO userDTO) throws UsernameAlreadyInUseException;
}
