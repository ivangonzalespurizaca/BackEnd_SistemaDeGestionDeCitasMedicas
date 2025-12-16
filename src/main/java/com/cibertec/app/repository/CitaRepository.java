package com.cibertec.app.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cibertec.app.entity.Cita;
import com.cibertec.app.enums.EstadoCita;

public interface CitaRepository extends JpaRepository<Cita, Long>{
	
	List<Cita> findByPaciente_DniStartingWith(String dni);
	
	List<Cita> findByPaciente_NombresStartingWith(String nombre);
	
	List<Cita> findByMedico_IdMedicoAndFechaAndHora(Long id, LocalDate fecha, LocalTime hora);
	
	List<Cita> findByMedico_IdMedicoAndFecha(Long id, LocalDate fecha);
	
	List<Cita> findByEstado(EstadoCita estado);
	
	List<Cita> findByEstadoAndPaciente_Dni(EstadoCita estado, String id);
}
