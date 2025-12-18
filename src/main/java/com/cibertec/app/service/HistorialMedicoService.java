package com.cibertec.app.service;

import java.util.List;

import com.cibertec.app.dto.HistorialMedicoDetalleDTO;
import com.cibertec.app.dto.HistorialMedicoRegistrarDTO;
import com.cibertec.app.dto.HistorialMedicoResponseDTO;

public interface HistorialMedicoService {

	HistorialMedicoResponseDTO registrarHistorialMedico(HistorialMedicoRegistrarDTO dto, String username);
	
	List<HistorialMedicoResponseDTO> listarTodos();
	
	List<HistorialMedicoResponseDTO> buscarPorCriterio(String criterio);
	
	HistorialMedicoDetalleDTO buscarPorId(Long id);
	
}
