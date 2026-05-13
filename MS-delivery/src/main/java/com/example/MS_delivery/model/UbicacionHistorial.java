package com.example.MS_delivery.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "ubicacion_historial")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UbicacionHistorial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "repartidor_id", nullable = false)
    private Repartidor repartidor;

    // Puede ser null si el repartidor estaba libre al mandar su ubicación
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    @NotNull
    @Column(nullable = false, precision = 10, scale = 7)
    private Double latitud;

    @NotNull
    @Column(nullable = false, precision = 10, scale = 7)
    private Double longitud;

    @Column(name = "velocidad_kmh", precision = 5, scale = 2)
    private Double velocidadKmh;

    @Column(name = "precision_m", precision = 6, scale = 2)
    private Double precisionM;

    @Column(nullable = false)
    @Builder.Default
    private LocalDateTime timestamp = LocalDateTime.now();
}
