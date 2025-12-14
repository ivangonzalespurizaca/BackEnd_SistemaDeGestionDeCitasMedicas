package com.cibertec.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cibertec.app.entity.Medico;

public interface MedicoRepository extends JpaRepository<Medico, Long>{
	
	boolean existsByDni(String dni);
	
	List<Medico> findByNombresStartingWithIgnoreCase(String nombre);
	
	List<Medico> findByDniStartingWith(String dni);
	
}
