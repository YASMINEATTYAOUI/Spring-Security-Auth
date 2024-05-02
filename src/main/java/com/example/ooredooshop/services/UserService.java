package com.example.ooredooshop.services;



import com.example.ooredooshop.payload.request.UserRequest;
import com.example.ooredooshop.payload.response.UserResponse;

import java.util.List;


public interface UserService {

    UserResponse saveUser(UserRequest userRequest);

    UserResponse getUser();

    List<UserResponse> getAllUser();


}
