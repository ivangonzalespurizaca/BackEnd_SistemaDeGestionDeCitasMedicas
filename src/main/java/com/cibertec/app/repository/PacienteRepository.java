package com.cibertec.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cibertec.app.entity.Paciente;

public interface PacienteRepository extends JpaRepository<Paciente, Long>{
	
	List<Paciente> findByNombresStartingWithIgnoreCase(String dni);
	
	List<Paciente> findByDniStartingWith(String dni);
	
}
