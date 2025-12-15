package com.cibertec.app.dto;

import java.time.LocalTime;

import lombok.Value;

@Value
public class HorarioProyeccionDTO {
	private String diaSemana;
    private LocalTime horarioEntrada;
    private LocalTime horarioSalida;
}
