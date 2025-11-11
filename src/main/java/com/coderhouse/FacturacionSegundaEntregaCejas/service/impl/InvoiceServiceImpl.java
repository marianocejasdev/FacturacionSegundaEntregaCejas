package com.coderhouse.FacturacionSegundaEntregaCejas.service.impl;

import com.coderhouse.FacturacionSegundaEntregaCejas.dto.InvoiceItemRequest;
import com.coderhouse.FacturacionSegundaEntregaCejas.dto.InvoiceRequest;
import com.coderhouse.FacturacionSegundaEntregaCejas.exception.BadRequestException;
import com.coderhouse.FacturacionSegundaEntregaCejas.exception.NotFoundException;
import com.coderhouse.FacturacionSegundaEntregaCejas.model.*;
import com.coderhouse.FacturacionSegundaEntregaCejas.repository.ClientRepository;
import com.coderhouse.FacturacionSegundaEntregaCejas.repository.InvoiceRepository;
import com.coderhouse.FacturacionSegundaEntregaCejas.repository.ProductRepository;
import com.coderhouse.FacturacionSegundaEntregaCejas.service.InvoiceService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class InvoiceServiceImpl implements InvoiceService {

    private final ClientRepository clientRepository;
    private final ProductRepository productRepository;
    private final InvoiceRepository invoiceRepository;

    public InvoiceServiceImpl(ClientRepository clientRepository,
            ProductRepository productRepository,
            InvoiceRepository invoiceRepository) {
        this.clientRepository = clientRepository;
        this.productRepository = productRepository;
        this.invoiceRepository = invoiceRepository;
    }

    @Override
    public Invoice create(InvoiceRequest request) {
        if (request.getClientId() == null) {
            throw new BadRequestException("El clientId es obligatorio");
        }
        if (request.getItems() == null || request.getItems().isEmpty()) {
            throw new BadRequestException("La factura debe tener al menos un ítem");
        }

        Client client = clientRepository.findById(request.getClientId())
                .orElseThrow(() -> new NotFoundException("Cliente no encontrado con id " + request.getClientId()));

        Invoice invoice = new Invoice();
        invoice.setClient(client);
        invoice.setTotal(BigDecimal.ZERO);
        invoice.setDetails(new ArrayList<>());
        // set createdAt manual si querés
        // reflection: si el campo es private sin setter, agregale setCreatedAt
        try {
            var field = Invoice.class.getDeclaredField("createdAt");
            field.setAccessible(true);
            field.set(invoice, LocalDateTime.now());
        } catch (Exception ignored) {
        }

        BigDecimal total = BigDecimal.ZERO;
        List<InvoiceDetail> details = new ArrayList<>();

        for (InvoiceItemRequest item : request.getItems()) {
            if (item.getQuantity() == null || item.getQuantity() <= 0) {
                throw new BadRequestException("La cantidad debe ser mayor a 0");
            }

            Product product = productRepository.findById(item.getProductId())
                    .orElseThrow(() -> new NotFoundException("Producto no encontrado con id " + item.getProductId()));

            if (product.getStock() < item.getQuantity()) {
                throw new BadRequestException("Stock insuficiente para el producto " + product.getName());
            }

            BigDecimal unitPrice = product.getPrice();
            BigDecimal lineTotal = unitPrice.multiply(BigDecimal.valueOf(item.getQuantity()));

            InvoiceDetail detail = new InvoiceDetail();
            detail.setInvoice(invoice);
            detail.setProduct(product);
            detail.setQuantity(item.getQuantity());
            detail.setUnitPrice(unitPrice);

            details.add(detail);

            // actualizar stock
            product.setStock(product.getStock() - item.getQuantity());
            productRepository.save(product);

            total = total.add(lineTotal);
        }

        invoice.setDetails(details);
        invoice.setTotal(total);

        return invoiceRepository.save(invoice); // cascade guarda detalles
    }

    @Override
    public Invoice getById(Long id) {
        return invoiceRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Factura no encontrada con id " + id));
    }

    @Override
    public List<Invoice> getAll() {
        return invoiceRepository.findAll();
    }

    @Override
    public void delete(Long id) {
        if (!invoiceRepository.existsById(id)) {
            throw new NotFoundException("Factura no encontrada con id " + id);
        }
        invoiceRepository.deleteById(id);
    }
}
