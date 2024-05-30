package com.example.ooredooshop.services.serviceImpl;


import com.example.ooredooshop.exceptions.NotFoundException;
import com.example.ooredooshop.models.UserRole;
import com.example.ooredooshop.payload.request.UserRequest;
import com.example.ooredooshop.payload.response.UserResponse;
import com.example.ooredooshop.models.UserInfo;
import com.example.ooredooshop.repositories.RoleRepository;
import com.example.ooredooshop.repositories.UserRepository;
import com.example.ooredooshop.services.UserService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    ModelMapper modelMapper = new ModelMapper();

    @Override
    public UserInfo saveUser(UserInfo user) {
        try {
            if(user.getUsername() == null){
                throw new RuntimeException("Parameter username is not found in request..!!");
            } else if(user.getPassword() == null){
                throw new RuntimeException("Parameter password is not found in request..!!");
            }


//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        UserDetails userDetail = (UserDetails) authentication.getPrincipal();
//        String usernameFromAccessToken = userDetail.getUsername();
//
//        UserInfo currentUser = userRepository.findByUsername(usernameFromAccessToken);

            UserInfo savedUser = null;

            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            String rawPassword = user.getPassword();
            String encodedPassword = encoder.encode(rawPassword);

            user.setPassword(encodedPassword);
            if(user.getId() != null){
                UserInfo oldUser = userRepository.findFirstById(user.getId());
                if(oldUser != null){
                    oldUser.setId(user.getId());
                    oldUser.setPassword(user.getPassword());
                    oldUser.setUsername(user.getUsername());
                    oldUser.setRoles(user.getRoles());

                    savedUser = userRepository.save(oldUser);
                    userRepository.refresh(savedUser);
                } else {
                    throw new RuntimeException("Can't find record with identifier: " + user.getId());
                }
            } else {
//            user.setCreatedBy(currentUser);
                user.setCreationDate(new Date());
                savedUser = userRepository.save(user);
            }

            return savedUser;
        } catch (Exception e) {
            throw e;
        }

    }

    @Override
    @Transactional
    public UserInfo toggleUserStatus(Long userId) {
        UserInfo user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));

        // Toggle the active status of the user
        user.setStatus(!user.isActive());

        return userRepository.save(user);
    }
    @Override
    public UserInfo getUser() {
        System.out.println("aaaaa");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetail = (UserDetails) authentication.getPrincipal();
        String usernameFromAccessToken = userDetail.getUsername();
        UserInfo user = userRepository.findByUsername(usernameFromAccessToken);
        UserInfo userResponse = modelMapper.map(user, UserInfo.class);
        return userResponse;
    }

    @Override
    public List<UserInfo> getAllUser() {
        List<UserInfo> users = (List<UserInfo>) userRepository.findAll();
        return users;
    }

}
