package com.cibertec.app.dto;

import java.time.LocalDate;

import lombok.Value;

@Value
public class PacienteResponseDTO {
	
	private Long idPaciente;
    private String nombres;
    private String apellidos;
    private String dni;
    private LocalDate fechaNacimiento;
    private String telefono;
    
}
