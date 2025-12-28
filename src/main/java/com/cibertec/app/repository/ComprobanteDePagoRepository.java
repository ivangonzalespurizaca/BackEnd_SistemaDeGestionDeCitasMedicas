package com.cibertec.app.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cibertec.app.entity.ComprobanteDePago;
import com.cibertec.app.enums.MetodoPago;

public interface ComprobanteDePagoRepository extends JpaRepository<ComprobanteDePago, Long>{
	
	@Query("SELECT c FROM ComprobanteDePago c " +
	           "JOIN FETCH c.cita ci " +
	           "JOIN FETCH ci.paciente p " +
	           "WHERE (:criterio IS NULL OR :criterio = '' " +
	           "OR c.pagador.dniPagador LIKE CONCAT('%', :criterio, '%')" +
	           "OR LOWER(c.pagador.nombresPagador) LIKE LOWER(CONCAT('%', :criterio, '%')) " +
	           "OR LOWER(c.pagador.apellidosPagador) LIKE LOWER(CONCAT('%', :criterio, '%')))"+
				"ORDER BY c.fechaEmision DESC")
	    List<ComprobanteDePago> buscarPorCriterio(@Param("criterio") String criterio);
	
	List<ComprobanteDePago> findAllByOrderByFechaEmisionDesc();
	
	@Query("SELECT SUM(c.monto) FROM ComprobanteDePago c " +
		       "WHERE MONTH(c.fechaEmision) = MONTH(CURRENT_DATE) " +
		       "AND YEAR(c.fechaEmision) = YEAR(CURRENT_DATE) " +
		       "AND c.estado = 'EMITIDO'")
		BigDecimal sumMontoMesActual();
	
	@Query("SELECT COALESCE(SUM(c.monto), 0) FROM ComprobanteDePago c " +
		       "WHERE CAST(c.fechaEmision AS date) = CURRENT_DATE " +
		       "AND c.estado = com.cibertec.app.enums.EstadoComprobante.EMITIDO")
    	Double sumTotalHoy();

    @Query("SELECT COALESCE(SUM(c.monto), 0) FROM ComprobanteDePago c " +
    	       "WHERE CAST(c.fechaEmision AS date) = CURRENT_DATE " +
    	       "AND c.metodoPago = :metodo " +
    	       "AND c.estado = com.cibertec.app.enums.EstadoComprobante.EMITIDO")
    	Double sumPorMetodoHoy(@Param("metodo") MetodoPago metodo);

    List<ComprobanteDePago> findTop5ByOrderByFechaEmisionDesc();
}
