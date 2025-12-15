package com.cibertec.app.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

import lombok.Value;

@Value
public class HistorialMedicoDetalleDTO {
	
	private Long idHistorial;
	private String diagnostico;
    private String tratamiento;
    private String notasAdicionales;
    private BigDecimal peso;
    private String presionArterial;
    
	private Long idCita; 
    private LocalDate fecha;
    private LocalTime hora;
    private String motivo;
    
    private String pacienteNombreCompleto;
    private String pacienteDni;
    
    private String nombreMedicoResponsable;
    private String nombreEspecialidad;
}
