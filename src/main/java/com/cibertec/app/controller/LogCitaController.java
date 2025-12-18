package com.cibertec.app.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cibertec.app.dto.LogCitaResponseDTO;
import com.cibertec.app.service.impl.LogServiceImpl;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/administrador/logs")
@RequiredArgsConstructor
public class LogCitaController {
	
	private final LogServiceImpl logService;
	
	@GetMapping("/buscar")
	public ResponseEntity<List<LogCitaResponseDTO>> listarPorFecha(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
        
        List<LogCitaResponseDTO> listado = logService.listarPorFecha(fecha);
        return listado.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(listado);
    }
	
	@GetMapping
	public ResponseEntity<List<LogCitaResponseDTO>> listarTodo() {
        List<LogCitaResponseDTO> listado = logService.listarTodo();
        return listado.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(listado);
    }
}
