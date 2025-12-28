package com.cibertec.app.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.cibertec.app.dto.CajeroDashboardDTO;
import com.cibertec.app.dto.CitaAgendaMedicoDTO;
import com.cibertec.app.dto.ComprobanteRecienteDTO;
import com.cibertec.app.dto.DashboardAdminDTO;
import com.cibertec.app.dto.DashboardRecepcionDTO;
import com.cibertec.app.dto.LogCitaResponseDTO;
import com.cibertec.app.dto.MedicoDashboardDTO;
import com.cibertec.app.entity.Cita;
import com.cibertec.app.enums.MetodoPago;
import com.cibertec.app.mapper.CitaOutputMapper;
import com.cibertec.app.mapper.ComprobantePagoMapper;
import com.cibertec.app.repository.CitaRepository;
import com.cibertec.app.repository.ComprobanteDePagoRepository;
import com.cibertec.app.repository.LogCitaRepository;
import com.cibertec.app.repository.MedicoRepository;
import com.cibertec.app.repository.PacienteRepository;
import com.cibertec.app.repository.SlotHorarioRepository;
import com.cibertec.app.repository.UsuarioRepository;
import com.cibertec.app.service.DashboardService;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class DashboardServiceImpl implements DashboardService{
	
	private final MedicoRepository medicoRepo;
    private final UsuarioRepository usuarioRepo;
    private final ComprobanteDePagoRepository pagoRepo;
    private final LogCitaRepository logRepo;
    private final CitaRepository citaRepo;
    private final SlotHorarioRepository slotRepo;
    private final PacienteRepository pacienteRepo;

    private final ComprobantePagoMapper pagoMapper;
    private final CitaOutputMapper citaMapper;
    
	@Override
	public DashboardAdminDTO getAdminStats() {
		
		Long totalMed = medicoRepo.count();
        Long userActivos = usuarioRepo.countByEstadoActivo();
		
        BigDecimal ingresos = pagoRepo.sumMontoMesActual(); // Requiere método en Repo
        if (ingresos == null) ingresos = BigDecimal.ZERO;
		
		List<LogCitaResponseDTO> logs = logRepo.findTop5ByOrderByFechaAccionDesc()
		        .stream()
		        .map(log -> new LogCitaResponseDTO(
		            log.getIdLog(),
		            log.getFechaAccion(),
		            log.getAccion(),
		            log.getDetalle(),
		            log.getCita().getIdCita(), // Suponiendo que la entidad Log tiene la relación Cita
		            log.getUsuarioActor() != null ? 
		                log.getUsuarioActor().getNombres() + " " + log.getUsuarioActor().getApellidos() : "Sistema",
		            log.getUsuarioActor() != null ? log.getUsuarioActor().getRol().name() : "SYSTEM"
		        ))
		        .collect(Collectors.toList());

		    return new DashboardAdminDTO(totalMed, userActivos, ingresos, logs);
		}

	@Override
	public DashboardRecepcionDTO getRecepcionStats() {
		Long citasHoy = citaRepo.countCitasOperativasHoy();
	    Long cupos = slotRepo.countSlotsDisponiblesRealesHoy();
	    Long pacientes = pacienteRepo.count(); // Cantidad total de pacientes en base de datos
	    List<Map<String, Object>> proximas = citaRepo.findProximasCitasDelDia();

	    return new DashboardRecepcionDTO(citasHoy, cupos, pacientes, proximas);
	}

	@Override
	public CajeroDashboardDTO obtenerResumenCaja() {
        Double total = pagoRepo.sumTotalHoy();
        Double efectivo = pagoRepo.sumPorMetodoHoy(MetodoPago.EFECTIVO);
        Double tarjeta = pagoRepo.sumPorMetodoHoy(MetodoPago.TARJETA);
        Double transferencia = pagoRepo.sumPorMetodoHoy(MetodoPago.TRANSFERENCIA);

        // 2. Gestión de citas
        Long pendientes = citaRepo.countPendientesCobroHoy();

        // 3. Mapeo de pagos recientes
        List<ComprobanteRecienteDTO> recientes = pagoRepo.findTop5ByOrderByFechaEmisionDesc()
                .stream()
                .map(pagoMapper::toComprobanteRecienteDTO)
                .collect(Collectors.toList());

        return new CajeroDashboardDTO(
            total, 
            efectivo, 
            tarjeta, 
            transferencia, 
            pendientes, 
            recientes
        );
	}

	@Override
	public MedicoDashboardDTO obtenerDashboardMedico(String username) {
		
        Long idMed = medicoRepo.findIdMedicoByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("Médico no encontrado"));
        
        
        List<Cita> citasEntity = citaRepo.findAgendaHoyByMedico(idMed);
        List<CitaAgendaMedicoDTO> agenda = citasEntity.stream()
                .map(citaMapper::toAgendaMedicoDTO)
                .collect(Collectors.toList());

        Long total = citaRepo.countTotalHoy(idMed);
        Long atendidas = citaRepo.countAtendidosHoy(idMed);
        Long pendientes = total - atendidas;

        CitaAgendaMedicoDTO siguiente = agenda.stream()
                .filter(c -> c.getEstado().name().equals("CONFIRMADO"))
                .findFirst()
                .orElse(null);

        return new MedicoDashboardDTO(total, atendidas, pendientes, siguiente, agenda);
	}
	

	
}
