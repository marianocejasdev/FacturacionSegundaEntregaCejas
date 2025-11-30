package com.coderhouse.FacturacionSegundaEntregaCejas.dto;

import com.coderhouse.FacturacionSegundaEntregaCejas.model.Invoice;

import java.math.BigDecimal;
import java.util.List;

public class ComprobanteResponseDTO {

    private boolean success;
    private String fecha;
    private BigDecimal total;
    private Integer cantidadTotalProductos;
    private Invoice comprobante;
    private List<String> errores;

    public ComprobanteResponseDTO() {
    }

    public ComprobanteResponseDTO(boolean success, String fecha, BigDecimal total,
            Integer cantidadTotalProductos, Invoice comprobante,
            List<String> errores) {
        this.success = success;
        this.fecha = fecha;
        this.total = total;
        this.cantidadTotalProductos = cantidadTotalProductos;
        this.comprobante = comprobante;
        this.errores = errores;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public Integer getCantidadTotalProductos() {
        return cantidadTotalProductos;
    }

    public void setCantidadTotalProductos(Integer cantidadTotalProductos) {
        this.cantidadTotalProductos = cantidadTotalProductos;
    }

    public Invoice getComprobante() {
        return comprobante;
    }

    public void setComprobante(Invoice comprobante) {
        this.comprobante = comprobante;
    }

    public List<String> getErrores() {
        return errores;
    }

    public void setErrores(List<String> errores) {
        this.errores = errores;
    }
}
