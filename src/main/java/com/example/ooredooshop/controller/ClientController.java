package com.example.ooredooshop.controller;

import com.example.ooredooshop.exceptions.NotFoundException;
import com.example.ooredooshop.models.Client;
import com.example.ooredooshop.services.ClientService;
import lombok.AllArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

        import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@AllArgsConstructor
@RequestMapping("/api/clients")
public class ClientController {
    private final ClientService clientService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createClient(@RequestBody Client client) {
        clientService.createClient(client);
    }

    @PutMapping("/{clientId}/toggle")
    public ResponseEntity<Client> toggleClientStatus(@PathVariable Long clientId) {
        Client updatedClient = clientService.toggleClientStatus(clientId);
        return ResponseEntity.ok(updatedClient);
    }

    @GetMapping("/sorted")
    @ResponseStatus(HttpStatus.OK)
    public List<Client> getAllClients() {

        return clientService.getAllClientsSortedByCreationDate();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Client> getClientById(@PathVariable Long id) {
        Client client = clientService.getClientById(id);
        return ResponseEntity.ok(client);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable Long id) {
        try {
            clientService.deleteClientById(id);
            return ResponseEntity.ok().build();
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/batch")
    public ResponseEntity<Void> deleteMultipleClients(@RequestParam List<Long> ids) {
        clientService.deleteMultipleClientsByIds(ids);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/search")
    public List<Client> searchClientsByKeyword(
            @RequestParam String keyword
    ) {
        return clientService.searchClientsByUsername(keyword);
    }

    @GetMapping("/count")
    public long countClients() {
        return clientService.countClients();
    }

}
