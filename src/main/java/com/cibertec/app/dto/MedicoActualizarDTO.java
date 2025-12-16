package com.cibertec.app.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class MedicoActualizarDTO {
	
	@NotNull 
    private Long idMedico; 
    
    @NotBlank(message = "El número de colegiatura es obligatorio.")
    @Size(max = 20, message = "El número de colegiatura es demasiado largo.")
    private String nroColegiatura; 
    
    @NotNull(message = "Debe seleccionar la especialidad del médico.")
    private Long idEspecialidad;
    
}
