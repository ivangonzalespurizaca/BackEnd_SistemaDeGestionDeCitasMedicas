package com.cibertec.app.service;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class EmailService {

	private final JavaMailSender mailSender;
	
	public void enviarCorreoConAdjunto(String to, String subject, String content, MultipartFile archivo) {
	    try {
	        MimeMessage message = mailSender.createMimeMessage();
	        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

	        helper.setTo(to);
	        helper.setSubject(subject);
	        helper.setFrom("clinsantar@gmail.com");
	        helper.setText(content, false); 

	        if (archivo != null && !archivo.isEmpty()) {
	            helper.addAttachment(archivo.getOriginalFilename(), archivo);
	        }

	        mailSender.send(message);
	    } catch (MessagingException e) {
	        throw new RuntimeException("Error al adjuntar archivo: " + e.getMessage());
	    }
	}
	
}
