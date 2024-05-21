package com.example.ooredooshop.controller;

import com.example.ooredooshop.models.UserInfo;

import com.example.ooredooshop.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;

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

    @PutMapping("/{userId}/toggle")
    public ResponseEntity<UserInfo> toggleUserStatus(@PathVariable Long userId) {
        UserInfo updatedUser = userService.toggleUserStatus(userId);
        return ResponseEntity.ok(updatedUser);
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



}
