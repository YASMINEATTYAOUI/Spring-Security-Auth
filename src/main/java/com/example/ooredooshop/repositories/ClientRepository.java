package com.example.ooredooshop.repositories;

import com.example.ooredooshop.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    List<Client> findAllByOrderByCreationDateDesc();

    List<Client> findByUsernameContainingIgnoreCaseOrderByCreationDateDesc(String username);


    Client findByUsername(String username);
}
