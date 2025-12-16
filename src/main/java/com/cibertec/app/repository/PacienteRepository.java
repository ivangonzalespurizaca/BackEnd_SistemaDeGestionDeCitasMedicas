package com.cibertec.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cibertec.app.entity.Paciente;

public interface PacienteRepository extends JpaRepository<Paciente, Long>{
	
	
	@Query("SELECT p FROM Paciente p WHERE "
	         + "(:criterio IS NULL) OR "
	         + "(LOWER(p.nombres) LIKE LOWER(CONCAT(:criterio, '%'))) OR "
	         + "(p.dni LIKE CONCAT(:criterio, '%'))")
	List<Paciente> buscarPorCriterio(String criterio);
	
	boolean existsByDni(String dni);
}
