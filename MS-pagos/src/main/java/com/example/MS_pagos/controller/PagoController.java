package com.example.MS_pagos.controller;

import com..pagos.model.Pago;
import com.example.MS_pagos.controller.MetodoPago;
import com.sistema.pagos.dto.PagoRequest;
import com.sistema.pagos.service.PagoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/pagos")
public class PagoController {

    @Autowired
    private PagoService pagoService;

    @PostMapping("/procesar")
    public ResponseEntity<Pago> crearPago(@RequestBody PagoRequest request) {
        return ResponseEntity.ok(pagoService.procesarPago(request));
    }

    @GetMapping("/metodos")
    public ResponseEntity<List<MetodoPago>> getMetodo() {
        return ResponseEntity.ok(pagoService.obtenerMetodosDisponibles());
    }

    @PostMapping("/confirmar")
    public ResponseEntity<Pago> confirmar(
            @RequestParam String transaccionId,
            @RequestParam String status) {
        return ResponseEntity.ok(pagoService.confirmarTransaccion(transaccionId, status));
    }
}