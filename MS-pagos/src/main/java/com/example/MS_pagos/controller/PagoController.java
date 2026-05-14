package com.example.MS_pagos.controller;

import com.example.MS_pagos.modelo.MetodoPago;
import com.example.MS_pagos.modelo.Pago;
import com.example.MS_pagos.modelo.PagoRequest;
import com.example.MS_pagos.service.PagoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/pagos")
@RequiredArgsConstructor
public class PagoController {

    private final PagoService pagoService;

    // POST /api/v1/pagos/procesar — solo CLIENTE o ADMIN
    @PostMapping("/procesar")
    public ResponseEntity<?> crearPago(@RequestBody PagoRequest request,
                                       @RequestHeader(value = "X-Usuario-Rol", defaultValue = "") String rol) {
        if (!rol.equals("CLIENTE") && !rol.equals("ADMIN")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("error", "Solo clientes pueden crear pagos"));
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(pagoService.procesarPago(request));
    }

    // GET /api/v1/pagos/metodos — público dentro de la red
    @GetMapping("/metodos")
    public ResponseEntity<List<MetodoPago>> getMetodos() {
        return ResponseEntity.ok(pagoService.obtenerMetodosDisponibles());
    }

    // GET /api/v1/pagos/pedido/{pedidoId}
    @GetMapping("/pedido/{pedidoId}")
    public ResponseEntity<List<Pago>> porPedido(@PathVariable Long pedidoId) {
        return ResponseEntity.ok(pagoService.obtenerPorPedido(pedidoId));
    }

    // POST /api/v1/pagos/confirmar — solo ADMIN
    @PostMapping("/confirmar")
    public ResponseEntity<?> confirmar(@RequestParam String transaccionId,
                                       @RequestParam String status,
                                       @RequestHeader(value = "X-Usuario-Rol", defaultValue = "") String rol) {
        if (!rol.equals("ADMIN")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("error", "Solo administradores pueden confirmar pagos"));
        }
        try {
            return ResponseEntity.ok(pagoService.confirmarTransaccion(transaccionId, status));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        }
    }
}