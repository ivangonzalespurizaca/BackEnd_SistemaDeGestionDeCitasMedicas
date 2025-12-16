package com.cibertec.app.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cibertec.app.entity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
	
	Optional<Usuario> findByUsername(String username);
	
	boolean existsByUsername(String username);
	
	@Query("SELECT u FROM Usuario u WHERE "
	         + "u.rol = com.cibertec.app.enums.TipoRol.MEDICO AND " 
	         + "TYPE(u) = Usuario AND "
	         + "(:dniFiltro IS NULL OR u.dni LIKE CONCAT(:dniFiltro, '%'))")
	    List<Usuario> buscarUsuariosDisponiblesParaMedico(@Param("dniFiltro") String dniFiltro);
}
