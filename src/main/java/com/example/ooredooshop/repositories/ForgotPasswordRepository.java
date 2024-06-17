package com.example.ooredooshop.repositories;

import com.example.ooredooshop.models.ForgotPassword;
import com.example.ooredooshop.models.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ForgotPasswordRepository extends JpaRepository<ForgotPassword,Long> {
    Optional<ForgotPassword> findByOtpAndUser(Integer otp, UserInfo userInfo);
}
