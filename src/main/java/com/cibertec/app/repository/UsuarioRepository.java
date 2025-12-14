package com.cibertec.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cibertec.app.entity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
	
	Usuario findByUsername(String username);
	
}
