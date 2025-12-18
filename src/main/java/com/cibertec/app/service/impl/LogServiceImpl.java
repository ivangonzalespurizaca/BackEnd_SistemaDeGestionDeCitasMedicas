package com.cibertec.app.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cibertec.app.dto.LogCitaResponseDTO;
import com.cibertec.app.entity.Cita;
import com.cibertec.app.entity.LogCita;
import com.cibertec.app.entity.Usuario;
import com.cibertec.app.enums.Accion;
import com.cibertec.app.mapper.LogCitaMapper;
import com.cibertec.app.repository.LogCitaRepository;
import com.cibertec.app.service.LogService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LogServiceImpl implements LogService{
	
	private final LogCitaMapper logMapper;
	private final LogCitaRepository logCitaRepository;

    @Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void registrarLog(Cita cita, Accion accion, String detalle, Usuario usuarioActor) {
        LogCita log = LogCita.builder()
                .cita(cita)
                .accion(accion)
                .usuarioActor(usuarioActor)
                .detalle(detalle)
                .fechaAccion(LocalDateTime.now())
                .build();
        
        logCitaRepository.save(log);
	}

	@Override
	public List<LogCitaResponseDTO> listarTodo() {
		return logCitaRepository.findAll().stream()
                .map(logMapper::toResponseLogDTO)
                .toList();
	}

	@Override
	public List<LogCitaResponseDTO> listarPorFecha(LocalDate fecha) {
        LocalDateTime inicio = fecha.atStartOfDay();
        LocalDateTime fin = fecha.atTime(LocalTime.MAX);

        return logCitaRepository.findByFechaAccionBetweenOrderByFechaAccionDesc(inicio, fin)
                .stream()
                .map(logMapper::toResponseLogDTO)
                .toList();
	}
}
