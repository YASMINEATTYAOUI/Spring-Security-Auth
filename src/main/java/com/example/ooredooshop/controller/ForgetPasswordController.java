package com.example.ooredooshop.controller;

import com.example.ooredooshop.exceptions.NotFoundException;
import com.example.ooredooshop.models.ChangePassword;
import com.example.ooredooshop.models.ForgotPassword;
import com.example.ooredooshop.models.MailBody;
import com.example.ooredooshop.models.UserInfo;
import com.example.ooredooshop.repositories.ForgotPasswordRepository;
import com.example.ooredooshop.repositories.UserRepository;
import com.example.ooredooshop.services.serviceImpl.EmailServiceImpl;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Date;
import java.util.Objects;
import java.util.Random;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/forgot-pwd")
@RequiredArgsConstructor
public class ForgetPasswordController {

    private static final Logger logger = LoggerFactory.getLogger(ForgetPasswordController.class);

    private final UserRepository userRepository;
    private final EmailServiceImpl emailService;
    private final ForgotPasswordRepository forgotPasswordRepository;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/verify-mail/{email}")
    public ResponseEntity<String> verifyOEmail(@PathVariable String email) {
        logger.info("Received forgot password request for email: {}", email);

        UserInfo user = userRepository.findByEmail(email)
                .orElseThrow(() -> {
                    logger.error("User not found for email: {}", email);
                    return new UsernameNotFoundException("Please provide a valid email");
                });

        Integer otp = otpGenerator();
        logger.info("Generated OTP: {}", otp);

        MailBody mailBody = MailBody.builder()
                .to(email)
                .text("This is the OTP for your Forgot Password request: " + otp)
                .subject("Your OTP for Password Reset")
                .build();

        ForgotPassword forgotPassword = ForgotPassword.builder()
                .otp(otp)
                .expirationTime(new Date(System.currentTimeMillis() + 70 * 1000))
                .user(user)
                .build();

        try {
            emailService.sendSimpleMessage(mailBody);
            logger.info("OTP email sent to: {}", email);
        } catch (Exception e) {
            logger.error("Failed to send OTP email to: {}", email, e);
            return new ResponseEntity<>("Failed to send email",HttpStatus.INTERNAL_SERVER_ERROR);
        }

        forgotPasswordRepository.save(forgotPassword);
        logger.info("Saved OTP for email: {}", email);

        return ResponseEntity.ok("Email sent for verification!");
    }

    @PostMapping("/verify-mail/{otp}/{email}")
    public ResponseEntity<String> verifyOEmail(@PathVariable Integer otp, @PathVariable String email) {
        logger.info("Verifying OTP: {} for email: {}", otp, email);

        UserInfo user = userRepository.findByEmail(email)
                .orElseThrow(() -> {
                    logger.error("User not found for email: {}", email);
                    return new NotFoundException("User not found");
                });

        ForgotPassword forgotPassword = forgotPasswordRepository.findByOtpAndUser(otp, user)
                .orElseThrow(() -> {
                    logger.error("Invalid OTP: {} for email: {}", otp, email);
                    return new RuntimeException("Invalid OTP for email: " + email);
                });

        if (forgotPassword.getExpirationTime().before(Date.from(Instant.now()))) {
            forgotPasswordRepository.deleteById(forgotPassword.getId());
            logger.info("OTP expired for email: {}", email);
            return new ResponseEntity<>("OTP has expired!", HttpStatus.EXPECTATION_FAILED);
        }

        logger.info("OTP verified for email: {}", email);
        return ResponseEntity.ok("OTP verified!");
    }

    @PostMapping("/reset-password/{email}")
    public ResponseEntity<String> resetPassword(@RequestBody ChangePassword changePassword,
                                                @PathVariable String email) {
        logger.info("Resetting password for email: {}", email);

        if (!Objects.equals(changePassword.getPassword(), changePassword.getConfirmPassword())) {
            logger.error("Passwords do not match for email: {}", email);
            return new ResponseEntity<>("Please enter the password again!", HttpStatus.EXPECTATION_FAILED);
        }

        String encodedPassword = passwordEncoder.encode(changePassword.getPassword());
        userRepository.updatePassword(email, encodedPassword);
        logger.info("Password changed for email: {}", email);

        return ResponseEntity.ok("Password has been changed!");
    }

    private Integer otpGenerator() {
        Random random = new Random();
        return 100000 + random.nextInt(900000);
    }
}







/*
package com.example.ooredooshop.controller;

import com.example.ooredooshop.exceptions.NotFoundException;
import com.example.ooredooshop.models.ChangePassword;
import com.example.ooredooshop.models.ForgotPassword;
import com.example.ooredooshop.models.MailBody;
import com.example.ooredooshop.models.UserInfo;
import com.example.ooredooshop.repositories.ForgotPasswordRepository;
import com.example.ooredooshop.repositories.UserRepository;
import com.example.ooredooshop.services.serviceImpl.EmailServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Date;
import java.util.Objects;
import java.util.Random;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/forgot-pwd")
@RequiredArgsConstructor
public class ForgetPasswordController {

    private final UserRepository userRepository;
    private final EmailServiceImpl emailService;
    private final ForgotPasswordRepository forgotPasswordRepository;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/verify-mail/{email}")
    public ResponseEntity<String> verifyOEmail(@PathVariable String email) {
        UserInfo user = userRepository.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("Please provide a valid email"));


       Integer otp = otpGenerator();

        MailBody mailBody = MailBody.builder()
                .to(email)
                .text("This is the OTP for your Forgot Password request: "+ otp)
                .subject("Your OTP for Password Reset")
                .build();

        ForgotPassword forgotPassword = ForgotPassword.builder()
                .otp(otp)
                .expirationTime(new Date(System.currentTimeMillis()+70*1000))
                .user(user)
                .build();

        emailService.sendSimpleMessage(mailBody);
        forgotPasswordRepository.save(forgotPassword);
        return ResponseEntity.ok("Email sent for verification!");
    }


    @PostMapping("/verify-mail/{otp}/{email}")
    public ResponseEntity<String> verifyOEmail(@PathVariable Integer otp,@PathVariable String email) {
        UserInfo user = userRepository.findByEmail(email)
         .orElseThrow(() -> new NotFoundException("User not found"));

        ForgotPassword forgotPassword = forgotPasswordRepository.findByOtpAndUser(otp, user)
            .orElseThrow(() -> new RuntimeException("Invalid OTP for email: "+ email));

        if( forgotPassword.getExpirationTime().before(Date.from(Instant.now()))){
            forgotPasswordRepository.deleteById(forgotPassword.getId());
            return new ResponseEntity<>("OTP has expired!", HttpStatus.EXPECTATION_FAILED);
        }

        return ResponseEntity.ok("OTP verified!");
    }

    @PostMapping("/reset-password/{email}")
    public ResponseEntity<String> resetPassword(@RequestBody ChangePassword changePassword,
                                                @PathVariable String email) {
       if (!Objects.equals(changePassword.getPassword(),changePassword.getConfirmPassword())){
           return new ResponseEntity<>("Please enter the password again!", HttpStatus.EXPECTATION_FAILED);

       }

       String encodedPassword = passwordEncoder.encode(changePassword.getPassword());
       userRepository.updatePassword(email,encodedPassword);

       return ResponseEntity.ok("Password has been changed!");
    }

    private Integer otpGenerator() {
        Random random = new Random();
        return 100000 + random.nextInt(900000);
    }
    /*
    private Integer otpGenerator(){
        Random random = new Random();
        return random.nextInt(100_000,999_999);
    }
*/
