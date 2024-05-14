package com.example.ooredooshop.controller;

import com.example.ooredooshop.models.Brand;
import com.example.ooredooshop.models.UserInfo;
import com.example.ooredooshop.security.jwt.RefreshToken;
import com.example.ooredooshop.payload.request.AuthRequestDTO;
import com.example.ooredooshop.payload.request.RefreshTokenRequestDTO;
import com.example.ooredooshop.payload.request.UserRequest;
import com.example.ooredooshop.payload.response.JwtResponseDTO;
import com.example.ooredooshop.payload.response.UserResponse;
import com.example.ooredooshop.security.service.JwtService;
import com.example.ooredooshop.security.service.RefreshTokenService;
import com.example.ooredooshop.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/auth")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    RefreshTokenService refreshTokenService;


    @Autowired
    private  AuthenticationManager authenticationManager;


    @PostMapping(value = "/save")
    public ResponseEntity saveUser(@RequestBody UserInfo userRequest) {
        try {
            UserInfo userResponse = userService.saveUser(userRequest);
            return ResponseEntity.ok(userResponse);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<UserInfo> createUser(@RequestParam("file") MultipartFile file,
                                               @RequestParam("username") String username,
                                               @RequestParam("password") String password
                                               ) throws IOException {
        try {
        UserInfo user = new UserInfo();
        user.setImage(Base64.getEncoder().encodeToString(file.getBytes()));
        user.setUsername(username);
        user.setPassword(password);
        userService.saveUser(user);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    } catch (Exception e) {
        throw new RuntimeException(e);
    }
    }


    @GetMapping("/users")
    public ResponseEntity getAllUsers() {
        try {
            List<UserInfo> user = userService.getAllUser();
            return ResponseEntity.ok(user);
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }


    @PostMapping("/profile")
    public ResponseEntity<UserInfo> getUserProfile() {
        try {
            UserInfo user= userService.getUser();
        return ResponseEntity.ok().body(user);
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/test")
    public String test() {
        try {
            return "Welcome";
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/login")
    public JwtResponseDTO AuthenticateAndGetToken(@RequestBody AuthRequestDTO authRequestDTO){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequestDTO.getUsername(), authRequestDTO.getPassword()));
        if(authentication.isAuthenticated()){
            RefreshToken refreshToken = refreshTokenService.createRefreshToken(authRequestDTO.getUsername());
           return JwtResponseDTO.builder()
                   .accessToken(jwtService.GenerateToken(authRequestDTO.getUsername()))
                   .token(refreshToken.getToken()).build();

        } else {
            throw new UsernameNotFoundException("invalid user request..!!");
        }

    }


    @PostMapping("/refreshToken")
    public JwtResponseDTO refreshToken(@RequestBody RefreshTokenRequestDTO refreshTokenRequestDTO){
        return refreshTokenService.findByToken(refreshTokenRequestDTO.getToken())
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUserInfo)
                .map(userInfo -> {
                    String accessToken = jwtService.GenerateToken(userInfo.getUsername());
                    return JwtResponseDTO.builder()
                            .accessToken(accessToken)
                            .token(refreshTokenRequestDTO.getToken()).build();
                }).orElseThrow(() ->new RuntimeException("Refresh Token is not in DB..!!"));
    }

}
