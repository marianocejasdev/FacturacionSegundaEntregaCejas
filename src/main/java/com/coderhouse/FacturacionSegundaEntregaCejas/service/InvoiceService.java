package com.coderhouse.FacturacionSegundaEntregaCejas.service;

import com.coderhouse.FacturacionSegundaEntregaCejas.dto.InvoiceRequest;
import com.coderhouse.FacturacionSegundaEntregaCejas.model.Invoice;

import java.util.List;

public interface InvoiceService {
    Invoice create(InvoiceRequest request);

    Invoice getById(Long id);

    List<Invoice> getAll();

    void delete(Long id);
}
