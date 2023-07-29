package com.example.JwtOauthResourceServer.Service;

import com.example.JwtOauthResourceServer.CustomException.UserAlreadyExistException;
import com.example.JwtOauthResourceServer.Models.ApplicationUsers;
import com.example.JwtOauthResourceServer.Repository.ApplicationUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AppUserService {

    private final ApplicationUserRepository applicationUserRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    public void registerUser(ApplicationUsers applicationUsers) throws UserAlreadyExistException {
        boolean present = applicationUserRepository.findByEmail(applicationUsers.getEmail()).isPresent();

        if(present){
            throw  new UserAlreadyExistException(String.format("User with username: %s already exist!", applicationUsers.getEmail()));
        }
        applicationUsers.setPassword(passwordEncoder.encode(applicationUsers.getPassword()));
        applicationUserRepository.save(applicationUsers);

    }
}
