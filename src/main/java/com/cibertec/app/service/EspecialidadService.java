package com.cibertec.app.service;

import java.util.List;

import com.cibertec.app.dto.EspecialidadActualizacionDTO;
import com.cibertec.app.dto.EspecialidadRegistroDTO;
import com.cibertec.app.dto.EspecialidadResponseDTO;

public interface EspecialidadService {
	
	List<EspecialidadResponseDTO> listarTodo();
	
	List<EspecialidadResponseDTO> buscarPorNombre(String nombre);
	
	boolean existeEspecialidad(String nombre);
	
	public void eliminarEspecialidadPorId(Long id);
	
	EspecialidadResponseDTO registrarEspecialidad(EspecialidadRegistroDTO dto);
	
	EspecialidadResponseDTO actualizarEspecialidad(EspecialidadActualizacionDTO dto);
	
	EspecialidadResponseDTO buscarPorId(Long id);
	
}
