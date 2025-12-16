package com.cibertec.app.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cibertec.app.dto.EspecialidadActualizacionDTO;
import com.cibertec.app.dto.EspecialidadRegistroDTO;
import com.cibertec.app.dto.EspecialidadResponseDTO;
import com.cibertec.app.service.EspecialidadService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/api/admin/especialidades")
@RequiredArgsConstructor
@RestController
public class EspecialidadController {
	public final EspecialidadService especialidadService;
	
	@GetMapping
	ResponseEntity<List<EspecialidadResponseDTO>> listarEspecialidades(){
		List<EspecialidadResponseDTO> listado = especialidadService.listarTodo();
		return ResponseEntity.ok(listado);
	}
	
    @GetMapping("/buscar")
    public ResponseEntity<List<EspecialidadResponseDTO>> buscarPorNombre(
            @RequestParam String nombre) {
        return ResponseEntity.ok(especialidadService.buscarPorNombre(nombre));
    }
	
    @GetMapping("/{id}")
    public ResponseEntity<EspecialidadResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(especialidadService.buscarPorId(id));
    }
    
    @PostMapping
    public ResponseEntity<EspecialidadResponseDTO> registrar(
            @RequestBody EspecialidadRegistroDTO dto) {
        EspecialidadResponseDTO response = especialidadService.registrarEspecialidad(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
	
    @PutMapping("/{id}")
    public ResponseEntity<EspecialidadResponseDTO> actualizar(
            @PathVariable Long id,
            @RequestBody EspecialidadActualizacionDTO dto) {

        dto.setIdEspecialidad(id);
        return ResponseEntity.ok(especialidadService.actualizarEspecialidad(dto));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        especialidadService.eliminarEspecialidadPorId(id);
        return ResponseEntity.noContent().build();
    }
}
