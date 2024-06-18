package com.example.ooredooshop.services;

import com.example.ooredooshop.models.UserInfo;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;


import java.util.List;

public interface UserService {

    UserInfo getUserByUsername(String username);
    void updatePassword(String email,String newPassword);
    UserInfo saveUser(UserInfo user);
    public UserInfo toggleUserStatus(Long userId);
    UserInfo getUser();
    List<UserInfo> getAllUser();
    long countUsers();

}
