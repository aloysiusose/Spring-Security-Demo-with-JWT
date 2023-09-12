package com.example.JwtOauthResourceServer.Controllers;

import com.example.JwtOauthResourceServer.Models.ApplicationUsers;
import com.example.JwtOauthResourceServer.Models.AuthenticationRequest;
import com.example.JwtOauthResourceServer.Models.Roles;
import com.example.JwtOauthResourceServer.Service.AppUserService;
import com.example.JwtOauthResourceServer.Service.AuthenticationService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.junit.jupiter.api.Assertions.*;
//@SpringBootTest
@WebMvcTest(AuthenticationController.class)
@ExtendWith(MockitoExtension.class)
class AuthenticationControllerTest {
    @Autowired
    private WebApplicationContext context;
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    private AppUserService appUserService;
    @MockBean
    private AuthenticationService authenticationService;
@BeforeEach
    void setUp(){
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    void registerUser() throws Exception {
        ApplicationUsers user1 = new ApplicationUsers();
        user1.setPassword("12345"); user1.setEmail("jean"); user1.setRole(Roles.USER);

        String request = objectMapper.writeValueAsString(user1);

        mockMvc.perform(post("/api/v1/auth/register").with(csrf()).contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isOk());

    }

    @Test
    void authenticateUser() throws Exception {
        AuthenticationRequest authRequest = new AuthenticationRequest();
        authRequest.setPassword("12345");
        authRequest.setUsername("john");

        String authenticationRequest = objectMapper.writeValueAsString(authRequest);

        mockMvc.perform(post("/api/v1/auth/authenticate").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authenticationRequest))
                .andExpect(status().isOk())
                .andReturn();

    }
}