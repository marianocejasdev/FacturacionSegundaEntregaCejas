package com.coderhouse.FacturacionSegundaEntregaCejas.repository;

import com.coderhouse.FacturacionSegundaEntregaCejas.model.InvoiceDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoiceDetailRepository extends JpaRepository<InvoiceDetail, Long> {
}