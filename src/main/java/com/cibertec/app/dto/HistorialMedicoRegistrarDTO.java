package com.cibertec.app.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class HistorialMedicoRegistrarDTO {
	
    @NotNull(message = "El ID de la cita es obligatorio para registrar el historial.")
    private Long idCita; 
    
    @NotBlank(message = "El diagnóstico es obligatorio.")
    private String diagnostico;

    @NotBlank(message = "El tratamiento o plan es obligatorio.")
    private String tratamiento;

    private String notasAdicionales; 
    
    @DecimalMin(value = "0.00", message = "El peso debe ser positivo o cero.")
    private BigDecimal peso; 

    @Size(max = 10, message = "La presión arterial excede el límite de 10 caracteres.")
    private String presionArterial;
    
}
