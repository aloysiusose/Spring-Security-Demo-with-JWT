package com.example.JwtOauthResourceServer.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class ApplicationUsers {
    @Id
    private int id;
    private String email;
    private String password;
}
