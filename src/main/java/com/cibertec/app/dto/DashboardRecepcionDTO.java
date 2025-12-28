package com.cibertec.app.dto;

import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DashboardRecepcionDTO {
	private Long citasHoy;
    private Long cuposDisponibles;
    private Long pacientesTotales;
    private List<Map<String, Object>> proximasCitas;
}
