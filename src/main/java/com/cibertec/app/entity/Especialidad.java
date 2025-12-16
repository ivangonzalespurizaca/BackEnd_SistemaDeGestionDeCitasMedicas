package com.cibertec.app.entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Especialidad")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Especialidad {
	@Id
	@Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEspecialidad;

    @Column(name = "Nombre_Especialidad", nullable = false, unique = true, length = 100)
    private String nombreEspecialidad;
    
    @OneToMany(mappedBy = "especialidad", fetch = FetchType.LAZY)
    private List<Medico> medicos;
}
