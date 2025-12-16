package com.cibertec.app.dto;

import lombok.Value;

@Value
public class MedicoResponseDTO {
	
    private Long idMedico; 
    private String nombres;
    private String apellidos;
    private String dni;
    private String nroColegiatura;
    private String nombreEspecialidad;
    
}
