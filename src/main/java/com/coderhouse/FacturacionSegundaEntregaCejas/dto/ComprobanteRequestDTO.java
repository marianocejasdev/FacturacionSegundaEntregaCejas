package com.coderhouse.FacturacionSegundaEntregaCejas.dto;

import java.util.List;

public class ComprobanteRequestDTO {

    private ClienteRefDTO cliente;
    private List<LineaRequestDTO> lineas;

    public ComprobanteRequestDTO() {
    }

    public ClienteRefDTO getCliente() {
        return cliente;
    }

    public void setCliente(ClienteRefDTO cliente) {
        this.cliente = cliente;
    }

    public List<LineaRequestDTO> getLineas() {
        return lineas;
    }

    public void setLineas(List<LineaRequestDTO> lineas) {
        this.lineas = lineas;
    }
}
