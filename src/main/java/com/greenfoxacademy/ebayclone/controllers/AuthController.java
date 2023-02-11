package com.greenfoxacademy.ebayclone.controllers;


import com.greenfoxacademy.ebayclone.dtos.user.LoginResponseDTO;
import com.greenfoxacademy.ebayclone.dtos.user.UserCreationDTO;
import com.greenfoxacademy.ebayclone.exeptions.user.PasswordInvalidException;
import com.greenfoxacademy.ebayclone.exeptions.user.UsernameAlreadyInUseException;
import com.greenfoxacademy.ebayclone.services.UserManagementService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UserManagementService userManagementService;

    public AuthController(UserManagementService userManagementService) {
        this.userManagementService = userManagementService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> sendToken(
            @Valid @RequestBody UserCreationDTO userCreationDTO,
            BindingResult bindingResult
    ) throws PasswordInvalidException {
        return ResponseEntity.ok(this.userManagementService.processLoginRequest(userCreationDTO, bindingResult));
    }

    @PostMapping("/register/{userType}")
    public ResponseEntity<?> createAccount(
            @Valid @RequestBody UserCreationDTO userCreationDTO,
            BindingResult bindingResult,
            @PathVariable String userType
    ) throws UsernameAlreadyInUseException {
        this.userManagementService.createNewUser(userType, userCreationDTO, bindingResult);
        return ResponseEntity.created(null).build();
    }
}
