package com.coderhouse.FacturacionSegundaEntregaCejas.repository;

import com.coderhouse.FacturacionSegundaEntregaCejas.model.Client;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {
    Optional<Client> findByDni(String dni);
}