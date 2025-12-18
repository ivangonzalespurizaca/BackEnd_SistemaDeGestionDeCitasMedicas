package com.cibertec.app.service;

import java.util.List;

import com.cibertec.app.dto.ComprobanteDePagoDetalleDTO;
import com.cibertec.app.dto.ComprobanteDePagoRegistroDTO;
import com.cibertec.app.dto.ComprobanteDePagoResponseDTO;

public interface ComprobanteDePagoService {
	
	ComprobanteDePagoResponseDTO registrarComprobanteDePago(ComprobanteDePagoRegistroDTO dto, String username);
	
	List<ComprobanteDePagoResponseDTO> listarTodos();
	
	List<ComprobanteDePagoResponseDTO> buscarPorCriterio(String criterio);
	
	void anularComprobanteDePago(Long id, String username);
	
	ComprobanteDePagoDetalleDTO buscarPorId(Long id);
	
}
