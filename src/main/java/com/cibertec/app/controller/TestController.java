package com.cibertec.app.controller;


import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cibertec.app.dto.EspecialidadResponseDTO;
import com.cibertec.app.service.EspecialidadService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/test")
public class TestController {

	public final EspecialidadService especialidadService;
	
	@GetMapping("/especialidades")
	ResponseEntity<List<EspecialidadResponseDTO>> listarEspecialidades(){
		List<EspecialidadResponseDTO> listado = especialidadService.listarTodo();
		return ResponseEntity.ok(listado);
	}
    
}
