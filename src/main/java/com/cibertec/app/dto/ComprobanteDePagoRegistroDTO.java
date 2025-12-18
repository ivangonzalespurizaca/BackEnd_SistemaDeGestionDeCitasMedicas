package com.cibertec.app.dto;

import java.math.BigDecimal;

import com.cibertec.app.enums.MetodoPago;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ComprobanteDePagoRegistroDTO {
	
    @NotNull(message = "Debe seleccionar una cita pendiente de pago.")
    private Long idCita;

    @NotNull(message = "El método de pago es obligatorio.")
    private MetodoPago metodoPago; 
    
    @NotNull(message = "El monto es obligatorio.")
    @DecimalMin(value = "0.01", message = "El monto debe ser positivo.")
    private BigDecimal monto;

    @NotBlank(message = "El nombre del pagador es obligatorio.")
    @Size(min = 2, max = 50, message = "El nombre debe tener entre 2 y 50 caracteres.")
    private String nombresPagador;
    
    @NotBlank(message = "Los apellidos del pagador son obligatorios.")
    @Size(min = 2, max = 50, message = "Los apellidos deben tener entre 2 y 50 caracteres.")
    private String apellidosPagador;
    
    @NotBlank(message = "El DNI del pagador es obligatorio.")
    @Pattern(regexp = "\\d{8}", message = "El DNI debe tener 8 dígitos.")
    private String dniPagador;
    
    @NotBlank(message = "El contacto del pagador es obligatorio.")
    @Pattern(regexp = "^\\d{9}$", message = "El teléfono debe contener exactamente 9 dígitos numéricos.")
    private String contactoPagador;
}
