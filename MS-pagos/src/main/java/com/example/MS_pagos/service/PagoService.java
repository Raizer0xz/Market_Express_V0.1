package com.example.MS_pagos.service;

import com.example.MS_pagos.modelo.EstadoPago;
import com.example.MS_pagos.modelo.MetodoPago;
import com.example.MS_pagos.modelo.Pago;
import com.example.MS_pagos.modelo.PagoRequest;
import com.example.MS_pagos.repository.PagoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class PagoService {

    private final PagoRepository pagoRepository;

    @Transactional
    public Pago procesarPago(PagoRequest request) {
        Pago pago = new Pago();
        pago.setPedidoId(request.getPedidoId());
        pago.setMonto(request.getMonto());
        pago.setMoneda(request.getMoneda() != null ? request.getMoneda() : "CLP");
        pago.setMetodo(MetodoPago.valueOf(request.getMetodo().toUpperCase()));
        pago.setEstado(EstadoPago.PROCESANDO);
        pago.setTransaccionId(UUID.randomUUID().toString());
        log.info("Procesando pago para pedido {} por ${}", request.getPedidoId(), request.getMonto());
        return pagoRepository.save(pago);
    }

    @Transactional(readOnly = true)
    public List<MetodoPago> obtenerMetodosDisponibles() {
        return Arrays.asList(MetodoPago.values());
    }

    @Transactional(readOnly = true)
    public List<Pago> obtenerPorPedido(Long pedidoId) {
        return pagoRepository.findByPedidoId(pedidoId);
    }

    @Transactional
    public Pago confirmarTransaccion(String transaccionId, String status) {
        Pago pago = pagoRepository.findByTransaccionId(transaccionId)
                .orElseThrow(() -> new RuntimeException("Pago no encontrado: " + transaccionId));

        pago.setEstado("SUCCESS".equalsIgnoreCase(status)
                ? EstadoPago.COMPLETADO
                : EstadoPago.RECHAZADO);

        log.info("Pago {} → {}", transaccionId, pago.getEstado());
        return pagoRepository.save(pago);
    }
}