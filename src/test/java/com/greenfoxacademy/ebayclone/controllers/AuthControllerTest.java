package com.greenfoxacademy.ebayclone.controllers;

import com.greenfoxacademy.ebayclone.exeptions.user.PasswordInvalidException;
import com.greenfoxacademy.ebayclone.exeptions.user.UsernameAlreadyInUseException;
import com.greenfoxacademy.ebayclone.services.UserManagementService;
import jakarta.validation.ValidationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserManagementService userManagementService;

    @Test
    void sendTokenShouldSucceedWith200AndCorrectToken() throws Exception {
        when(this.userManagementService.processLoginRequest(any(), any())).thenReturn("asd");
        this.mockMvc.perform(post("/auth/login").content("""
                                {
                                    "username": "admin123",
                                    "password": "asdasd"
                                }      
                                """)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(
                        """
                                {
                                    "token": "asd"
                                }
                                """
                ));
        verify(this.userManagementService, times(1)).processLoginRequest(any(), any());
    }

    @Test
    void sendTokenShouldFailWith404AndCorrectMessage() throws Exception {
        when(this.userManagementService.processLoginRequest(any(), any()))
                .thenThrow(new UsernameNotFoundException("No such user!"));
        this.mockMvc.perform(post("/auth/login").content("""
                                {
                                    "username": "admin123",
                                    "password": "asdasd"
                                }      
                                """)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().json(
                        """
                                {
                                    "message": [
                                        "No such user!"
                                    ]
                                }
                                """
                ));
        verify(this.userManagementService, times(1)).processLoginRequest(any(), any());
    }

    @Test
    void sendTokenShouldFailWith403AndCorrectMessage() throws Exception {
        when(this.userManagementService.processLoginRequest(any(), any()))
                .thenThrow(new PasswordInvalidException("Invalid username or password."));
        this.mockMvc.perform(post("/auth/login").content("""
                                {
                                    "username": "admin123",
                                    "password": "asdasd"
                                }      
                                """)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden())
                .andExpect(content().json(
                        """
                                {
                                     "message": [
                                         "Invalid username or password."
                                     ]
                                }
                                """
                ));
        verify(this.userManagementService, times(1)).processLoginRequest(any(), any());
    }

    @Test
    void sendTokenShouldFailWith400AndCorrectMessage() throws Exception {
        when(this.userManagementService.processLoginRequest(any(), any()))
                .thenThrow(new ValidationException("asd"));
        this.mockMvc.perform(post("/auth/login").content("""
                                {
                                    "username": "admin123",
                                    "password": "asdasd"
                                }      
                                """)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().json(
                        """
                                {
                                     "message": [
                                         "asd"
                                     ]
                                }
                                """
                ));
        verify(this.userManagementService, times(1)).processLoginRequest(any(), any());
    }

    @Test
    void createAccountShouldSucceedWith201() throws Exception {
        this.mockMvc.perform(post("/auth/register/asd").content("""
                                {
                                    "username": "admin123",
                                    "password": "asdasd"
                                }
                                """)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
        verify(this.userManagementService, times(1)).createNewUser(anyString(), any(), any());
    }

    @Test
    void createAccountShouldFailWith406AndCorrectMessage_UsernameAlrInUse() throws Exception {
        doThrow(UsernameAlreadyInUseException.class)
                .when(this.userManagementService).createNewUser(anyString(), any(), any());
        this.mockMvc.perform(post("/auth/register/asd").content("""
                                {
                                    "username": "admin123",
                                    "password": "asdasd"
                                }
                                """)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict())
                .andExpect(content().json(
                        """
                                {
                                    "message": [
                                        "Username already in use!"
                                    ]
                                }
                                """
                ));
        verify(this.userManagementService, times(1)).createNewUser(anyString(), any(), any());
    }

    @Test
    void createAccountShouldFailWith400AndCorrectMessage_IllegalArg() throws Exception {
        doThrow(new IllegalArgumentException("Not a supported user-type!"))
                .when(this.userManagementService).createNewUser(anyString(), any(), any());
        this.mockMvc.perform(post("/auth/register/asd").content("""
                                {
                                    "username": "admin123",
                                    "password": "asdasd"
                                }
                                """)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().json(
                        """
                                {
                                    "message": [
                                        "Not a supported user-type!"
                                    ]
                                }
                                """
                ));
        verify(this.userManagementService, times(1)).createNewUser(anyString(), any(), any());
    }

    @Test
    void createAccountShouldFailWith400AndCorrectMessage_Validation() throws Exception {
        doThrow(new ValidationException("asd"))
                .when(this.userManagementService).createNewUser(anyString(), any(), any());
        this.mockMvc.perform(post("/auth/register/asd").content("""
                                {
                                    "username": "admin123",
                                    "password": "asdasd"
                                }
                                """)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().json(
                        """
                                {
                                    "message": [
                                        "asd"
                                    ]
                                }
                                """
                ));
        verify(this.userManagementService, times(1)).createNewUser(anyString(), any(), any());
    }
}