package com.example.ooredooshop.services.serviceImpl;
import com.example.ooredooshop.exceptions.NotFoundException;
import com.example.ooredooshop.models.Client;
import com.example.ooredooshop.repositories.ClientRepository;
import com.example.ooredooshop.services.ClientService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {
    private final ClientRepository clientRepository;
    private static final Logger logger = LoggerFactory.getLogger(ClientServiceImpl.class);

    @Override
    public void createClient(Client client) {
        clientRepository.save(client);
        logger.info("Client {} is saved", client.getId());
    }

    @Override
    public Client updateClient(Client updatedClient) {
        Client finalUpdatedClient = updatedClient;
        Client existingClient = clientRepository.findById(updatedClient.getId())
                .orElseThrow(() -> new NotFoundException("Client with ID " + finalUpdatedClient.getId() + " not found"));

        updatedClient = clientRepository.save(existingClient);
        logger.info("Client {} got updated", updatedClient.getId());

        return updatedClient;
    }

    @Override
    @Transactional
    public Client toggleClientStatus(Long clientId) {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new NotFoundException("Client not found"));

        // Toggle the active status of the client
        client.setActive(!client.isActive());

        return clientRepository.save(client);
    }

    @Override
    public Client getClientById(Long id) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Client with ID " + id + " not found"));
        logger.info("Client {} is fetched", client.getId());

        return client;
    }

    @Override
    public List<Client> getAllClientsSortedByCreationDate() {
        logger.info("Retrieving All Clients (Sorted)");
        return clientRepository.findAllByOrderByCreationDateDesc();
    }

    @Override
    public List<Client> searchClientsByUsername(String keyword) {
        return clientRepository.findByUsernameContainingIgnoreCaseOrderByCreationDateDesc(keyword);
    }

    @Override
    public void deleteClientById(Long id) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Client not found with ID: " + id));
        clientRepository.delete(client);
        logger.info("Client {} is deleted", client.getId());
    }

    @Override
    public void deleteMultipleClientsByIds(List<Long> ids) {
        logger.info("Batch deletion of clients with IDs: {}", ids);
        clientRepository.deleteAllById(ids);
    }

    @Override
    public long countClients() {
        return clientRepository.count();
    }
}
