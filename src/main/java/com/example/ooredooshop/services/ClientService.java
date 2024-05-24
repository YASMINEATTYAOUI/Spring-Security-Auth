package com.example.ooredooshop.services;

import com.example.ooredooshop.exceptions.NotFoundException;
import com.example.ooredooshop.models.Client;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ClientService {
    void createClient(Client clientRequest);
    Client updateClient(Client client);
    Client getClientById(Long id);

    List<Client> getAllClientsSortedByCreationDate();
     List<Client> searchClientsByUsername(String keyword);

    void deleteClientById(Long id);
    void deleteMultipleClientsByIds(List<Long> ids);
    long countClients();

    Client toggleClientStatus(Long clientId);
}
