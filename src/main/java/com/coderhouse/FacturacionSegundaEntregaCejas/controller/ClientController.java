package com.coderhouse.FacturacionSegundaEntregaCejas.controller;

import com.coderhouse.FacturacionSegundaEntregaCejas.model.Client;
import com.coderhouse.FacturacionSegundaEntregaCejas.service.ClientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/client")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    // POST api/client/
    @PostMapping("/")
    public ResponseEntity<Client> create(@RequestBody Client client) {
        return ResponseEntity.status(HttpStatus.CREATED).body(clientService.create(client));
    }

    // PUT api/client/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Client> update(@PathVariable Long id, @RequestBody Client client) {
        return ResponseEntity.ok(clientService.update(id, client));
    }

    // GET api/client/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Client> getById(@PathVariable Long id) {
        return ResponseEntity.ok(clientService.getById(id));
    }

    // DELETE api/client/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        clientService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // GET api/client/
    @GetMapping("/")
    public ResponseEntity<List<Client>> getAll() {
        return ResponseEntity.ok(clientService.getAll());
    }

    // GET api/client/search?dni=12345678
    @GetMapping("/search")
    public ResponseEntity<Client> getByDni(@RequestParam String dni) {
        return ResponseEntity.ok(clientService.getByDni(dni));
    }
}