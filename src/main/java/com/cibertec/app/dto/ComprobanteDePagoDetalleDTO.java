package com.cibertec.app.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import lombok.Value;

@Value
public class ComprobanteDePagoDetalleDTO {
	
    private Long idComprobante;
    private LocalDateTime fechaEmision;
    private BigDecimal monto;
    private String metodoPagoNombre; 
    private String estadoNombre; 
    
    private String nombrePagador;
    private String apellidosPagador;
    private String dniPagador;
    private String contactoPagador; 
    
    private Long idCita; 
    private LocalDate fechaCita;
    private LocalTime horaCita;
    private String motivoCita;
    
    private String pacienteNombreCompleto;
    private String medicoNombreCompleto;
    
    private String cajeroNombreCompleto;
}
