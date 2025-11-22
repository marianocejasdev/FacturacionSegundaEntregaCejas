package com.coderhouse.FacturacionSegundaEntregaCejas.repository;

import com.coderhouse.FacturacionSegundaEntregaCejas.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
