package com.cibertec.app.entity;

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

@Entity
@Table(name = "Medico")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Medico {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_Medico")
    private Long idMedico;

	@Column(name = "Nro_Colegiatura", nullable = false, unique = true, length = 20)
    private String nroColegiatura;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Especialidad_ID", nullable = false)
    private Especialidad especialidad;
    
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Id_Usuario", nullable = false, unique = true)
    private Usuario usuario;
}
