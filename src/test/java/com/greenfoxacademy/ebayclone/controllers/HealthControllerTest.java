package com.greenfoxacademy.ebayclone.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
class HealthControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void healthCheck() throws Exception {
        mockMvc.perform(get("/health/check")).andExpect(status().isOk());
    }

    @Test
    void indexPage() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(content().json(
                        """
                                {
                                  "hi": "Hi!",
                                  "welcome": "Welcome to this secret page!",
                                  "credits": [
                                    "Krajnyák Márk",
                                    "ASDASD",
                                    "@GreenFoxAcademy",
                                    "https://www.greenfoxacademy.com/"
                                  ],
                                  "advice": [
                                    "Also if u are using chrome and NOT using this, please do it NOW!!!",
                                    "https://chrome.google.com/webstore/detail/json-viewer/gbmdgpbipfallnflgajpaliibnhdgobh"
                                  ]
                                }
                                """
                ));
    }
}