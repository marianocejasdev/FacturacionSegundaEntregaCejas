package com.coderhouse.FacturacionSegundaEntregaCejas.dto;

public class LineaRequestDTO {

    private Integer cantidad;
    private ProductoRefDTO producto;

    public LineaRequestDTO() {
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public ProductoRefDTO getProducto() {
        return producto;
    }

    public void setProducto(ProductoRefDTO producto) {
        this.producto = producto;
    }
}
