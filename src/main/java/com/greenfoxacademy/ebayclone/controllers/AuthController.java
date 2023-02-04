package com.greenfoxacademy.ebayclone.controllers;


import com.greenfoxacademy.ebayclone.dtos.user.TokenDTO;
import com.greenfoxacademy.ebayclone.dtos.user.UserDTO;
import com.greenfoxacademy.ebayclone.exeptions.user.UsernameAlreadyInUseException;
import com.greenfoxacademy.ebayclone.security.JwtProviderService;
import com.greenfoxacademy.ebayclone.services.UserManagementService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UserManagementService userManagementService;

    public AuthController(
            UserManagementService userManagementService
    ) {
        this.userManagementService = userManagementService;
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDTO> sendToken(
            @Valid @RequestBody UserDTO userDTO,
            BindingResult bindingResult
    ) {
        return ResponseEntity.ok(new TokenDTO(this.userManagementService.processLoginRequest(userDTO, bindingResult)));
    }

    @PostMapping("/register/{userType}")
    public ResponseEntity<?> createAccount(
            @Valid @RequestBody UserDTO userDTO,
            BindingResult bindingResult,
            @PathVariable String userType
    ) throws UsernameAlreadyInUseException {
        this.userManagementService.createNewUser(userType, userDTO, bindingResult);
        return ResponseEntity.created(null).build();
    }
}
