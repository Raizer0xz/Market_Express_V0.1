package com.example.MS_gateway_security.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class ValidarTokenResponse {
    private boolean valido;
    private Long usuarioId;
    private String email;
    private String rol;
    private String mensaje;
}
