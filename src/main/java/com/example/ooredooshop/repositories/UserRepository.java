package com.example.ooredooshop.repositories;


import com.example.ooredooshop.models.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<UserInfo, Long> {
   UserInfo findByUsername(String username);

   Optional<UserInfo> findByEmail(String email);
   UserInfo findFirstById(Long id);
   /*
   @Transactional
   @Modifying
   @Query("update UserInfo u set u.password = ?2 where u.email = ?1")
   void updatePassword(String email, String password);
*/

}
