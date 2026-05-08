package com.example.MS_gateway_security.controller;

import com.example.MS_gateway_security.dto.*;
import com.example.MS_gateway_security.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * AuthController — Endpoints del ms-seguridad (puerto 9099)
 *
 * POST /auth/registrar  → crea credenciales para un usuario nuevo
 * POST /auth/login      → autentica y devuelve JWT
 * POST /auth/validar    → valida token (lo usan los demas microservicios)
 * GET  /auth/health     → verifica que el servicio esta activo
 */
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/registrar")
    public ResponseEntity<?> registrar(@Valid @RequestBody RegisterRequest request) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(authService.registrar(request));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
        try {
            return ResponseEntity.ok(authService.login(request));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", e.getMessage()));
        }
    }

    // Los otros microservicios mandan: Authorization: Bearer <token>
    @PostMapping("/validar")
    public ResponseEntity<ValidarTokenResponse> validarToken(
            @RequestHeader("Authorization") String authHeader) {

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.badRequest().body(
                    ValidarTokenResponse.builder().valido(false)
                            .mensaje("Header invalido. Formato esperado: Bearer <token>").build());
        }

        String token = authHeader.substring(7);
        ValidarTokenResponse response = authService.validarToken(token);
        return response.isValido() ? ResponseEntity.ok(response)
                : ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> health() {
        return ResponseEntity.ok(Map.of("servicio", "ms-seguridad", "estado", "activo", "puerto", "9099"));
    }
}
