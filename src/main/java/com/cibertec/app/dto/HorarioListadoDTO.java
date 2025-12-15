package com.cibertec.app.dto;

import lombok.Value;

@Value
public class HorarioListadoDTO {
	
	private Long idHorario;
    private String diaSemana; 
    private String horarioEntrada;
    private String horarioSalida;
    
}
