package com.example.ooredooshop.services.serviceImpl;


import com.example.ooredooshop.exceptions.NotFoundException;

import com.example.ooredooshop.models.UserInfo;

import com.example.ooredooshop.repositories.RoleRepository;
import com.example.ooredooshop.repositories.UserRepository;
import com.example.ooredooshop.services.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Type;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    //private PasswordResetTokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);


    @Override
    public UserInfo getUserByUsername(String username) {

        return userRepository.findByUsername(username);
    }

    public void updatePassword(String email, String newPassword) {
        UserInfo user = userRepository.findByEmail(email)
                .orElseThrow(() -> {
                    logger.error("User not found for email: {}", email);
                    return new NotFoundException("User not found");
                });
        user.setPassword(newPassword);
        userRepository.save(user);
    }



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

            String rawPassword = user.getPassword();
            String encodedPassword = passwordEncoder.encode(rawPassword);

            user.setPassword(encodedPassword);
            if(user.getId() != null){
                UserInfo oldUser = userRepository.findFirstById(user.getId());
                if(oldUser != null){
                    oldUser.setId(user.getId());
                    oldUser.setPassword(user.getPassword());
                    oldUser.setUsername(user.getUsername());
                    oldUser.setRole(user.getRole());

                    savedUser = userRepository.save(oldUser);
                } else {
                    throw new RuntimeException("Can't find record with identifier: " + user.getId());
                }
            } else {
//            user.setCreatedBy(currentUser);
                user.setCreationDate(new Date());
                user.setLastModifiedDate(new Date());

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
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetail = (UserDetails) authentication.getPrincipal();
        String usernameFromAccessToken = userDetail.getUsername();
        return userRepository.findByUsername(usernameFromAccessToken);
    }

    @Override
    public List<UserInfo> getAllUser() {
        List<UserInfo> users = (List<UserInfo>) userRepository.findAll();
        return users;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserInfo account = userRepository.findByUsername(username);
        if(account == null){
            log.error("Account not found in the database");
            throw new UsernameNotFoundException("Account not found in the database");
        }
        else{
            log.info("Account found in the database");
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();

        authorities.add(new SimpleGrantedAuthority(account.getRole().getName()));
        return new User(account.getUsername(), account.getPassword(), authorities);
    }

    @Override
    public long countUsers() {
        return userRepository.count();
    }


    @Override
    public UserInfo getUserById(Long userId) {
        Optional<UserInfo> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            return userOptional.get();
        } else {
            throw new RuntimeException("User not found with ID: " + userId);
        }
    }
}
