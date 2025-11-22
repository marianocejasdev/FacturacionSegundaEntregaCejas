package com.coderhouse.FacturacionSegundaEntregaCejas.service.impl;

import com.coderhouse.FacturacionSegundaEntregaCejas.exception.BadRequestException;
import com.coderhouse.FacturacionSegundaEntregaCejas.exception.NotFoundException;
import com.coderhouse.FacturacionSegundaEntregaCejas.model.Product;
import com.coderhouse.FacturacionSegundaEntregaCejas.repository.ProductRepository;
import com.coderhouse.FacturacionSegundaEntregaCejas.service.ProductService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product create(Product product) {
        validate(product);
        return productRepository.save(product);
    }

    @Override
    public Product update(Long id, Product product) {
        Product existing = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Producto no encontrado con id " + id));

        existing.setName(product.getName());
        existing.setDescription(product.getDescription());
        existing.setPrice(product.getPrice());
        existing.setStock(product.getStock());

        validate(existing);
        return productRepository.save(existing);
    }

    @Override
    public void delete(Long id) {
        if (!productRepository.existsById(id)) {
            throw new NotFoundException("Producto no encontrado con id " + id);
        }
        productRepository.deleteById(id);
    }

    @Override
    public Product getById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Producto no encontrado con id " + id));
    }

    @Override
    public List<Product> getAll() {
        return productRepository.findAll();
    }

    private void validate(Product p) {
        if (p.getName() == null || p.getName().isBlank()) {
            throw new BadRequestException("El nombre del producto es obligatorio");
        }
        if (p.getPrice() == null || p.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BadRequestException("El precio debe ser mayor a 0");
        }
        if (p.getStock() == null || p.getStock() < 0) {
            throw new BadRequestException("El stock no puede ser negativo");
        }
    }
}
