package com.greenfoxacademy.ebayclone.services;

import com.greenfoxacademy.ebayclone.dtos.user.LoginResponseDTO;
import com.greenfoxacademy.ebayclone.dtos.user.UserCreationDTO;
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

import static org.junit.jupiter.api.Assertions.*;
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
    UserCreationDTO userCreationDTO;

    @BeforeEach
    public void setup() {
        this.userCreationDTO = new UserCreationDTO("testName", "testPassword");
        this.userManagementService = new UserManagementServiceImpl(
                this.userRepo,
                this.passwordEncoder,
                this.jwtProviderService,
                this.bindingResultHandlerService
        );
    }

    @Test
    void processLoginRequestShouldReturnWithCorrectLoginResponse() throws PasswordInvalidException {
        UserCreationDTO userCreationDTO = this.userCreationDTO;
        User user = new User();
        user.setBalance(0);
        LoginResponseDTO expected = new LoginResponseDTO("asd", 0);

        doNothing().when(this.bindingResultHandlerService).handleBindingResult(this.bindingResult);
        when(this.userRepo.findUserByUsername(userCreationDTO.getUsername())).thenReturn(Optional.of(user));
        when(this.jwtProviderService.generateTokenByUserLoginRequest(userCreationDTO)).thenReturn("asd");
        LoginResponseDTO actual = this.userManagementService.processLoginRequest(userCreationDTO, this.bindingResult);

        assertEquals(expected, actual);
        verify(this.userRepo, times(1)).findUserByUsername(userCreationDTO.getUsername());
        verify(this.jwtProviderService, times(1)).generateTokenByUserLoginRequest(userCreationDTO);
    }

    @ParameterizedTest
    @ValueSource(strings = {"seller", "buyer", "admin", " admin   ", "ADMIN", "AdMiN"})
    void createNewUserShouldSucceed(String usertype) throws UsernameAlreadyInUseException {
        Boolean userAlreadyExists = false;

        doNothing().when(this.bindingResultHandlerService).handleBindingResult(this.bindingResult);
        when(this.userRepo.existsByUsername(this.userCreationDTO.getUsername())).thenReturn(userAlreadyExists);
        when(this.passwordEncoder.encode(this.userCreationDTO.getPassword())).thenReturn("testPassword");
        assertTrue(this.userManagementService.createNewUser(usertype, this.userCreationDTO, this.bindingResult));

        verify(this.userRepo, times(1)).existsByUsername(this.userCreationDTO.getUsername());
        verify(this.passwordEncoder, times(1)).encode(this.userCreationDTO.getPassword());
    }

    @ParameterizedTest
    @ValueSource(strings = {"seller", "buyer", "admin", " admin   ", "ADMIN", "AdMiN"})
    void createNewUserShouldThrowUsernameAlreadyInUseExc(String usertype) {
        Boolean userAlreadyExists = true;

        doNothing().when(this.bindingResultHandlerService).handleBindingResult(this.bindingResult);
        when(this.userRepo.existsByUsername(this.userCreationDTO.getUsername())).thenReturn(userAlreadyExists);
        assertThrows(
                UsernameAlreadyInUseException.class,
                () -> this.userManagementService.createNewUser(usertype, this.userCreationDTO, this.bindingResult)
        );

        verify(this.userRepo, times(1)).existsByUsername(this.userCreationDTO.getUsername());
        verify(this.passwordEncoder, times(0)).encode(this.userCreationDTO.getPassword());
    }

    @ParameterizedTest
    @ValueSource(strings = {"asd", " ", ""})
    void createNewUserShouldThrowIllegalArgumentExc(String usertype) {
        Boolean userAlreadyExists = false;

        doNothing().when(this.bindingResultHandlerService).handleBindingResult(this.bindingResult);
        when(this.userRepo.existsByUsername(this.userCreationDTO.getUsername())).thenReturn(userAlreadyExists);
        assertThrows(
                IllegalArgumentException.class,
                () -> this.userManagementService.createNewUser(usertype, this.userCreationDTO, this.bindingResult)
        );

        verify(this.userRepo, times(1)).existsByUsername(this.userCreationDTO.getUsername());
        verify(this.passwordEncoder, times(0)).encode(this.userCreationDTO.getPassword());
    }

}