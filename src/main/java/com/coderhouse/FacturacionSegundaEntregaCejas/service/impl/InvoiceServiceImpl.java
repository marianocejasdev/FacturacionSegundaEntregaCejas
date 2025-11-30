package com.coderhouse.FacturacionSegundaEntregaCejas.service.impl;

import com.coderhouse.FacturacionSegundaEntregaCejas.dto.*;
import com.coderhouse.FacturacionSegundaEntregaCejas.model.*;
import com.coderhouse.FacturacionSegundaEntregaCejas.repository.ClientRepository;
import com.coderhouse.FacturacionSegundaEntregaCejas.repository.InvoiceRepository;
import com.coderhouse.FacturacionSegundaEntregaCejas.repository.ProductRepository;
import com.coderhouse.FacturacionSegundaEntregaCejas.service.InvoiceService;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class InvoiceServiceImpl implements InvoiceService {

    private final ClientRepository clientRepository;
    private final ProductRepository productRepository;
    private final InvoiceRepository invoiceRepository;

    public InvoiceServiceImpl(ClientRepository clientRepository,
            ProductRepository productRepository,
            InvoiceRepository invoiceRepository) {
        this.clientRepository = clientRepository;
        this.productRepository = productRepository;
        this.invoiceRepository = invoiceRepository;
    }

    @Override
    public ComprobanteResponseDTO create(ComprobanteRequestDTO request) {
        List<String> errores = new ArrayList<>();

        // ✅ Validaciones básicas de estructura
        if (request == null) {
            errores.add("El request no puede ser nulo");
        }

        if (request == null || request.getCliente() == null || request.getCliente().getClienteid() == null) {
            errores.add("El campo cliente.clienteid es obligatorio");
        }

        if (request == null || request.getLineas() == null || request.getLineas().isEmpty()) {
            errores.add("Debe existir al menos una línea en 'lineas'");
        }

        // Si ya hay errores estructurales, devolvemos respuesta de error
        if (!errores.isEmpty()) {
            ComprobanteResponseDTO resp = new ComprobanteResponseDTO();
            resp.setSuccess(false);
            resp.setErrores(errores);
            return resp;
        }

        Long clientId = request.getCliente().getClienteid();

        // ✅ Validar cliente existente
        Client client = clientRepository.findById(clientId).orElse(null);
        if (client == null) {
            errores.add("El cliente con id " + clientId + " no existe");
        }

        // Armamos la factura
        Invoice invoice = new Invoice();
        invoice.setClient(client);

        // ✅ Fecha desde servicio externo (worldclock) con fallback a Date/LocalDateTime
        LocalDateTime fechaComprobante = obtenerFechaDesdeWorldClock();
        invoice.setCreatedAt(fechaComprobante);

        BigDecimal total = BigDecimal.ZERO;
        int cantidadTotalProductos = 0;
        List<InvoiceDetail> details = new ArrayList<>();

        // ✅ Validar productos, stock y construir detalles
        for (LineaRequestDTO linea : request.getLineas()) {
            if (linea.getCantidad() == null || linea.getCantidad() <= 0) {
                errores.add("La cantidad debe ser mayor a 0");
                continue;
            }

            if (linea.getProducto() == null || linea.getProducto().getProductoid() == null) {
                errores.add("Cada línea debe incluir producto.productoid");
                continue;
            }

            Long productId = linea.getProducto().getProductoid();

            Product product = productRepository.findById(productId).orElse(null);
            if (product == null) {
                errores.add("El producto con id " + productId + " no existe");
                continue;
            }

            if (product.getStock() < linea.getCantidad()) {
                errores.add("Stock insuficiente para el producto " + product.getName()
                        + ". Pedido: " + linea.getCantidad()
                        + ", stock disponible: " + product.getStock());
                continue;
            }

            // ✅ Congelamos el precio en el momento de la venta
            BigDecimal unitPrice = product.getPrice();
            BigDecimal lineTotal = unitPrice.multiply(BigDecimal.valueOf(linea.getCantidad()));

            InvoiceDetail detail = new InvoiceDetail();
            detail.setInvoice(invoice);
            detail.setProduct(product);
            detail.setQuantity(linea.getCantidad());
            detail.setUnitPrice(unitPrice);

            details.add(detail);

            // ✅ Reducir stock del producto
            product.setStock(product.getStock() - linea.getCantidad());
            productRepository.save(product);

            // Acumulamos total y cantidad de productos
            total = total.add(lineTotal);
            cantidadTotalProductos += linea.getCantidad();
        }

        // Si cliente es nulo o no hubo ninguna línea válida, devolvemos error
        if (client == null || details.isEmpty()) {
            ComprobanteResponseDTO resp = new ComprobanteResponseDTO();
            resp.setSuccess(false);
            resp.setErrores(errores);
            return resp;
        }

        // Seteamos detalles y total y guardamos comprobante
        invoice.setDetails(details);
        invoice.setTotal(total);

        Invoice saved = invoiceRepository.save(invoice); // cascade guarda detalles

        // ✅ Armamos la respuesta final
        ComprobanteResponseDTO resp = new ComprobanteResponseDTO();
        resp.setSuccess(true);
        resp.setFecha(fechaComprobante.toString());
        resp.setTotal(total);
        resp.setCantidadTotalProductos(cantidadTotalProductos);
        resp.setComprobante(saved);
        resp.setErrores(errores); // puede venir vacío si no hubo problemas

        return resp;
    }

    private LocalDateTime obtenerFechaDesdeWorldClock() {
        String url = "http://worldclockapi.com/api/json/utc/now";
        try {
            RestTemplate restTemplate = new RestTemplate();
            WorldClockResponseDTO response = restTemplate.getForObject(url, WorldClockResponseDTO.class);
            if (response != null && response.getCurrentDateTime() != null) {
                // OJO: ajustá este parseo si el formato no matchea directo con LocalDateTime
                return LocalDateTime.parse(response.getCurrentDateTime());
            }
        } catch (Exception e) {
            // Si falla el servicio externo, usamos la fecha local
        }
        return LocalDateTime.now();
    }

    @Override
    public Invoice getById(Long id) {
        return invoiceRepository.findById(id)
                .orElseThrow(() -> new com.coderhouse.FacturacionSegundaEntregaCejas.exception.NotFoundException(
                        "Factura no encontrada con id " + id));
    }

    @Override
    public List<Invoice> getAll() {
        return invoiceRepository.findAll();
    }

    @Override
    public void delete(Long id) {
        if (!invoiceRepository.existsById(id)) {
            throw new com.coderhouse.FacturacionSegundaEntregaCejas.exception.NotFoundException(
                    "Factura no encontrada con id " + id);
        }
        invoiceRepository.deleteById(id);
    }
}
