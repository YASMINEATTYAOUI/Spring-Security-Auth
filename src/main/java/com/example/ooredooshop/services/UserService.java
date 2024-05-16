package com.example.ooredooshop.services;

import com.example.ooredooshop.models.UserInfo;
import com.example.ooredooshop.models.UserRole;
import com.example.ooredooshop.payload.request.UserRequest;
import com.example.ooredooshop.payload.response.UserResponse;

import java.util.List;

public interface UserService {


    UserInfo saveUser(UserInfo user);
    public UserInfo toggleUserStatus(Long userId);
    UserInfo getUser();
    List<UserInfo> getAllUser();

}
