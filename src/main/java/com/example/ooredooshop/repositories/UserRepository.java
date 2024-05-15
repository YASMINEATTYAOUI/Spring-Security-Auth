package com.example.ooredooshop.repositories;


import com.example.ooredooshop.helpers.RefreshableCRUDRepository;
import com.example.ooredooshop.models.UserInfo;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends RefreshableCRUDRepository<UserInfo, Long> {

   UserInfo findByUsername(String username);
   UserInfo findFirstById(Long id);

}
