package com.cibertec.app.service;

import java.util.List;

import com.cibertec.app.dto.UsuarioActualizacionDTO;
import com.cibertec.app.dto.UsuarioRegistroDTO;
import com.cibertec.app.dto.UsuarioResponseDTO;

public interface UsuarioService {
	
	public UsuarioResponseDTO registrarUsuario(UsuarioRegistroDTO dto);
	
	public UsuarioResponseDTO actualizarUsuario(UsuarioActualizacionDTO dto);
	
	public UsuarioResponseDTO buscarPorUserName(String userName);
	
	public UsuarioResponseDTO buscarPorId(Long id);
	
	public List<UsuarioResponseDTO> buscarUsuarioParaMedicosDisponibles(String dni);
	
}
