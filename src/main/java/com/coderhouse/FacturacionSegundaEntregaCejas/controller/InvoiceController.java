package com.coderhouse.FacturacionSegundaEntregaCejas.controller;

import com.coderhouse.FacturacionSegundaEntregaCejas.dto.ComprobanteRequestDTO;
import com.coderhouse.FacturacionSegundaEntregaCejas.dto.ComprobanteResponseDTO;
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
    public ResponseEntity<ComprobanteResponseDTO> create(@RequestBody ComprobanteRequestDTO request) {
        ComprobanteResponseDTO resp = invoiceService.create(request);

        // Si querés, podés devolver 201 cuando todo sale bien y 400 cuando hay errores
        // de validación
        HttpStatus status = resp.isSuccess() ? HttpStatus.CREATED : HttpStatus.BAD_REQUEST;

        return ResponseEntity.status(status).body(resp);
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