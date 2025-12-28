package com.cibertec.app.dto;

import lombok.Data;

@Data
public class EmailRequestDTO {
	private String destinatario;
    private String asunto;
    private String mensaje;
}
