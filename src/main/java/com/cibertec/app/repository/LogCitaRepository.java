package com.cibertec.app.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cibertec.app.entity.LogCita;

public interface LogCitaRepository extends JpaRepository<LogCita, Long>{

	List<LogCita> findByFechaAccionBetweenOrderByFechaAccionDesc(LocalDateTime inicio, LocalDateTime fin);
	
}
