package com.cibertec.app.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.cibertec.app.enums.MetodoPago;

import lombok.Value;

@Value
public class ComprobanteRecienteDTO {
	Long idComprobante;
    String pacienteNombre; 
    BigDecimal monto;
    MetodoPago metodoPago;
    LocalDateTime fechaEmision;
    String estado;
}
