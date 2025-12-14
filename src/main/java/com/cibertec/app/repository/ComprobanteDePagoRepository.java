package com.cibertec.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cibertec.app.entity.ComprobanteDePago;

public interface ComprobanteDePagoRepository extends JpaRepository<ComprobanteDePago, Long>{
	
	List<ComprobanteDePago> findByPagador_Dni(String dni);
	
	List<ComprobanteDePago> findByPagador_Nombres(String nombres);
}
