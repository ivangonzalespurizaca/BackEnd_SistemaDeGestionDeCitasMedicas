package com.cibertec.app.dto;

import java.util.List;

import lombok.Value;

@Value
public class MedicoDashboardDTO {
	Long totalCitasHoy;
    Long atencionesFinalizadas;
    Long pacientesPendientes;
    CitaAgendaMedicoDTO siguientePaciente; 
    List<CitaAgendaMedicoDTO> agendaDelDia;
}
