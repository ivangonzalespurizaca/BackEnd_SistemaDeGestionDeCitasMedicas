package com.cibertec.app.service;

import com.cibertec.app.dto.CajeroDashboardDTO;
import com.cibertec.app.dto.DashboardAdminDTO;
import com.cibertec.app.dto.DashboardRecepcionDTO;
import com.cibertec.app.dto.MedicoDashboardDTO;

public interface DashboardService {
	
	public DashboardAdminDTO getAdminStats();
	
	public DashboardRecepcionDTO getRecepcionStats();
	
	public CajeroDashboardDTO obtenerResumenCaja();
	
	public MedicoDashboardDTO obtenerDashboardMedico(String username);
	
}
