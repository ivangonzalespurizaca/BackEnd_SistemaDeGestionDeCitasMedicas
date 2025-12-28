package com.cibertec.app.controller;

import java.io.ByteArrayInputStream;
import java.security.Principal;
import java.util.List;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cibertec.app.dto.CitaActualizacionDTO;
import com.cibertec.app.dto.CitaDetalleDTO;
import com.cibertec.app.dto.CitaRegistroDTO;
import com.cibertec.app.dto.CitaResponseDTO;
import com.cibertec.app.enums.EstadoCita;
import com.cibertec.app.service.CitaPdfService;
import com.cibertec.app.service.CitaService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/recepcionista/citas")
@RequiredArgsConstructor
public class CitaController {
	private final CitaService citaService;
	private final CitaPdfService citaPdfService;
	
	@GetMapping
    public ResponseEntity<List<CitaResponseDTO>> listarTodo() {
		List<CitaResponseDTO> listado = citaService.listarTodo();
        return listado.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(listado);
    }
	
	@GetMapping("/{id}")
    public ResponseEntity<CitaDetalleDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(citaService.buscarPorId(id));
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<CitaResponseDTO>> buscarPorCriterio(@RequestParam String filtro) {
        List<CitaResponseDTO> listado = citaService.buscarPorCriterio(filtro);
        return listado.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(listado);
    }

    @GetMapping("/pendientes")
    public ResponseEntity<List<CitaResponseDTO>> listarPendientes() {
    	List<CitaResponseDTO> listado = citaService.listarPorEstado(EstadoCita.PENDIENTE);
        return listado.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(listado);
    }

    @GetMapping("/pendientes/buscar")
    public ResponseEntity<List<CitaResponseDTO>> listarPendientesPaciente(@RequestParam String filtro) {
    	List<CitaResponseDTO> listado = citaService.listarPendientesPorPaciente(filtro);
        return listado.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(listado);
    }
    
    @GetMapping("/confirmadas/buscar")
    public ResponseEntity<List<CitaResponseDTO>> listarConfirmadasPaciente(@RequestParam String filtro) {
    	List<CitaResponseDTO> listado = citaService.listarConfirmadasPorPaciente(filtro);
        return listado.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(listado);
    }
	
	@PostMapping
    public ResponseEntity<CitaResponseDTO> registrar(
            @Valid @RequestBody CitaRegistroDTO dto, 
            Principal principal) {
        return new ResponseEntity<>(citaService.registrarCita(dto, principal.getName()), HttpStatus.CREATED);
    }
	
	@PutMapping("/{id}")
    public ResponseEntity<CitaResponseDTO> actualizar(
    		@PathVariable Long id,
            @Valid @RequestBody CitaActualizacionDTO dto, 
            Principal principal) {
		dto.setIdCita(id);
        return ResponseEntity.ok(citaService.actualizarCita(dto, principal.getName()));
    }
	
	@PatchMapping("/{id}/anular")
    public ResponseEntity<Void> anular(@PathVariable Long id, Principal principal) {
        citaService.anularCita(id, principal.getName());
        return ResponseEntity.noContent().build();
    }

	//Probar pendiente
    @PatchMapping("/{id}/completar")
    public ResponseEntity<Void> completar(@PathVariable Long id, Principal principal) {
        citaService.completarCita(id, principal.getName());
        return ResponseEntity.noContent().build();
    }
    
    //Prabar pendiente
    @PatchMapping("/{id}/no-asistio")
    public ResponseEntity<Void> marcarNoAsistio(@PathVariable Long id, Principal principal) {
        citaService.marcarNoAsistio(id, principal.getName());
        return ResponseEntity.noContent().build();
    }
	
    @GetMapping("/reporte/pdf")
    public ResponseEntity<InputStreamResource> descargarReportePdf(
        @RequestParam(required = false) String criterio
    ) {

        List<CitaResponseDTO> citas = citaService.buscarPorCriterio(criterio); 

        ByteArrayInputStream bis = citaPdfService.generarReporteCitas(citas);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=reporte_busqueda_citas.pdf");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(bis));
    }
}
