package com.cibertec.app.dto;

import java.time.LocalTime;

import com.cibertec.app.enums.EstadoCita;

import lombok.Value;

@Value
public class CitaAgendaMedicoDTO {
	Long idCita;
    LocalTime hora;
    EstadoCita estado;
    
    Long idPaciente; 
    String nombreCompletoPaciente;
    String dniPaciente;
    Integer edadPaciente; 
    
    String motivo; 
    boolean tieneHistorialPrevio;
    
    Long idHistorial;
}
