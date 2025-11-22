package com.coderhouse.FacturacionSegundaEntregaCejas.dto;

import java.util.List;

public class InvoiceRequest {

    private Long clientId;
    private List<InvoiceItemRequest> items;

    public InvoiceRequest() {
    }

    public InvoiceRequest(Long clientId, List<InvoiceItemRequest> items) {
        this.clientId = clientId;
        this.items = items;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public List<InvoiceItemRequest> getItems() {
        return items;
    }

    public void setItems(List<InvoiceItemRequest> items) {
        this.items = items;
    }
}
