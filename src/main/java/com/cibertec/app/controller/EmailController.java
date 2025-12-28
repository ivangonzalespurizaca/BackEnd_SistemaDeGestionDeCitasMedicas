package com.cibertec.app.controller;

import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cibertec.app.service.EmailService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api/correo")
public class EmailController {
	private final EmailService emailService;
	
	@PostMapping(value = "/enviar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<?> enviarMensaje(
	    @RequestParam String destinatario,
	    @RequestParam String asunto,
	    @RequestParam String mensaje,
	    @RequestParam( required = false) MultipartFile archivo) {
	    
	    try {
	        emailService.enviarCorreoConAdjunto(destinatario, asunto, mensaje, archivo);
	        return ResponseEntity.ok(Map.of("mensaje", "Correo con adjunto enviado exitosamente"));
	    } catch (Exception e) {
	        return ResponseEntity.status(500).body(Map.of("error", e.getMessage()));
	    }
	}
}
