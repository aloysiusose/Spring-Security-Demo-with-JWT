[1mdiff --git a/src/main/java/com/example/JwtOauthResourceServer/Models/ApplicationUsers.java b/src/main/java/com/example/JwtOauthResourceServer/Models/ApplicationUsers.java[m
[1mindex babb8ed..c16431a 100644[m
[1m--- a/src/main/java/com/example/JwtOauthResourceServer/Models/ApplicationUsers.java[m
[1m+++ b/src/main/java/com/example/JwtOauthResourceServer/Models/ApplicationUsers.java[m
[36m@@ -1,6 +1,8 @@[m
 package com.example.JwtOauthResourceServer.Models;[m
 [m
 import jakarta.persistence.Entity;[m
[32m+[m[32mimport jakarta.persistence.EnumType;[m
[32m+[m[32mimport jakarta.persistence.Enumerated;[m
 import jakarta.persistence.Id;[m
 import lombok.Getter;[m
 import lombok.Setter;[m
[36m@@ -13,4 +15,6 @@[m [mpublic class ApplicationUsers {[m
     private int id;[m
     private String email;[m
     private String password;[m
[32m+[m[32m    @Enumerated(EnumType.STRING)[m
[32m+[m[32m    private Roles role;[m
 }[m
[1mdiff --git a/src/main/java/com/example/JwtOauthResourceServer/Security/CustomUserDetails.java b/src/main/java/com/example/JwtOauthResourceServer/Security/CustomUserDetails.java[m
[1mindex b09779b..809a0fe 100644[m
[1m--- a/src/main/java/com/example/JwtOauthResourceServer/Security/CustomUserDetails.java[m
[1m+++ b/src/main/java/com/example/JwtOauthResourceServer/Security/CustomUserDetails.java[m
[36m@@ -6,6 +6,8 @@[m [mimport org.springframework.security.core.GrantedAuthority;[m
 import org.springframework.security.core.userdetails.UserDetails;[m
 [m
 import java.util.Collection;[m
[32m+[m[32mimport java.util.List;[m
[32m+[m
 @AllArgsConstructor[m
 public class CustomUserDetails implements UserDetails {[m
 [m
[36m@@ -22,7 +24,7 @@[m [mpublic class CustomUserDetails implements UserDetails {[m
     }[m
     @Override[m
     public Collection<? extends GrantedAuthority> getAuthorities() {[m
[31m-        return null;[m
[32m+[m[32m        return List.of(()-> applicationUsers.getRole().name());[m
     }[m
 [m
     @Override[m
[1mdiff --git a/src/main/java/com/example/JwtOauthResourceServer/Security/SecurityConfig.java b/src/main/java/com/example/JwtOauthResourceServer/Security/SecurityConfig.java[m
[1mindex 2a1638a..f769cd9 100644[m
[1m--- a/src/main/java/com/example/JwtOauthResourceServer/Security/SecurityConfig.java[m
[1m+++ b/src/main/java/com/example/JwtOauthResourceServer/Security/SecurityConfig.java[m
[36m@@ -65,6 +65,7 @@[m [mpublic class SecurityConfig {[m
                     auth.requestMatchers("/favicon.svg").permitAll();[m
                     auth.requestMatchers("/css/*").permitAll();[m
                     auth.requestMatchers("/error").permitAll();[m
[32m+[m[32m                    auth.requestMatchers("/home").hasAuthority("SCOPE_ADMIN");[m
                     auth.anyRequest().authenticated();})[m
                 .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))[m
                 .oauth2ResourceServer(oauth -> oauth.jwt(Customizer.withDefaults()));[m
[36m@@ -72,14 +73,8 @@[m [mpublic class SecurityConfig {[m
 [m
         return httpSecurity.build();[m
     }[m
[31m-    /*[m
[31m-    i need to expose a decoder[m
[31m-    also expose an encoder[m
[31m-    create a key pair generator class[m
 [m
[31m-     */[m
[31m-    @Bean[m
[31m-    public KeyPair keyPair() throws NoSuchAlgorithmException {[m
[32m+[m[32m    private static KeyPair keyPair() throws NoSuchAlgorithmException {[m
 [m
             KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");[m
             keyPairGenerator.initialize(2048);[m
[1mdiff --git a/src/main/java/com/example/JwtOauthResourceServer/Service/AuthenticationService.java b/src/main/java/com/example/JwtOauthResourceServer/Service/AuthenticationService.java[m
[1mindex 6acd42e..b54728a 100644[m
[1m--- a/src/main/java/com/example/JwtOauthResourceServer/Service/AuthenticationService.java[m
[1m+++ b/src/main/java/com/example/JwtOauthResourceServer/Service/AuthenticationService.java[m
[36m@@ -24,9 +24,9 @@[m [mpublic class AuthenticationService {[m
 [m
         SecurityContextHolder.getContext().setAuthentication(authenticationObject);[m
 [m
[31m-        String s = tokenService.generateToken(authenticationObject);[m
[32m+[m[32m        String token = tokenService.generateToken(authenticationObject);[m
 [m
[31m-        return new AuthenticationResponse(s);[m
[32m+[m[32m        return new AuthenticationResponse(token);[m
 [m
     }[m
 }[m
[1mdiff --git a/src/main/java/com/example/JwtOauthResourceServer/Service/TokenService.java b/src/main/java/com/example/JwtOauthResourceServer/Service/TokenService.java[m
[1mindex b77b3b0..1b42e6b 100644[m
[1m--- a/src/main/java/com/example/JwtOauthResourceServer/Service/TokenService.java[m
[1m+++ b/src/main/java/com/example/JwtOauthResourceServer/Service/TokenService.java[m
[36m@@ -1,6 +1,7 @@[m
 package com.example.JwtOauthResourceServer.Service;[m
 [m
 import org.springframework.security.core.Authentication;[m
[32m+[m[32mimport org.springframework.security.core.GrantedAuthority;[m
 import org.springframework.security.oauth2.jwt.JwtClaimsSet;[m
 import org.springframework.security.oauth2.jwt.JwtEncoder;[m
 import org.springframework.security.oauth2.jwt.JwtEncoderParameters;[m
[36m@@ -8,6 +9,8 @@[m [mimport org.springframework.stereotype.Service;[m
 [m
 import java.time.Instant;[m
 import java.time.temporal.ChronoUnit;[m
[32m+[m[32mimport java.util.stream.Collectors;[m
[32m+[m
 @Service[m
 public class TokenService {[m
     private final JwtEncoder encoder;[m
[36m@@ -18,12 +21,15 @@[m [mpublic class TokenService {[m
 [m
     public String generateToken(Authentication authentication) {[m
         Instant now = Instant.now();[m
[32m+[m[32m        String scope = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority)[m
[32m+[m[32m                .collect(Collectors.joining(" "));[m
 [m
         JwtClaimsSet claims = JwtClaimsSet.builder()[m
                 .issuer("self")[m
                 .issuedAt(now)[m
                 .expiresAt(now.plus(1, ChronoUnit.HOURS))[m
                 .subject(authentication.getName())[m
[32m+[m[32m                .claim("scope", scope)[m
                 .build();[m
         return this.encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();[m
     }[m
