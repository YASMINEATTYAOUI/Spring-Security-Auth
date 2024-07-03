package com.example.ooredooshop.controller;

import com.example.ooredooshop.models.ChangePassword;
import com.example.ooredooshop.models.UserInfo;

import com.example.ooredooshop.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;

import org.springframework.security.crypto.password.PasswordEncoder;
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
    PasswordEncoder passwordEncoder;

    @Autowired
    private JavaMailSender mailSender;


    @GetMapping("/current-user")
    public UserInfo getCurrentUser(Authentication authentication) {
        String username = authentication.getName();
        return userService.getUserByUsername(username);
    }


    @PostMapping(value = "/save")
    public ResponseEntity saveUser(@RequestBody UserInfo userRequest) {
        try {
            System.out.println(userRequest);
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

    @GetMapping("/{userId}")
    public ResponseEntity<UserInfo> getUserById(@PathVariable Long userId) {
        try {
            UserInfo user = userService.getUserById(userId);
            if (user != null) {
                return ResponseEntity.ok(user);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/change-password/{email}")
    public ResponseEntity<?> resetPassword(@RequestBody ChangePassword changePassword,
                                           @PathVariable String email) {
        //logger.info("changing password for email: {}", email);
        if (!changePassword.getPassword().equals(changePassword.getConfirmPassword())) {
            return ResponseEntity.badRequest().body("Passwords do not match");
        }
        String encodedPassword = passwordEncoder.encode(changePassword.getPassword());
        userService.updatePassword(email, encodedPassword);
        //logger.info("Password changed for email: {}", email);
        return ResponseEntity.ok("Password has been changed!");
    }

    @GetMapping("/count")
    public long countUsers(){
        return userService.countUsers();
    }

}
