package com.greenfoxacademy.ebayclone.services;

import com.greenfoxacademy.ebayclone.dtos.user.UserDTO;
import com.greenfoxacademy.ebayclone.exeptions.user.UsernameAlreadyInUseException;
import com.greenfoxacademy.ebayclone.models.Admin;
import com.greenfoxacademy.ebayclone.models.Buyer;
import com.greenfoxacademy.ebayclone.models.Seller;
import com.greenfoxacademy.ebayclone.repositories.UserRepo;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserManagementServiceImpl implements UserManagementService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    public UserManagementServiceImpl(
            UserRepo userRepo,
            PasswordEncoder passwordEncoder
    ) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void createNewUser(String userType, UserDTO userDTO) throws UsernameAlreadyInUseException {
        if (this.userRepo.existsByUsername(userDTO.getUsername())) {
            throw new UsernameAlreadyInUseException();
        }
        userDTO.setPassword(this.passwordEncoder.encode(userDTO.getPassword()));
        switch (userType.toLowerCase().trim()) {
            case "admin" -> createAdmin(userDTO);
            case "seller" -> createSeller(userDTO);
            case "buyer" -> createBuyer(userDTO);
            default -> throw new IllegalArgumentException();
        }
    }
    private void createAdmin(UserDTO userDTO) {
        this.userRepo.save(new Admin(userDTO.getUsername(), userDTO.getPassword()));
    }
    private void createSeller(UserDTO userDTO) {
        this.userRepo.save(new Seller(userDTO.getUsername(), userDTO.getPassword()));
    }
    private void createBuyer(UserDTO userDTO) {
        this.userRepo.save(new Buyer(userDTO.getUsername(), userDTO.getPassword()));
    }
}
