package com.cibertec.app.entity;

import java.time.LocalTime;
import com.cibertec.app.enums.DiaSemana;
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
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "Horarios_Atencion", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"ID_MEDICO", "Dia_Semana", "Horario_Entrada", "Horario_Salida"})
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HorarioDeAtencion {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_Horario")
    private Long idHorario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_MEDICO", nullable = false)
    @ToString.Exclude
    private Medico medico;

    @Enumerated(EnumType.STRING)
    @Column(name = "Dia_Semana", nullable = false)
    private DiaSemana diaSemana;

    @Column(name = "Horario_Entrada", nullable = false)
    private LocalTime horarioEntrada;

    @Column(name = "Horario_Salida", nullable = false)
    private LocalTime horarioSalida;
}
