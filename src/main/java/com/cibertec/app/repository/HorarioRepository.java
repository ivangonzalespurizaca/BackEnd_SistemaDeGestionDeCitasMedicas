package com.cibertec.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cibertec.app.entity.HorarioDeAtencion;
import com.cibertec.app.enums.DiaSemana;

public interface HorarioRepository extends JpaRepository<HorarioDeAtencion, Long>{

	List<HorarioDeAtencion> findByMedico_IdMedico(Long idMedico);

	List<HorarioDeAtencion> findByMedico_IdMedicoAndDiaSemana(Long idMedico, DiaSemana diaSemana);
	
}
