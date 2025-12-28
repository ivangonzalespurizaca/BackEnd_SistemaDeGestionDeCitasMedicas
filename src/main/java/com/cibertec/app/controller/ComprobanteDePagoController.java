package com.cibertec.app.controller;

import java.io.ByteArrayInputStream;
import java.security.Principal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.cibertec.app.dto.ComprobanteDePagoDetalleDTO;
import com.cibertec.app.dto.ComprobanteDePagoRegistroDTO;
import com.cibertec.app.dto.ComprobanteDePagoResponseDTO;
import com.cibertec.app.enums.MetodoPago;
import com.cibertec.app.service.CdpPdfService;
import com.cibertec.app.service.ComprobanteDePagoService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/cajero/pagos")
@RequiredArgsConstructor
public class ComprobanteDePagoController {
	private final ComprobanteDePagoService pagoService;
	private final CdpPdfService cdpPDFService;

    @PostMapping
    public ResponseEntity<ComprobanteDePagoResponseDTO> registrar(
            @Valid @RequestBody ComprobanteDePagoRegistroDTO dto, 
            Principal principal) {
        return new ResponseEntity<>(pagoService.registrarComprobanteDePago(dto, principal.getName()), HttpStatus.CREATED);
    }
    
    @GetMapping
    public ResponseEntity<List<ComprobanteDePagoResponseDTO>> listarTodo() {
    	List<ComprobanteDePagoResponseDTO> listado = pagoService.listarTodos();
        return listado.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(listado);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ComprobanteDePagoDetalleDTO> obtenerDetalle(@PathVariable Long id) {
        return ResponseEntity.ok(pagoService.buscarPorId(id));
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<ComprobanteDePagoResponseDTO>> buscar(@RequestParam String filtro) {
    	List<ComprobanteDePagoResponseDTO> listado = pagoService.buscarPorCriterio(filtro);
        return listado.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(listado);
    }

    @PatchMapping("/{id}/anular")
    public ResponseEntity<Void> anular(@PathVariable Long id, Principal principal) {
        pagoService.anularComprobanteDePago(id, principal.getName());
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/metodos")
    public ResponseEntity<List<Map<String, String>>> listarMetodos() {
        List<Map<String, String>> metodos = Arrays.stream(MetodoPago.values())
                .map(m -> Map.of(
                    "value", m.name(), 
                    "label", m.name().replace("_", " ") 
                )).toList();
                
        return ResponseEntity.ok(metodos);
    }
    
    @GetMapping("/reporte/pdf")
    public ResponseEntity<InputStreamResource> descargarReportePdf(
        @RequestParam(required = false) String criterio
    ) {

        List<ComprobanteDePagoResponseDTO> cdp = pagoService.buscarPorCriterio(criterio); 

        ByteArrayInputStream bis = cdpPDFService.generarHistorialPagosPdf(cdp);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=reporte_busqueda_cdp.pdf");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(bis));
    }
     
}
