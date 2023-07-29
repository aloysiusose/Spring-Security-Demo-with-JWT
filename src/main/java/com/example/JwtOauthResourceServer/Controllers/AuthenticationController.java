package com.example.JwtOauthResourceServer.Controllers;

import com.example.JwtOauthResourceServer.CustomException.UserAlreadyExistException;
import com.example.JwtOauthResourceServer.Models.ApplicationUsers;
import com.example.JwtOauthResourceServer.Models.AuthenticationRequest;
import com.example.JwtOauthResourceServer.Service.AppUserService;
import com.example.JwtOauthResourceServer.Service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/auth/")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final AppUserService appUserService;

    @PostMapping("/register")
    public void registerUser(@RequestBody ApplicationUsers applicationUsers) throws UserAlreadyExistException {

        appUserService.registerUser(applicationUsers);

    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticateUser(@RequestBody AuthenticationRequest authenticationRequest){

         return ResponseEntity.ok(authenticationService.authenticateUser(authenticationRequest));
    }

    @ExceptionHandler
    public ResponseEntity<?> handleException(Exception ex){

        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}
