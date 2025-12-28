package com.cibertec.app.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cibertec.app.dto.CajeroDashboardDTO;
import com.cibertec.app.dto.DashboardAdminDTO;
import com.cibertec.app.dto.DashboardRecepcionDTO;
import com.cibertec.app.dto.MedicoDashboardDTO;
import com.cibertec.app.service.DashboardService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {
	private final DashboardService dashboardService;

    @GetMapping("/admin")
    public ResponseEntity<DashboardAdminDTO> obtenerResumenAdmin() {
        DashboardAdminDTO stats = dashboardService.getAdminStats();
        return ResponseEntity.ok(stats);
    }
    
    @GetMapping("/recepcion")
    public ResponseEntity<DashboardRecepcionDTO> getRecepcionDashboard() {
        return ResponseEntity.ok(dashboardService.getRecepcionStats());
    }
    
    @GetMapping("/caja")
    public ResponseEntity<CajeroDashboardDTO> getCajeroDashboard() {
        return ResponseEntity.ok(dashboardService.obtenerResumenCaja());
    }
    
    @GetMapping("/medico")
    public ResponseEntity<MedicoDashboardDTO> getMedicoDashboard(Authentication auth) {
        return ResponseEntity.ok(dashboardService.obtenerDashboardMedico(auth.getName()));
    }
}
