package com.example.JwtOauthResourceServer.Repository;

import com.example.JwtOauthResourceServer.Models.ApplicationUsers;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ApplicationUserRepository extends JpaRepository<ApplicationUsers, Integer> {
    Optional<ApplicationUsers> findByEmail(String email);
}
