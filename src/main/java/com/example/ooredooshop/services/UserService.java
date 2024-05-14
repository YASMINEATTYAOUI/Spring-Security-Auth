package com.example.ooredooshop.services;

import com.example.ooredooshop.models.UserInfo;
import com.example.ooredooshop.models.UserRole;
import com.example.ooredooshop.payload.request.UserRequest;
import com.example.ooredooshop.payload.response.UserResponse;

import java.util.List;

public interface UserService {

    void assignRole(Long userId, Long roleId);
    void revokeRole(Long userId, Long roleId);

    UserInfo saveUser(UserInfo user);

    UserInfo getUser();

    List<UserInfo> getAllUser();


}
