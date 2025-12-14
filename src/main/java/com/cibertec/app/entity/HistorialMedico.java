package com.cibertec.app.entity;

import java.math.BigDecimal;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "Historial_Medico")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HistorialMedico {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_Historial")
    private Long idHistorial;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_Cita", nullable = false, unique = true)
    @ToString.Exclude
    private Cita cita;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_Usuario", nullable = false)
    private Usuario medicoResponsable; 

    @Column(columnDefinition = "TEXT")
    private String diagnostico;

    @Column(columnDefinition = "TEXT")
    private String tratamiento;

    @Column(name = "Notas_Adicionales", columnDefinition = "TEXT")
    private String notasAdicionales;

    @Column(nullable = true, precision = 5, scale = 2)
    private BigDecimal peso;

    @Column(name = "Presion_Arterial", length = 10)
    private String presionArterial;
}
