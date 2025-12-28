package com.cibertec.app.service.impl;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cibertec.app.dto.EspecialidadActualizacionDTO;
import com.cibertec.app.dto.EspecialidadRegistroDTO;
import com.cibertec.app.dto.EspecialidadResponseDTO;
import com.cibertec.app.entity.Especialidad;
import com.cibertec.app.mapper.EspecialidadMapper;
import com.cibertec.app.repository.EspecialidadRepository;
import com.cibertec.app.service.EspecialidadService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EspecialidadServiceImpl implements EspecialidadService{
	
	private final EspecialidadRepository especialidadRepository;
	private final EspecialidadMapper especialidadMapper;

	@Cacheable(value = "especialidades", key = "'lista_completa'")
	@Transactional(readOnly = true)
	@Override
	public List<EspecialidadResponseDTO> listarTodo() {
		return especialidadRepository.findAllByOrderByIdEspecialidadAsc()
				.stream()
				.map(especialidadMapper::toEspecialidadResponseDTO)
				.toList();
	}

	@Cacheable(value = "especialidades", key = "#nombre != null ? #nombre : 'todas'")
	@Transactional(readOnly = true)
	@Override
	public List<EspecialidadResponseDTO> buscarPorNombre(String nombre) {
		
		List<Especialidad> especialidadesEncontradas;
	    
	    if (nombre != null && !nombre.trim().isEmpty()) {
	        especialidadesEncontradas = especialidadRepository.findByNombreEspecialidadStartingWithIgnoreCase(nombre);
	    } 
	    else {
	        especialidadesEncontradas = especialidadRepository.findAllByOrderByIdEspecialidadAsc();
	    }
	    return especialidadesEncontradas.stream()
	            .map(especialidadMapper::toEspecialidadResponseDTO)
	            .toList();
	}

	@CacheEvict(value = "especialidades", allEntries = true)
	@Transactional
	@Override
	public void eliminarPorId(Long id) {

        if (!especialidadRepository.existsById(id)) {
            throw new NoSuchElementException("Especialidad no encontrada");
        }
        especialidadRepository.deleteById(id);
		
	}

	@CacheEvict(value = "especialidades", allEntries = true)
	@Transactional
	@Override
	public EspecialidadResponseDTO registrarEspecialidad(EspecialidadRegistroDTO dto) {
        if (especialidadRepository.existsByNombreEspecialidad(dto.getNombreEspecialidad())) {
            throw new IllegalArgumentException("La especialidad ya existe");
        }

        Especialidad entity = especialidadMapper.toEntityEspecialidad(dto);
        Especialidad guardada = especialidadRepository.save(entity);

        return especialidadMapper.toEspecialidadResponseDTO(guardada);
	}

	@CacheEvict(value = "especialidades", allEntries = true)
	@Transactional
	@Override
	public EspecialidadResponseDTO actualizarEspecialidad(EspecialidadActualizacionDTO dto) {
        Especialidad entity = especialidadRepository.findById(dto.getIdEspecialidad())
                .orElseThrow(() -> new NoSuchElementException("Especialidad no encontrada"));

        if (dto.getNombreEspecialidad() != null && !dto.getNombreEspecialidad().equalsIgnoreCase(entity.getNombreEspecialidad())) {
            if (especialidadRepository.existsByNombreEspecialidad(dto.getNombreEspecialidad())) {
                throw new IllegalArgumentException("Ya existe otra especialidad con el nombre: " + dto.getNombreEspecialidad());
            }
        }
        
        especialidadMapper.toEspecialidadUpdate(dto, entity);
        Especialidad actualizada = especialidadRepository.save(entity);

        return especialidadMapper.toEspecialidadResponseDTO(actualizada);
	}
	
	@Cacheable(value = "especialidades", key = "#id")
	@Transactional(readOnly = true)
	@Override
	public EspecialidadResponseDTO buscarPorId(Long id) {
        Especialidad entity = especialidadRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Especialidad no encontrada"));
        return especialidadMapper.toEspecialidadResponseDTO(entity);
	}

}
