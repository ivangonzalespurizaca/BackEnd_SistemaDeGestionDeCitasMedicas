package com.cibertec.app.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cibertec.app.dto.UsuarioActualizacionDTO;
import com.cibertec.app.dto.UsuarioRegistroDTO;
import com.cibertec.app.dto.UsuarioResponseDTO;
import com.cibertec.app.service.UsuarioService;

import lombok.RequiredArgsConstructor;

@RequestMapping("api/administrador/usuarios")
@RequiredArgsConstructor
@RestController
public class UsuarioController {
	public final UsuarioService usuarioService;
	
	@PostMapping
	public ResponseEntity<UsuarioResponseDTO> registrarUsuario(
			@RequestBody UsuarioRegistroDTO dto) {
		try {
			UsuarioResponseDTO response = usuarioService.registrarUsuario(dto);
			return ResponseEntity.status(HttpStatus.CREATED).body(response);
		} catch (IllegalArgumentException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<UsuarioResponseDTO> actualizarUsuario(
            @PathVariable Long id, 
            @RequestBody UsuarioActualizacionDTO dto) {
		
        dto.setIdUsuario(id); 
		
		try {
			return ResponseEntity.ok(usuarioService.actualizarUsuario(dto));
		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<UsuarioResponseDTO> buscarPorId(@PathVariable Long id) {
		try {
			UsuarioResponseDTO response = usuarioService.buscarPorId(id);
			return ResponseEntity.ok(response);
		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}
	
	@GetMapping("/username/{username}")
	public ResponseEntity<UsuarioResponseDTO> buscarPorUserName(@PathVariable String username) {
		try {
			UsuarioResponseDTO response = usuarioService.buscarPorUserName(username);
			return ResponseEntity.ok(response);
		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}
	
	@GetMapping("/buscar")
	public ResponseEntity<List<UsuarioResponseDTO>> buscarDisponiblesParaMedico(
			@RequestParam(required = false) String criterio) {
		
		List<UsuarioResponseDTO> listado = usuarioService.buscarUsuarioParaMedicosDisponibles(criterio);
		
		if (listado.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		
		return ResponseEntity.ok(listado);
	}
}
