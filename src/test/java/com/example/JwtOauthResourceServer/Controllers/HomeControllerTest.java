package com.example.JwtOauthResourceServer.Controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
class HomeControllerTest {

    @Autowired
    private WebApplicationContext context;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp(){
        mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
    }


    @Test
    void greet() throws Exception {
        Jwt jwtToken = Jwt.withTokenValue("token").header("alg", "none").subject("Tony")
                .claim("scope", "ADMIN").build();

        mockMvc.perform(get("/home").with(jwt().jwt(jwtToken)))
                .andExpect(status().isOk())
                .andExpect(content().string("Hello user with username: Tony"));

    }

    @Test
    void userWithoutScopeAdminCannotAccessGreet() throws Exception {
        Jwt jwtToken = Jwt.withTokenValue("token").header("alg", "none").subject("Tony")
                .claim("scope", "USER").build();

        mockMvc.perform(get("/home").with(jwt().jwt(jwtToken)))
                .andExpect(status().isForbidden());
    }



    @Test
    void userWithScopeAdminCanAccessHello() throws Exception {
        Jwt jwtToken = Jwt.withTokenValue("token").header("alg", "none").subject("Tony")
                .claim("scope", "ADMIN").build();

        mockMvc.perform(get("/public").with(jwt().jwt(jwtToken)))
                .andExpect(status().isOk())
                .andExpect(content().string("Every user can view this page"));
    }
    @Test
    void userWithScopeUserCanAccessHello() throws Exception {
        Jwt jwtToken = Jwt.withTokenValue("token").header("alg", "none").subject("Tony")
                .claim("scope", "USER").build();

        mockMvc.perform(get("/public").with(jwt().jwt(jwtToken)))
                .andExpect(status().isOk())
                .andExpect(content().string("Every user can view this page"));
    }
    @Test
    void unauthenticatedUserCanAccessHello() throws Exception {


        mockMvc.perform(get("/public"))
                .andExpect(status().isOk())
                .andExpect(content().string("Every user can view this page"));
    }
}