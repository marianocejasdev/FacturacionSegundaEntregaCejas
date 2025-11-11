package com.coderhouse.FacturacionSegundaEntregaCejas.controller;

import com.coderhouse.FacturacionSegundaEntregaCejas.dto.InvoiceRequest;
import com.coderhouse.FacturacionSegundaEntregaCejas.model.Invoice;
import com.coderhouse.FacturacionSegundaEntregaCejas.service.InvoiceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/invoice")
public class InvoiceController {

    private final InvoiceService invoiceService;

    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    // POST api/invoice/
    @PostMapping("/")
    public ResponseEntity<Invoice> create(@RequestBody InvoiceRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(invoiceService.create(request));
    }

    // GET api/invoice/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Invoice> getById(@PathVariable Long id) {
        return ResponseEntity.ok(invoiceService.getById(id));
    }

    // GET api/invoice/
    @GetMapping("/")
    public ResponseEntity<List<Invoice>> getAll() {
        return ResponseEntity.ok(invoiceService.getAll());
    }

    // DELETE api/invoice/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        invoiceService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
