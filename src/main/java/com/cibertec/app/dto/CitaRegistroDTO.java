package com.cibertec.app.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CitaRegistroDTO {
	
    @NotNull(message = "Debe seleccionar una ranura de horario disponible.")
    private Long idSlot;
    
    @NotNull(message = "Debe seleccionar un paciente.")
    private Long idPaciente;
      
    @Size(max = 255, message = "El motivo no puede exceder los 255 caracteres.")
    private String motivo;
    
}
