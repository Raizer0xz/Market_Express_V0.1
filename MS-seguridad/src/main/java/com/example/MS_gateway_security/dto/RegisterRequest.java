package com.example.MS_gateway_security.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterRequest {
    @NotNull(message = "El usuario_id es obligatorio")
    private Long usuarioId;

    @NotBlank @Email
    private String email;

    @NotBlank
    @Size(min = 8, message = "Minimo 8 caracteres")
    private String password;

    @NotBlank
    private String rol; // CLIENTE, REPARTIDOR, ADMIN_SUCURSAL
}
