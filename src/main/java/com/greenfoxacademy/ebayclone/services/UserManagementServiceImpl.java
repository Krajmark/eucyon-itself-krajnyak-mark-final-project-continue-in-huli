package com.greenfoxacademy.ebayclone.services;

import com.greenfoxacademy.ebayclone.dtos.user.UserDTO;
import com.greenfoxacademy.ebayclone.exeptions.user.PasswordInvalidException;
import com.greenfoxacademy.ebayclone.exeptions.user.UsernameAlreadyInUseException;
import com.greenfoxacademy.ebayclone.models.Admin;
import com.greenfoxacademy.ebayclone.models.Buyer;
import com.greenfoxacademy.ebayclone.models.Seller;
import com.greenfoxacademy.ebayclone.repositories.UserRepo;
import com.greenfoxacademy.ebayclone.security.JwtProviderService;
import jakarta.validation.ValidationException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.Objects;

@Service
public class UserManagementServiceImpl implements UserManagementService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtProviderService jwtProviderService;

    public UserManagementServiceImpl(
            UserRepo userRepo,
            PasswordEncoder passwordEncoder,
            JwtProviderService jwtProviderService
    ) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.jwtProviderService = jwtProviderService;
    }

    @Override
    public String processLoginRequest(UserDTO userDTO, BindingResult bindingResult) throws PasswordInvalidException {
        handleBindingResult(bindingResult);
        return this.jwtProviderService.generateTokenByUserLoginRequest(userDTO);
    }

    @Override
    public void createNewUser(String userType, UserDTO userDTO, BindingResult bindingResult) throws UsernameAlreadyInUseException {
        handleBindingResult(bindingResult);
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

    private void handleBindingResult(BindingResult bindingResult) throws ValidationException {
        if (bindingResult.hasErrors()) {
            var asd = bindingResult.getFieldErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .reduce("", (string, snippet) -> string + (string.isBlank() || Objects.requireNonNull(snippet).isBlank() ? "" : ", ") + snippet);
            throw new ValidationException(
                    asd
            );
        }
    }
}
