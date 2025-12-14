package com.cibertec.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cibertec.app.entity.Especialidad;

public interface EspecialidadRepository  extends JpaRepository<Especialidad, Long>{
	
	List<Especialidad> findByNombreStartingWithIgnoreCase(String nombre);
	
	boolean existsByNombre(String nombre);
	
}
