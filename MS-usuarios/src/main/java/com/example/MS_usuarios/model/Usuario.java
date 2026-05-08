package com.example.MS_usuarios.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Entity
@Table(name = "usuarios")

public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 2, max = 100, message = "Entre 2 y 100 caracteres")
    @Column(nullable = false)//le dice a la BD que esa columna no puede tener el valor NULL.
    private String nombre;  //es equivalente a NOT NULL en SQL

    @NotBlank(message = "El email es Obligatorio")
    @Email(message = "Formateo de Email invalido")
    @Column(nullable = false,unique = true)//Nadie puede registrarse con un email que ya existe en la BD.
    private String email;

    @NotBlank(message = "La contraseña es obligatoria")
    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    @Size(min = 9,max = 15,message = "Telefono entre 9 y 15 caracteres")
    @Column(length = 15)//Define el tamaño máximo del VARCHAR en la BD
    private String telefono;

    @NotBlank(message = "El rol es obligatorio")
    @Column(nullable = false)
    private String rol;

    @CreationTimestamp
    @Column(name = "created_at", updatable = true)//Este campo se puede modificar después de crearse. Si intentas hacer un UPDATE sobre el.
    private LocalDateTime createdAt;
}

