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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cibertec.app.dto.HistorialMedicoDetalleDTO;
import com.cibertec.app.dto.HistorialMedicoRegistrarDTO;
import com.cibertec.app.dto.HistorialMedicoResponseDTO;
import com.cibertec.app.service.HistorialMedicoPdfService;
import com.cibertec.app.service.HistorialMedicoService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/medico/historiales")
@RequiredArgsConstructor
public class HistorialMedicoController {
	
	private final HistorialMedicoService historialService;
	private final HistorialMedicoPdfService pdfService;
	
	@PostMapping
    public ResponseEntity<HistorialMedicoResponseDTO> registrar(
            @Valid @RequestBody HistorialMedicoRegistrarDTO dto,
            Principal principal) {
        return new ResponseEntity<>(historialService.registrarHistorialMedico(dto, principal.getName()), HttpStatus.CREATED);
    }
	
	@GetMapping
    public ResponseEntity<List<HistorialMedicoResponseDTO>> listarTodos() {
		List<HistorialMedicoResponseDTO> listado = historialService.listarTodos();
        return listado.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(listado);
    }
	
	@GetMapping("/buscar")
    public ResponseEntity<List<HistorialMedicoResponseDTO>> buscar(
            @RequestParam(required = false) String criterio) {
		List<HistorialMedicoResponseDTO> listado = historialService.buscarPorCriterio(criterio);
		return listado.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(listado);
    }
	
	@GetMapping("/{id}")
    public ResponseEntity<HistorialMedicoDetalleDTO> obtenerDetalle(@PathVariable Long id) {
        return ResponseEntity.ok(historialService.buscarPorId(id));
    }
	
    @GetMapping("/reporte/pdf")
    public ResponseEntity<InputStreamResource> descargarReportePdf(
        @RequestParam(required = false) String criterio
    ) {

        List<HistorialMedicoResponseDTO> citas = historialService.buscarPorCriterio(criterio); 

        ByteArrayInputStream bis = pdfService.generarReporteHistorial(citas);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=reporte_busqueda_historial_medico.pdf");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(bis));
    }
	
}
