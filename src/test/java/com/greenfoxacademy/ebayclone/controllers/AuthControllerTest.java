package com.greenfoxacademy.ebayclone.controllers;

import com.greenfoxacademy.ebayclone.services.UserManagementService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserManagementService userManagementService;

    @Test
    void sendToken() throws Exception {
        when(this.userManagementService.processLoginRequest(any(), any())).thenReturn("asd");
        this.mockMvc.perform(MockMvcRequestBuilders.post("/auth/login").content("""
                                {
                                    "username": "admin123",
                                    "password": "asdasd"
                                }      
                                """)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json(
                        """
                                {
                                    "token": "asd"
                                }
                                """
                ));
        verify(this.userManagementService, times(1)).processLoginRequest(any(), any());
    }

    @Test
    void createAccount() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.post("/auth/register/asdasd").content("""
                {
                    "username": "admin123",
                    "password": "asdasd"
                }
                """)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated());
        verify(this.userManagementService, times(1)).createNewUser(anyString(),any(), any());
    }
}