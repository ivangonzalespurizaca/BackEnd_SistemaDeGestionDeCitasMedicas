package com.cibertec.app.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class MedicoRegistroDTO {

    @NotNull(message = "Debe seleccionar un usuario para registrarlo como médico.")
    private Long idUsuario;
    
    @NotBlank(message = "El número de colegiatura es obligatorio.")
    @Size(max = 20, message = "El número de colegiatura es demasiado largo.")
    private String nroColegiatura;
    
    @NotNull(message = "Debe seleccionar la especialidad del médico.")
    private Long idEspecialidad;
    
}
