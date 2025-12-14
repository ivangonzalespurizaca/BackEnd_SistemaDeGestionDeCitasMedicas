package com.cibertec.app.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import com.cibertec.app.enums.EstadoComprobante;
import com.cibertec.app.enums.MetodoPago;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "Comprobante_Pago")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ComprobanteDePago {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_Comprobante")
    private Long idComprobante;

    @ManyToOne(fetch = FetchType.LAZY) 
    @JoinColumn(name = "ID_Cita", nullable = false)
    @ToString.Exclude
    private Cita cita;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_Usuario", nullable = false)
    @ToString.Exclude
    private Usuario usuarioEmisor;
	
	@Embedded
    private DatosPagador pagador;
	
	@Column(name = "Fecha_Emision", nullable = false)
    private LocalDateTime fechaEmision;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal monto;

    @Enumerated(EnumType.STRING)
    @Column(name = "Metodo_Pago", nullable = false)
    private MetodoPago metodoPago;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    private EstadoComprobante estado = EstadoComprobante.EMITIDO;

    @PrePersist
    public void prePersist() {
        if (this.fechaEmision == null) {
            this.fechaEmision = LocalDateTime.now();
        }
    }
}
