package com.example.ooredooshop.repositories;


import com.example.ooredooshop.models.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<UserInfo, Long> {

   UserInfo findByUsername(String username);
   UserInfo findFirstById(Long id);

}
