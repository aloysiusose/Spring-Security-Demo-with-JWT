package com.example.JwtOauthResourceServer.Service;

import com.example.JwtOauthResourceServer.Models.AuthenticationRequest;
import com.example.JwtOauthResourceServer.Models.AuthenticationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthenticationService {

    private final TokenService tokenService;

    private final AuthenticationManager authenticationManager;
    public AuthenticationResponse authenticateUser(AuthenticationRequest authenticationRequest) {

        Authentication authenticationObject = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),
                        authenticationRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authenticationObject);

        String s = tokenService.generateToken(authenticationObject);

        return new AuthenticationResponse(s);

    }
}
