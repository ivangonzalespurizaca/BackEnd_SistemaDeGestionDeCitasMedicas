package com.cibertec.app.dto;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PacienteActualizacionDTO {

    @NotNull(message = "El ID del paciente es obligatorio para la actualización.")
    private Long idPaciente;

    @NotBlank(message = "Los nombres son obligatorios.")
    @Size(max = 50, message = "Los nombres son demasiado largos.")
    private String nombres;
    
    @NotBlank(message = "Los apellidos son obligatorios.")
    @Size(max = 50, message = "Los apellidos son demasiado largos.")
    private String apellidos;
    
    @NotBlank(message = "El DNI es obligatorio.")
    @Pattern(regexp = "^\\d{8}$", message = "El DNI debe contener solo 8 dígitos numéricos.")
    private String dni;
    
    @NotNull(message = "La fecha de nacimiento es obligatoria.")
    @Past(message = "La fecha de nacimiento no puede ser futura.")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaNacimiento;
    
    @Pattern(regexp = "^\\d{9}$", message = "El teléfono debe contener exactamente 9 dígitos numéricos.")
    private String telefono;
    
}
