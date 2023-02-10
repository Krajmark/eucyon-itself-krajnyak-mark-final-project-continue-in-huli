package com.greenfoxacademy.ebayclone.services;

import com.greenfoxacademy.ebayclone.dtos.user.LoginResponseDTO;
import com.greenfoxacademy.ebayclone.dtos.user.UserDTO;
import com.greenfoxacademy.ebayclone.exeptions.user.PasswordInvalidException;
import com.greenfoxacademy.ebayclone.exeptions.user.UsernameAlreadyInUseException;
import com.greenfoxacademy.ebayclone.models.User;
import com.greenfoxacademy.ebayclone.repositories.UserRepo;
import com.greenfoxacademy.ebayclone.security.JwtProviderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class UserManagementServiceImplTest {
    @Mock
    BindingResult bindingResult;
    @Mock
    UserRepo userRepo;
    @Mock
    JwtProviderService jwtProviderService;
    @Mock
    PasswordEncoder passwordEncoder;
    @Mock
    BindingResultHandlerService bindingResultHandlerService;
    UserManagementServiceImpl userManagementService;
    UserDTO userDTO;

    @BeforeEach
    public void setup() {
        this.userDTO = new UserDTO("testName", "testPassword");
        this.userManagementService = new UserManagementServiceImpl(
                this.userRepo,
                this.passwordEncoder,
                this.jwtProviderService,
                this.bindingResultHandlerService
        );
    }

    @Test
    void processLoginRequestShouldReturnWithCorrectLoginResponse() throws PasswordInvalidException {
        UserDTO userDTO = this.userDTO;
        User user = new User();
        user.setBalance(0);
        LoginResponseDTO expected = new LoginResponseDTO("asd", 0);

        doNothing().when(this.bindingResultHandlerService).handleBindingResult(this.bindingResult);
        when(this.userRepo.findUserByUsername(userDTO.getUsername())).thenReturn(Optional.of(user));
        when(this.jwtProviderService.generateTokenByUserLoginRequest(userDTO)).thenReturn("asd");
        LoginResponseDTO actual = this.userManagementService.processLoginRequest(userDTO, this.bindingResult);

        assertEquals(expected, actual);
        verify(this.userRepo, times(1)).findUserByUsername(userDTO.getUsername());
        verify(this.jwtProviderService, times(1)).generateTokenByUserLoginRequest(userDTO);
    }

    @ParameterizedTest
    @ValueSource(strings = {"seller", "buyer", "admin", " admin   ", "ADMIN", "AdMiN"})
    void createNewUserShouldSucceed(String usertype) throws UsernameAlreadyInUseException {
        Boolean userAlreadyExists = false;

        doNothing().when(this.bindingResultHandlerService).handleBindingResult(this.bindingResult);
        when(this.userRepo.existsByUsername(this.userDTO.getUsername())).thenReturn(userAlreadyExists);
        when(this.passwordEncoder.encode(this.userDTO.getPassword())).thenReturn("testPassword");
        this.userManagementService.createNewUser(usertype, this.userDTO, this.bindingResult);

        verify(this.userRepo, times(1)).existsByUsername(this.userDTO.getUsername());
        verify(this.passwordEncoder, times(1)).encode(this.userDTO.getPassword());
    }

    @ParameterizedTest
    @ValueSource(strings = {"seller", "buyer", "admin", " admin   ", "ADMIN", "AdMiN"})
    void createNewUserShouldThrowUsernameAlreadyInUseExc(String usertype) {
        Boolean userAlreadyExists = true;

        doNothing().when(this.bindingResultHandlerService).handleBindingResult(this.bindingResult);
        when(this.userRepo.existsByUsername(this.userDTO.getUsername())).thenReturn(userAlreadyExists);
        assertThrows(
                UsernameAlreadyInUseException.class,
                () -> this.userManagementService.createNewUser(usertype, this.userDTO, this.bindingResult)
        );

        verify(this.userRepo, times(1)).existsByUsername(this.userDTO.getUsername());
        verify(this.passwordEncoder, times(0)).encode(this.userDTO.getPassword());
    }

    @ParameterizedTest
    @ValueSource(strings = {"asd", " ", ""})
    void createNewUserShouldThrowIllegalArgumentExc(String usertype) {
        Boolean userAlreadyExists = false;

        doNothing().when(this.bindingResultHandlerService).handleBindingResult(this.bindingResult);
        when(this.userRepo.existsByUsername(this.userDTO.getUsername())).thenReturn(userAlreadyExists);
        assertThrows(
                IllegalArgumentException.class,
                () -> this.userManagementService.createNewUser(usertype, this.userDTO, this.bindingResult)
        );

        verify(this.userRepo, times(1)).existsByUsername(this.userDTO.getUsername());
        verify(this.passwordEncoder, times(0)).encode(this.userDTO.getPassword());
    }

}