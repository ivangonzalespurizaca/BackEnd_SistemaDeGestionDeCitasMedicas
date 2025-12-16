package com.cibertec.app.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

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

	@Override
	public List<EspecialidadResponseDTO> listarTodo() {
		return especialidadRepository.findAll()
				.stream()
				.map(especialidadMapper::toEspecialidadResponseDTO)
				.toList();
	}

	@Override
	public List<EspecialidadResponseDTO> buscarPorNombre(String nombre) {
		
		List<Especialidad> especialidadesEncontradas;
	    
	    if (nombre != null && !nombre.trim().isEmpty()) {
	        especialidadesEncontradas = especialidadRepository.findByNombreEspecialidadStartingWithIgnoreCase(nombre);
	    } 
	    else {
	        especialidadesEncontradas = especialidadRepository.findAll();
	    }
	    return especialidadesEncontradas.stream()
	            .map(especialidadMapper::toEspecialidadResponseDTO)
	            .toList();
	}

	@Override
	public boolean existeEspecialidad(String nombre) {
		return especialidadRepository.existsByNombreEspecialidad(nombre);
	}

	@Override
	public void eliminarPorId(Long id) {

        if (!especialidadRepository.existsById(id)) {
            throw new RuntimeException("Especialidad no encontrada");
        }

        especialidadRepository.deleteById(id);
		
	}

	@Override
	public EspecialidadResponseDTO registrarEspecialidad(EspecialidadRegistroDTO dto) {
        if (especialidadRepository.existsByNombreEspecialidad(dto.getNombreEspecialidad())) {
            throw new IllegalArgumentException("La especialidad ya existe");
        }

        Especialidad entity = especialidadMapper.toEntityEspecialidad(dto);
        Especialidad guardada = especialidadRepository.save(entity);

        return especialidadMapper.toEspecialidadResponseDTO(guardada);
	}

	@Override
	public EspecialidadResponseDTO actualizarEspecialidad(EspecialidadActualizacionDTO dto) {
        Especialidad entity = especialidadRepository.findById(dto.getIdEspecialidad())
                .orElseThrow(() -> new RuntimeException("Especialidad no encontrada"));

        especialidadMapper.toEspecialidadUpdate(dto, entity);

        Especialidad actualizada = especialidadRepository.save(entity);

        return especialidadMapper.toEspecialidadResponseDTO(actualizada);
	}

	@Override
	public EspecialidadResponseDTO buscarPorId(Long id) {
        Especialidad entity = especialidadRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Especialidad no encontrada"));
        return especialidadMapper.toEspecialidadResponseDTO(entity);
	}

}
