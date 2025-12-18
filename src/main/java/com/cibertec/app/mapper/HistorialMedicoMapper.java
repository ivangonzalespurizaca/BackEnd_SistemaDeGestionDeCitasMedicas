package com.cibertec.app.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import com.cibertec.app.dto.HistorialMedicoDetalleDTO;
import com.cibertec.app.dto.HistorialMedicoResponseDTO;
import com.cibertec.app.dto.HistorialMedicoRegistrarDTO;
import com.cibertec.app.entity.Cita;
import com.cibertec.app.entity.HistorialMedico;
import com.cibertec.app.entity.Paciente;
import com.cibertec.app.entity.Usuario;

@Mapper(componentModel = "spring")
public interface HistorialMedicoMapper {
    @Mapping(target = "idHistorial", ignore = true)
    @Mapping(target = "cita", source = "cita")
    @Mapping(target = "medicoResponsable", source = "medico")
    @Mapping(target = "diagnostico", source = "dto.diagnostico")
    @Mapping(target = "tratamiento", source = "dto.tratamiento")
    HistorialMedico toHistorialEntity(HistorialMedicoRegistrarDTO dto, Cita cita, Usuario medico);

    @Mapping(target = "idCita", source = "cita.idCita")
    @Mapping(target = "pacienteNombreCompleto", source = "cita.paciente", qualifiedByName = "formatearNombreCompleto")
    @Mapping(target = "pacienteDni", source = "cita.paciente.dni")
    @Mapping(target = "fecha", source = "cita.fecha")
    @Mapping(target = "nombreMedicoResponsable", source = "medicoResponsable", qualifiedByName = "formatearNombreMedico")
    @Mapping(target = "especialidadNombre", source = "cita.medico.especialidad.nombreEspecialidad")
    HistorialMedicoResponseDTO toResponseHistorialDTO(HistorialMedico h);

    @Mapping(target = "idCita", source = "cita.idCita")
    @Mapping(target = "fecha", source = "cita.fecha")
    @Mapping(target = "hora", source = "cita.hora")
    @Mapping(target = "motivo", source = "cita.motivo")
    @Mapping(target = "pacienteNombreCompleto", source = "cita.paciente", qualifiedByName = "formatearNombreCompleto")
    @Mapping(target = "pacienteDni", source = "cita.paciente.dni")
    @Mapping(target = "nombreMedicoResponsable", source = "medicoResponsable", qualifiedByName = "formatearNombreMedico")
    @Mapping(target = "nombreEspecialidad", source = "cita.medico.especialidad.nombreEspecialidad")
    HistorialMedicoDetalleDTO toDetalleHistorialDTO(HistorialMedico h);

    @Named("formatearNombreCompleto")
    default String formatearNombreCompleto(Paciente paciente) {
        if (paciente == null) return null;
        return String.format("%s %s", paciente.getNombres(), paciente.getApellidos()).trim();
    }

    @Named("formatearNombreMedico")
    default String formatearNombreMedico(Usuario medico) {
        if (medico == null) return null;
        return String.format("%s %s", medico.getNombres(), medico.getApellidos()).trim();
    }
}
