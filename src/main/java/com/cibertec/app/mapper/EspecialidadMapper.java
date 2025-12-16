package com.cibertec.app.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.cibertec.app.dto.EspecialidadActualizacionDTO;
import com.cibertec.app.dto.EspecialidadResponseDTO;
import com.cibertec.app.dto.EspecialidadRegistroDTO;
import com.cibertec.app.entity.Especialidad;

@Mapper(componentModel = "spring")
public interface EspecialidadMapper {
	@Mapping(target = "idEspecialidad", ignore = true)
	@Mapping(target = "medicos", ignore = true)
	Especialidad toEntityEspecialidad(EspecialidadRegistroDTO dto);
	
	EspecialidadResponseDTO toEspecialidadResponseDTO(Especialidad entity);
	
	@Mapping(target = "medicos", ignore = true)
	void toEspecialidadUpdate(EspecialidadActualizacionDTO dto, @MappingTarget Especialidad entity);
}
