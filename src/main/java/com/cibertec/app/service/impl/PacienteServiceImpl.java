package com.cibertec.app.service.impl;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cibertec.app.dto.PacienteActualizacionDTO;
import com.cibertec.app.dto.PacienteRegistroDTO;
import com.cibertec.app.dto.PacienteResponseDTO;
import com.cibertec.app.entity.Paciente;
import com.cibertec.app.mapper.PacienteMapper;
import com.cibertec.app.repository.PacienteRepository;
import com.cibertec.app.service.PacienteService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class PacienteServiceImpl implements PacienteService{

    private final PacienteRepository pacienteRepository;
    private final PacienteMapper pacienteMapper;
    
    @CacheEvict(value = "pacientes", allEntries = true)
	@Transactional
	@Override
	public PacienteResponseDTO registrarPaciente(PacienteRegistroDTO dto) {
        if (pacienteRepository.existsByDni(dto.getDni())) {
            throw new IllegalArgumentException("El DNI ya está registrado");
        }

        Paciente entity = pacienteMapper.toEntityPaciente(dto);
        Paciente guardado = pacienteRepository.save(entity);

        return pacienteMapper.toPacienteResponseDTO(guardado);
	}

    @Cacheable(value = "pacientes", key = "'lista_completa'")
	@Transactional(readOnly = true)
	@Override
	public List<PacienteResponseDTO> listarTodo() {
        return pacienteRepository.findAll()
                .stream()
                .map(pacienteMapper::toPacienteResponseDTO)
                .toList();
	}

	@CacheEvict(value = "pacientes", allEntries = true)
	@Transactional
	@Override
	public PacienteResponseDTO actualizarPaciente(PacienteActualizacionDTO dto) {
        Paciente entity = pacienteRepository.findById(dto.getIdPaciente())
                .orElseThrow(() -> new NoSuchElementException("Paciente no encontrado"));

        if (dto.getDni() != null && !dto.getDni().equals(entity.getDni())) {
            if (pacienteRepository.existsByDni(dto.getDni())) {
                throw new IllegalArgumentException("El nuevo DNI ya pertenece a otro paciente.");
            }
        }
        
        pacienteMapper.toPacienteUpdate(dto, entity);
        Paciente actualizado = pacienteRepository.save(entity);
        return pacienteMapper.toPacienteResponseDTO(actualizado);
	}

	@CacheEvict(value = "pacientes", allEntries = true)
	@Transactional
	@Override
	public void eliminarPorId(Long id) {
        if (!pacienteRepository.existsById(id)) {
            throw new NoSuchElementException("Paciente no encontrado");
        }
        pacienteRepository.deleteById(id);
	}

	@Cacheable(value = "pacientes", key = "#id")
	@Transactional(readOnly = true)
	@Override
	public PacienteResponseDTO buscarPorId(Long id) {
        Paciente entity = pacienteRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Paciente no encontrado"));
        return pacienteMapper.toPacienteResponseDTO(entity);
	}

	@Cacheable(value = "pacientes", key = "#criterio != null ? #criterio : 'sin_criterio'")
	@Transactional(readOnly = true)
	@Override
	public List<PacienteResponseDTO> buscarPorNombreDNI(String criterio) {
		
        String criterioBusqueda = null;
        
        if (criterio != null && !criterio.trim().isEmpty()) {
            criterioBusqueda = criterio.trim();
        }
        
        List<Paciente> pacientesEncontrados = pacienteRepository.buscarPorCriterio(criterioBusqueda);
        
        return pacientesEncontrados.stream()
                .map(pacienteMapper::toPacienteResponseDTO)
                .toList();
		}
}
