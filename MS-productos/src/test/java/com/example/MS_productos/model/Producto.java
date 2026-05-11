package com.example.MS_productos.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table (name = "Producto")
@Data
@AllArgsConstructor
@NoArgsConstructor




public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "categoria_id", nullable = false)
    private Categoria categoria;


    @NotBlank
    private String nombre;
    private String descripcion;
    private String imagenUrl;
    private String unidadMedida;
    private Boolean activo = true;

}
