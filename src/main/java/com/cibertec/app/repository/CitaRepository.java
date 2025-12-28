package com.cibertec.app.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cibertec.app.entity.Cita;
import com.cibertec.app.enums.EstadoCita;

public interface CitaRepository extends JpaRepository<Cita, Long>{
	
    @Query("SELECT COUNT(c) > 0 FROM Cita c WHERE " +
	           "c.medico.idMedico = :idMedico AND " +
	           "c.hora = :hora AND " +
	           "c.fecha >= :fechaActual AND " +
	           "c.estado IN :estados AND " +
	           "FUNCTION('DAYOFWEEK', c.fecha) = :indiceDia")
	    boolean existeCitaEnDiaYHora(
	        @Param("idMedico") Long idMedico, 
	        @Param("hora") LocalTime hora, 
	        @Param("fechaActual") LocalDate fechaActual, 
	        @Param("estados") List<EstadoCita> estados, 
	        @Param("indiceDia") int indiceDia // MySQL: 1=Dom, 2=Lun, ... 7=Sáb
	    );
	
    List<Cita> findAllByOrderByFechaDescHoraDesc();
    
	@Query("SELECT c FROM Cita c " +
	           "JOIN FETCH c.paciente p " +
	           "JOIN FETCH c.medico m " +
	           "JOIN FETCH m.usuario mu " +
	           "JOIN FETCH m.especialidad e " +
	           "WHERE :filtro IS NULL OR :filtro = '' " +
	           "OR p.dni LIKE CONCAT('%', :filtro, '%') " +
	           "OR LOWER(p.nombres) LIKE LOWER(CONCAT('%', :filtro, '%')) " +
	           "OR LOWER(p.apellidos) LIKE LOWER(CONCAT('%', :filtro, '%')) " +
	           "ORDER BY c.fecha DESC, c.hora DESC")
	    List<Cita> buscarPorCriterioGlobal(@Param("filtro") String filtro);
	
	List<Cita> findByEstado(EstadoCita estado);
	
	@Query("SELECT c FROM Cita c " +
		       "JOIN FETCH c.paciente p " +
		       "JOIN FETCH c.medico m " +
		       "WHERE c.estado = :estado " +
		       "AND (:filtro IS NULL OR :filtro = '' " +
		       "OR p.dni LIKE CONCAT('%', :filtro, '%') " +
		       "OR LOWER(p.nombres) LIKE LOWER(CONCAT('%', :filtro, '%')) " +
		       "OR LOWER(p.apellidos) LIKE LOWER(CONCAT('%', :filtro, '%')))")
		List<Cita> findPendientesByPaciente(@Param("filtro") String filtro, @Param("estado") EstadoCita estado);
	

	@Query("SELECT COUNT(c) FROM Cita c WHERE c.fecha = CURRENT_DATE " +
	       "AND c.estado IN (com.cibertec.app.enums.EstadoCita.PENDIENTE, " +
	       "com.cibertec.app.enums.EstadoCita.CONFIRMADO, " +
	       "com.cibertec.app.enums.EstadoCita.ATENDIDO)")
	Long countCitasOperativasHoy();

	@Query("SELECT c.paciente.nombres as paciente, c.paciente.apellidos as apellidos, " +
		       "c.hora as hora, c.medico.usuario.nombres as medico, c.estado as estado " +
		       "FROM Cita c WHERE c.fecha = CURRENT_DATE " +
		       "AND c.estado IN (com.cibertec.app.enums.EstadoCita.PENDIENTE, com.cibertec.app.enums.EstadoCita.CONFIRMADO) " +
		       "ORDER BY c.hora ASC")
		List<Map<String, Object>> findProximasCitasDelDia();
	
	@Query("SELECT COUNT(c) FROM Cita c WHERE c.fecha = CURRENT_DATE AND c.estado = 'PENDIENTE'")
    	Long countPendientesCobroHoy();
	
	// 1. Agenda completa del médico para hoy
    @Query("SELECT c FROM Cita c WHERE c.medico.idMedico = :idMedico AND c.fecha = CURRENT_DATE ORDER BY c.hora ASC")
    List<Cita> findAgendaHoyByMedico(@Param("idMedico") Long idMedico);
    
    @Query("SELECT COUNT(c) FROM Cita c WHERE c.medico.idMedico = :idMedico AND c.fecha = CURRENT_DATE")
    Long countTotalHoy(@Param("idMedico") Long idMedico);
	
    @Query("SELECT COUNT(c) FROM Cita c WHERE c.medico.idMedico = :idMedico AND c.fecha = CURRENT_DATE AND c.estado = 'ATENDIDO'")
    Long countAtendidosHoy(@Param("idMedico") Long idMedico);
}
