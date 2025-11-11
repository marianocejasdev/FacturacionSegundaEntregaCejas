package com.coderhouse.FacturacionSegundaEntregaCejas.service.impl;

import com.coderhouse.FacturacionSegundaEntregaCejas.exception.BadRequestException;
import com.coderhouse.FacturacionSegundaEntregaCejas.exception.NotFoundException;
import com.coderhouse.FacturacionSegundaEntregaCejas.model.Client;
import com.coderhouse.FacturacionSegundaEntregaCejas.repository.ClientRepository;
import com.coderhouse.FacturacionSegundaEntregaCejas.service.ClientService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;

    public ClientServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public Client create(Client client) {
        if (client.getDni() == null || client.getDni().isBlank()) {
            throw new BadRequestException("El DNI del cliente es obligatorio");
        }

        clientRepository.findByDni(client.getDni())
                .ifPresent(c -> {
                    throw new BadRequestException("Ya existe un cliente con DNI " + client.getDni());
                });

        if (client.getName() == null || client.getName().isBlank()) {
            throw new BadRequestException("El nombre es obligatorio");
        }

        if (client.getLastName() == null || client.getLastName().isBlank()) {
            throw new BadRequestException("El apellido es obligatorio");
        }

        return clientRepository.save(client);
    }

    @Override
    public Client update(Long id, Client client) {
        Client existing = clientRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Cliente no encontrado con id " + id));

        existing.setName(client.getName());
        existing.setLastName(client.getLastName());
        existing.setDni(client.getDni());

        return clientRepository.save(existing);
    }

    @Override
    public void delete(Long id) {
        if (!clientRepository.existsById(id)) {
            throw new NotFoundException("Cliente no encontrado con id " + id);
        }
        clientRepository.deleteById(id);
    }

    @Override
    public Client getById(Long id) {
        return clientRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Cliente no encontrado con id " + id));
    }

    @Override
    public List<Client> getAll() {
        return clientRepository.findAll();
    }

    @Override
    public Client getByDni(String dni) {
        return clientRepository.findByDni(dni)
                .orElseThrow(() -> new NotFoundException("Cliente no encontrado con dni " + dni));
    }
}