package com.greenfoxacademy.ebayclone.services;

import com.greenfoxacademy.ebayclone.dtos.user.LoginResponseDTO;
import com.greenfoxacademy.ebayclone.dtos.user.UserDTO;
import com.greenfoxacademy.ebayclone.exeptions.user.PasswordInvalidException;
import com.greenfoxacademy.ebayclone.exeptions.user.UsernameAlreadyInUseException;
import com.greenfoxacademy.ebayclone.models.Admin;
import com.greenfoxacademy.ebayclone.models.Buyer;
import com.greenfoxacademy.ebayclone.models.Seller;
import com.greenfoxacademy.ebayclone.repositories.UserRepo;
import com.greenfoxacademy.ebayclone.security.JwtProviderService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

@Service
public class UserManagementServiceImpl implements UserManagementService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtProviderService jwtProviderService;

    private final BindingResultHandlerService bindingResultHandlerService;

    public UserManagementServiceImpl(
            UserRepo userRepo,
            PasswordEncoder passwordEncoder,
            JwtProviderService jwtProviderService,
            BindingResultHandlerService bindingResultHandlerService) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.jwtProviderService = jwtProviderService;
        this.bindingResultHandlerService = bindingResultHandlerService;
    }

    @Override
    public LoginResponseDTO processLoginRequest(UserDTO userDTO, BindingResult bindingResult) throws PasswordInvalidException {
        this.bindingResultHandlerService.handleBindingResult(bindingResult);
        String token = this.jwtProviderService.generateTokenByUserLoginRequest(userDTO);
        Integer balance = this.userRepo.findUserByUsername(userDTO.getUsername()).get().getBalance();
        return new LoginResponseDTO(token, balance);
    }

    @Override
    public void createNewUser(String userType, UserDTO userDTO, BindingResult bindingResult) throws UsernameAlreadyInUseException {
        this.bindingResultHandlerService.handleBindingResult(bindingResult);
        if (this.userRepo.existsByUsername(userDTO.getUsername())) {
            throw new UsernameAlreadyInUseException();
        }
        userDTO.setPassword(this.passwordEncoder.encode(userDTO.getPassword()));
        switch (userType.toLowerCase().trim()) {
            case "admin" -> createAdmin(userDTO);
            case "seller" -> createSeller(userDTO);
            case "buyer" -> createBuyer(userDTO);
            default -> throw new IllegalArgumentException("Not a supported user-type!");
        }
    }

    private void createAdmin(UserDTO userDTO) {
        this.userRepo.save(new Admin(userDTO.getUsername(), userDTO.getPassword()));
    }

    private void createSeller(UserDTO userDTO) {
        this.userRepo.save(new Seller(userDTO.getUsername(), userDTO.getPassword(), 0));
    }

    private void createBuyer(UserDTO userDTO) {
        this.userRepo.save(new Buyer(userDTO.getUsername(), userDTO.getPassword(), 0));
    }
}
