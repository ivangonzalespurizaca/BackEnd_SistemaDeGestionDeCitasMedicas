package com.cibertec.app.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DatosPagador {
	@Column(name = "Nombre_Pagador", nullable = false, length = 100)
    private String nombres;

    @Column(name = "Apellidos_Pagador", nullable = false, length = 100)
    private String apellidos;

    @Column(name = "DNI_Pagador", length = 8)
    private String dni;

    @Column(name = "Contacto_Pagador", length = 15)
    private String contacto;
}
