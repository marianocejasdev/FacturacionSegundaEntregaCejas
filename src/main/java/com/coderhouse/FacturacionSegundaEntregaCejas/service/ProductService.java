package com.coderhouse.FacturacionSegundaEntregaCejas.service;

import com.coderhouse.FacturacionSegundaEntregaCejas.model.Product;

import java.util.List;

public interface ProductService {
    Product create(Product product);

    Product update(Long id, Product product);

    void delete(Long id);

    Product getById(Long id);

    List<Product> getAll();
}