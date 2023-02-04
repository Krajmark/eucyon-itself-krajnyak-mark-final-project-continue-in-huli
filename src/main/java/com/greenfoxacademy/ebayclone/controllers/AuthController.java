package com.greenfoxacademy.ebayclone.controllers;


import com.greenfoxacademy.ebayclone.dtos.user.TokenDTO;
import com.greenfoxacademy.ebayclone.dtos.user.UserDTO;
import com.greenfoxacademy.ebayclone.exeptions.user.UsernameAlreadyInUseException;
import com.greenfoxacademy.ebayclone.security.JwtProviderService;
import com.greenfoxacademy.ebayclone.services.UserManagementService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final JwtProviderService jwtProviderService;
    private final UserManagementService userManagementService;

    public AuthController(
            JwtProviderService jwtProviderService,
            UserManagementService userManagementService
    ) {
        this.jwtProviderService = jwtProviderService;
        this.userManagementService = userManagementService;
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDTO> sendToken(@RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(new TokenDTO(this.jwtProviderService.generateTokenByUserLoginRequest(userDTO)));
    }

    @PostMapping("/register/{userType}")
    public ResponseEntity<?> createAccount(
            @RequestBody UserDTO userDTO,
            @PathVariable String userType
    ) throws UsernameAlreadyInUseException {
        this.userManagementService.createNewUser(userType, userDTO);
        return ResponseEntity.created(null).build();
    }
}
