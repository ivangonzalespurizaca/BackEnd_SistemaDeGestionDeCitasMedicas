package com.cibertec.app.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class EspecialidadActualizacionDTO {
	
	@NotNull(message = "El ID de la especialidad es obligatorio para la edición.")
    private Long id;
    
    @NotBlank(message = "El nombre de la especialidad es obligatorio.")
    @Pattern(regexp = "^[A-Za-zÁÉÍÓÚáéíóúÑñ\\s]+$", message = "El nombre solo puede contener letras y espacios.")
    private String nombre;
    
}
