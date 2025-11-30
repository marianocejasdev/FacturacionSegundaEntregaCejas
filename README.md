# Facturación – Entrega Final (Proyecto Java)
Alumno: Mariano Cejas  

Este repositorio contiene la entrega final del proyecto de Facturación, desarrollada en Java + Spring Boot.  
Incluye: API REST completa, validaciones de negocio, consumo de servicio externo y manejo de stock.

---

## ✔ Tecnologías utilizadas
- Java 17  
- Spring Boot 3  
- Spring Web  
- Spring Data JPA  
- H2 Database  
- Maven  

---

## 🚀 Funcionalidades principales

### **1. Crear Comprobante (POST /api/invoice/)**
El request cumple con el formato requerido:

```json
{
  "cliente": { "clienteid": 1 },
  "lineas": [
    {
      "cantidad": 1,
      "producto": { "productoid": 1 }
    }
  ]
}
