package com.cibertec.app.dto;

import java.time.LocalDateTime;

import com.cibertec.app.enums.Accion;

import lombok.Value;

@Value
public class LogCitaResponseDTO {
    private Long idLog;
    private LocalDateTime fechaAccion;
    private Accion accion; 
    private String detalle;

    private Long idCita; 

    private String usuarioActorNombreCompleto; 
    private String rolUsuarioActor;
}
