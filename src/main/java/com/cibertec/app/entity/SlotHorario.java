package com.cibertec.app.entity;

import java.time.LocalDate;
import java.time.LocalTime;
import com.cibertec.app.enums.EstadoSlotHorario;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Table(name = "Slot_Horario")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SlotHorario {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_Slot")
    private Long idSlot;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_Medico", nullable = false)
    @ToString.Exclude
    private Medico medico;

    @Column(nullable = false)
    private LocalDate fecha;

    @Column(name = "Hora_Inicio", nullable = false)
    private LocalTime hora;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "Estado", nullable = false)
    private EstadoSlotHorario estadoSlot = EstadoSlotHorario.DISPONIBLE;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_Cita")
    private Cita cita;
}
