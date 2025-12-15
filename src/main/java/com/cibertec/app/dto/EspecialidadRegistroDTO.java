package com.cibertec.app.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class EspecialidadRegistroDTO {
	
	@NotBlank(message = "El nombre de la especialidad es obligatorio.")
    @Pattern(regexp = "^[A-Za-zÁÉÍÓÚáéíóúÑñ]+(\\s?[A-Za-zÁÉÍÓÚáéíóúÑñ]+)*$", message = "El nombre solo puede contener letras y espacios.")
    private String nombre;
	
}
