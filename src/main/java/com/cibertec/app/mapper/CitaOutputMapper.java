package com.cibertec.app.mapper;

import java.time.LocalDate;
import java.time.Period;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.cibertec.app.dto.CitaAgendaMedicoDTO;
import com.cibertec.app.dto.CitaDetalleDTO;
import com.cibertec.app.dto.CitaResponseDTO;
import com.cibertec.app.entity.Cita;
import com.cibertec.app.entity.Medico;
import com.cibertec.app.entity.Paciente;
import com.cibertec.app.entity.Usuario;

@Mapper(componentModel = "spring")
public interface CitaOutputMapper {

    @Mapping(target = "nombreCompletoMedico", source = "medico")
    @Mapping(target = "especialidadNombre", source = "medico.especialidad.nombreEspecialidad")
    @Mapping(target = "nombreCompletoPaciente", source = "paciente")
    @Mapping(target = "dniPaciente", source = "paciente.dni")
    CitaResponseDTO toResponseDTO(Cita cita);

    @Mapping(target = "pacienteNombreCompleto", source = "paciente")
    @Mapping(target = "medicoNombreCompleto", source = "medico")
    @Mapping(target = "especialidadNombre", source = "medico.especialidad.nombreEspecialidad")
    @Mapping(target = "registradorNombreCompleto", source = "usuarioRegistro")
    @Mapping(target = "idUsuarioRegistrador", source = "usuarioRegistro.idUsuario")
    @Mapping(target = "medicoDni", source = "medico.usuario.dni")
    @Mapping(target = "pacienteDni", source = "paciente.dni")
    @Mapping(target = "idMedico", source = "medico.idMedico")
    CitaDetalleDTO toDetalleDTO(Cita cita);
    
	@Mapping(target = "idPaciente", source = "paciente.idPaciente")
    @Mapping(target = "dniPaciente", source = "paciente.dni")
    @Mapping(target = "nombreCompletoPaciente", source = "paciente")
	@Mapping(target = "idHistorial", source = "historialMedico.idHistorial")
    @Mapping(target = "edadPaciente", source = "paciente.fechaNacimiento")
    @Mapping(target = "tieneHistorialPrevio", ignore = true) 
    CitaAgendaMedicoDTO toAgendaMedicoDTO(Cita cita);

    default String mapNombreMedico(Medico medico) {
        if (medico == null || medico.getUsuario() == null) return "Sin asignar";
        return medico.getUsuario().getNombres() + " " + medico.getUsuario().getApellidos();
    }

    default String mapNombrePaciente(Paciente paciente) {
        if (paciente == null) return "Paciente no identificado";
        return paciente.getNombres() + " " + paciente.getApellidos();
    }

    default String mapNombreUsuario(Usuario usuario) {
        if (usuario == null) return "Sistema";
        return usuario.getNombres() + " " + usuario.getApellidos();
    }

    // Método para calcular la edad automáticamente
    default Integer calcularEdad(LocalDate fechaNacimiento) {
        if (fechaNacimiento == null) return 0;
        return Period.between(fechaNacimiento, LocalDate.now()).getYears();
    }
}