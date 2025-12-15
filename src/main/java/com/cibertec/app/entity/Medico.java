package com.cibertec.app.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Medico")
@PrimaryKeyJoinColumn(name = "ID_Medico") 
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class Medico extends Usuario{

	private static final long serialVersionUID = 1L;

	@Column(name = "Nro_Colegiatura", nullable = false, unique = true, length = 20)
    private String nroColegiatura;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Especialidad_ID", nullable = false)
    private Especialidad especialidad;
}
