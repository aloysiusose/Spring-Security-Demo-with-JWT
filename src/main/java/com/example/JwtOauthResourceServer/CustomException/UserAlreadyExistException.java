package com.example.JwtOauthResourceServer.CustomException;

public class UserAlreadyExistException extends Exception{
    public UserAlreadyExistException(String message) {
        super(message);
    }
}
