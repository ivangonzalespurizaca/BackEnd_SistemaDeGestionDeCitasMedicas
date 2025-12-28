package com.cibertec.app.dto;

import java.math.BigDecimal;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DashboardAdminDTO {
	private Long totalMedicos;
    private Long usuariosActivos;
    private BigDecimal ingresosMensuales;
    private List<LogCitaResponseDTO> logsRecientes;
}
