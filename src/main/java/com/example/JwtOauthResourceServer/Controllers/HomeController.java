package com.example.JwtOauthResourceServer.Controllers;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.awt.*;

@RestController
public class HomeController {

    @GetMapping("/home")
    public String greet(Authentication authentication){

        return String.format("Hello user with username: %s", authentication.getName());
    }

    @GetMapping(value = "/public")
    public String hello(){
        return "Every user can view this page";
    }
}
