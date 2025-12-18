package com.cibertec.app.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import com.cibertec.app.dto.LogCitaResponseDTO;
import com.cibertec.app.entity.LogCita;
import com.cibertec.app.entity.Usuario;

@Mapper(componentModel = "spring")
public interface LogCitaMapper {
	@Mapping(target = "idCita", source = "cita.idCita")
    @Mapping(target = "usuarioActorNombreCompleto", source = "usuarioActor", qualifiedByName = "formatearNombreActor")
    @Mapping(target = "rolUsuarioActor", source = "usuarioActor.rol")
    LogCitaResponseDTO toResponseLogDTO(LogCita log);

    @Named("formatearNombreActor")
    default String formatearNombreActor(Usuario usuario) {
        if (usuario == null) {
            return "SISTEMA (Automático)"; 
        }
        return String.format("%s %s", usuario.getNombres(), usuario.getApellidos()).trim();
    }
}
