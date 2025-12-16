package com.cibertec.app.service;

import java.util.List;

import com.cibertec.app.dto.PacienteActualizacionDTO;
import com.cibertec.app.dto.PacienteRegistroDTO;
import com.cibertec.app.dto.PacienteResponseDTO;

public interface PacienteService {

	public PacienteResponseDTO registrarPaciente(PacienteRegistroDTO dto);
	
	public List<PacienteResponseDTO> listarTodo();
	
	public PacienteResponseDTO actualizarPaciente(PacienteActualizacionDTO dto);
	
	public void eliminarPorId(Long id);
	
	public PacienteResponseDTO buscarPorId(Long id);
	
	public List<PacienteResponseDTO> buscarPorNombreDNI(String criterio);
	
	boolean existePaciente(String dni);
	
}
