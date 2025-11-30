package com.coderhouse.FacturacionSegundaEntregaCejas.service;

import com.coderhouse.FacturacionSegundaEntregaCejas.dto.ComprobanteRequestDTO;
import com.coderhouse.FacturacionSegundaEntregaCejas.dto.ComprobanteResponseDTO;
import com.coderhouse.FacturacionSegundaEntregaCejas.model.Invoice;

import java.util.List;

public interface InvoiceService {

    ComprobanteResponseDTO create(ComprobanteRequestDTO request);

    Invoice getById(Long id);

    List<Invoice> getAll();

    void delete(Long id);
}
