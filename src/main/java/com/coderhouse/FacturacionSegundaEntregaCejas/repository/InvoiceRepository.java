package com.coderhouse.FacturacionSegundaEntregaCejas.repository;

import com.coderhouse.FacturacionSegundaEntregaCejas.model.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
}