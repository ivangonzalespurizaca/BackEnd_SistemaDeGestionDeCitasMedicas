package com.cibertec.app.dto;

import com.cibertec.app.enums.TipoRol;

import lombok.Value;

@Value
public class UsuarioListadoDTO {
	
	private Long idUsuario;
    private String dni;
    private String nombres;
    private String apellidos;
    private TipoRol rol;
    
}
