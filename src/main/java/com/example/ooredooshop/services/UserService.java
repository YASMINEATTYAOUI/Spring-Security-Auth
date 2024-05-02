package com.example.ooredooshop.services;



import com.example.ooredooshop.dtos.UserRequest;
import com.example.ooredooshop.dtos.UserResponse;

import java.util.List;


public interface UserService {

    UserResponse saveUser(UserRequest userRequest);

    UserResponse getUser();

    List<UserResponse> getAllUser();


}
