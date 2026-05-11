package com.example.MS_productos.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "Precio_Producto")
@NoArgsConstructor
@AllArgsConstructor
public class PrecioProducto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne
    @JoinColumn(name = "producto_id", nullable = false)
    private Producto producto;

    @Column(name = "sucursal_id", nullable = false)
    private Long sucursalId;

    @Positive
    @Column(nullable = false)
    private BigDecimal precio;

    @Column(name = "vigente_desde", nullable = false)
    private LocalDate vigenteDesde;
}
