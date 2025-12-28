package com.cibertec.app.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CajeroDashboardDTO {
	private Double recaudacionTotalDia;
    private Double recaudacionEfectivo;
    private Double recaudacionTarjeta;
    private Double recaudacionTransferencia;
    private Long citasPendientesCobro; 
    private List<ComprobanteRecienteDTO> ultimosPagos;
}
