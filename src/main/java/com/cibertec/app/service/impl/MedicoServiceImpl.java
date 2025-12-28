package com.cibertec.app.service.impl;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cibertec.app.dto.MedicoActualizarDTO;
import com.cibertec.app.dto.MedicoRegistroDTO;
import com.cibertec.app.dto.MedicoResponseDTO;
import com.cibertec.app.dto.MedicoVistaModificarDTO;
import com.cibertec.app.entity.Especialidad;
import com.cibertec.app.entity.Medico;
import com.cibertec.app.entity.Usuario;
import com.cibertec.app.enums.TipoRol;
import com.cibertec.app.mapper.MedicoMapper;
import com.cibertec.app.repository.EspecialidadRepository;
import com.cibertec.app.repository.MedicoRepository;
import com.cibertec.app.repository.UsuarioRepository;
import com.cibertec.app.service.MedicoService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MedicoServiceImpl implements MedicoService {
	
	private final MedicoRepository medicoRepository;
	private final UsuarioRepository usuarioRepository;
	private final EspecialidadRepository especialidadRepository;
	private final MedicoMapper medicoMapper;

	@Override
	@Cacheable(value = "medicos", key = "'lista_completa'")
	@Transactional(readOnly = true)
	public List<MedicoResponseDTO> listarTodo() {
		return medicoRepository.findAll().stream()
				.map(medicoMapper::toMedicoResponseDTO)
				.toList();
	}

	@Override
	@CacheEvict(value = "medicos", allEntries = true)
	@Transactional
	public MedicoResponseDTO registrarMedico(MedicoRegistroDTO dto) {
		Usuario usuarioBase = usuarioRepository.findById(dto.getIdUsuario())
				.orElseThrow(() -> new NoSuchElementException("Usuario base no encontrado para el ID: " + dto.getIdUsuario()));
		
		if (usuarioBase.getRol() != TipoRol.MEDICO) {
			throw new IllegalArgumentException("El usuario debe tener el rol MEDICO para crear un perfil médico.");
		}
		
		if (medicoRepository.existsByUsuario_IdUsuario(dto.getIdUsuario())) {
			throw new IllegalArgumentException("Ya existe un perfil médico asociado a este usuario.");
		}
		
		if (medicoRepository.existsByNroColegiatura(dto.getNroColegiatura())) {
            throw new IllegalArgumentException("El número de colegiatura ya está registrado.");
        }
		
		Especialidad especialidad = buscarEspecialidad(dto.getIdEspecialidad());
		
		Medico medico = medicoMapper.toEntityMedico(dto);
		
		medico.setUsuario(usuarioBase);
		
		medico.setEspecialidad(especialidad);
		
		Medico guardado = medicoRepository.save(medico);
		
		return medicoMapper.toMedicoResponseDTO(guardado);
	}

	@CacheEvict(value = "medicos", allEntries = true)
	@Override
	@Transactional
	public MedicoResponseDTO actualizarMedico(MedicoActualizarDTO dto) {
		
		Medico medicoExistente = buscarMedico(dto.getIdMedico());
		
		if (!medicoExistente.getNroColegiatura().equals(dto.getNroColegiatura())) {
			if(medicoRepository.existsByNroColegiatura(dto.getNroColegiatura())) {
				throw new IllegalArgumentException("El número de colegiatura ya está registrado.");
			}
        }
		
		Especialidad nuevaEspecialidad = buscarEspecialidad(dto.getIdEspecialidad());
		
		medicoMapper.toMedicoUpdate(dto, medicoExistente);
		
		medicoExistente.setEspecialidad(nuevaEspecialidad);
		
		Medico actualizado = medicoRepository.save(medicoExistente);
		
		return medicoMapper.toMedicoResponseDTO(actualizado);
	}

	@Override
	@Cacheable(value = "medicos", key = "#id")
	@Transactional(readOnly = true)
	public MedicoResponseDTO buscarPorId(Long id) {
		Medico medico = buscarMedico(id);
		return medicoMapper.toMedicoResponseDTO(medico);
	}

	@Override
	@Cacheable(value = "medicos", key = "#criterio != null ? #criterio : 'sin_criterio'")
	@Transactional(readOnly = true)
	public List<MedicoResponseDTO> buscarPorCriterio(String criterio) {
		String criterioLimpio = (criterio != null && !criterio.trim().isEmpty()) ? criterio.trim() : null;
        
        List<Medico> medicos = medicoRepository.buscarPorCriterio(criterioLimpio);
        
        return medicos.stream()
                .map(medicoMapper::toMedicoResponseDTO)
                .toList();
	}
	
	private Especialidad buscarEspecialidad(Long idEspecialidad) {
		return especialidadRepository.findById(idEspecialidad)
				.orElseThrow(() -> new NoSuchElementException("Especialidad no encontrada con ID: " + idEspecialidad));
	}
	
	private Medico buscarMedico(Long id) {
		return medicoRepository.findById(id)
				.orElseThrow(() -> new NoSuchElementException("Perfil de Médico no encontrado con ID: " + id));
	}

	@Cacheable(value = "medicos", key = "'edit_' + #id")
	@Override
	@Transactional(readOnly = true)
	public MedicoVistaModificarDTO obtenerParaEditar(Long id) {
		Medico medico = medicoRepository.findById(id)
	            .orElseThrow(() -> new RuntimeException("Médico no encontrado con ID: " + id));
	    MedicoVistaModificarDTO dto = medicoMapper.toVistaModificarDTO(medico);
	    return dto;
	}

	@Override
	@Cacheable(value = "medicos", key = "'user_' + #username")
    @Transactional(readOnly = true)
    public Long obtenerIdMedicoPorUsername(String username) {
        // El repositorio devuelve Optional<Long>
        // .orElseThrow() lo convierte a Long o lanza error si no existe
        return medicoRepository.findIdMedicoByUsername(username)
                .orElseThrow(() -> new RuntimeException("Médico no encontrado para el usuario: " + username));
    }
}
