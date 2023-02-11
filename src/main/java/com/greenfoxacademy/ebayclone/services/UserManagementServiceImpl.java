package com.greenfoxacademy.ebayclone.services;

import com.greenfoxacademy.ebayclone.dtos.user.LoginResponseDTO;
import com.greenfoxacademy.ebayclone.dtos.user.UserCreationDTO;
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
    public LoginResponseDTO processLoginRequest(UserCreationDTO userCreationDTO, BindingResult bindingResult) throws PasswordInvalidException {
        this.bindingResultHandlerService.handleBindingResult(bindingResult);
        String token = this.jwtProviderService.generateTokenByUserLoginRequest(userCreationDTO);
        Integer balance = this.userRepo.findUserByUsername(userCreationDTO.getUsername()).get().getBalance();
        return new LoginResponseDTO(token, balance);
    }

    @Override
    public boolean createNewUser(String userType, UserCreationDTO userCreationDTO, BindingResult bindingResult) throws UsernameAlreadyInUseException {
        this.bindingResultHandlerService.handleBindingResult(bindingResult);
        if (this.userRepo.existsByUsername(userCreationDTO.getUsername())) {
            throw new UsernameAlreadyInUseException();
        }
        userCreationDTO.setPassword(this.passwordEncoder.encode(userCreationDTO.getPassword()));
        switch (userType.toLowerCase().trim()) {
            case "admin" -> createAdmin(userCreationDTO);
            case "seller" -> createSeller(userCreationDTO);
            case "buyer" -> createBuyer(userCreationDTO);
            default -> throw new IllegalArgumentException("Not a supported user-type!");
        }
        return true;
    }

    private void createAdmin(UserCreationDTO userCreationDTO) {
        this.userRepo.save(new Admin(userCreationDTO.getUsername(), userCreationDTO.getPassword()));
    }

    private void createSeller(UserCreationDTO userCreationDTO) {
        this.userRepo.save(new Seller(userCreationDTO.getUsername(), userCreationDTO.getPassword(), 0));
    }

    private void createBuyer(UserCreationDTO userCreationDTO) {
        this.userRepo.save(new Buyer(userCreationDTO.getUsername(), userCreationDTO.getPassword(), 0));
    }
}
