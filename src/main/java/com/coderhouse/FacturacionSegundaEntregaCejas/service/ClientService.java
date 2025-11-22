package com.coderhouse.FacturacionSegundaEntregaCejas.service;

import com.coderhouse.FacturacionSegundaEntregaCejas.model.Client;

import java.util.List;

public interface ClientService {

    Client create(Client client);

    Client update(Long id, Client client);

    void delete(Long id);

    Client getById(Long id);

    List<Client> getAll();

    Client getByDni(String dni);
}