package com.example.JwtOauthResourceServer.Models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class AuthenticationResponse {

    private String token;
}
