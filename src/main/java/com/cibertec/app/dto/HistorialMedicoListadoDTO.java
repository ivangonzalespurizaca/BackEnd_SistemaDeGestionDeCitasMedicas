package com.cibertec.app.dto;

import java.time.LocalDate;

import lombok.Value;

@Value
public class HistorialMedicoListadoDTO {
    private Long idHistorial;
    private Long idCita; 

    private String pacienteNombreCompleto;
    private String pacienteDni;

    private LocalDate fecha;
    private String nombreMedicoResponsable;
    private String especialidadNombre;
    
}
