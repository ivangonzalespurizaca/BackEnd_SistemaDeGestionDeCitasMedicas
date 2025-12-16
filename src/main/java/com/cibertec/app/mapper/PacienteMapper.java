package com.cibertec.app.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.cibertec.app.dto.PacienteActualizacionDTO;
import com.cibertec.app.dto.PacienteRegistroDTO;
import com.cibertec.app.dto.PacienteResponseDTO;
import com.cibertec.app.entity.Paciente;

@Mapper(componentModel = "spring")
public interface PacienteMapper {
	@Mapping(target = "idPaciente", ignore = true)
	Paciente toEntityPaciente(PacienteRegistroDTO dto);
	
	PacienteResponseDTO toPacienteResponseDTO(Paciente entity);
	
	void toPacienteUpdate(PacienteActualizacionDTO dto, @MappingTarget Paciente entity);
}
