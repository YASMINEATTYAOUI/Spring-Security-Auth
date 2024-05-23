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
    private final ClientService clentService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createClient(@RequestBody Client client) {
        clentService.createClient(client);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public Client updateClient(@RequestBody Client updatedClient) {
        return clentService.updateClient(updatedClient);
    }

    @PutMapping("/{clientId}/toggle")
    public ResponseEntity<Client> toggleClientStatus(@PathVariable Long clientId) {
        Client updatedClient = clentService.toggleClientStatus(clientId);
        return ResponseEntity.ok(updatedClient);
    }

    @GetMapping("/sorted")
    @ResponseStatus(HttpStatus.OK)
    public List<Client> getAllClients() {
        return clentService.getAllClientsSortedByCreationDate();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Client> getClientById(@PathVariable Long id) {
        Client client = clentService.getClientById(id);
        return ResponseEntity.ok(client);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable Long id) {
        try {
            clentService.deleteClientById(id);
            return ResponseEntity.ok().build();
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/batch")
    public ResponseEntity<Void> deleteMultipleClients(@RequestParam List<Long> ids) {
        clentService.deleteMultipleClientsByIds(ids);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/search")
    public List<Client> searchClientsByKeyword(
            @RequestParam String keyword
    ) {
        return clentService.searchClientsByUsername(keyword);
    }


    @GetMapping("/count")
    public long countClients() {
        return clentService.countClients();
    }

}
