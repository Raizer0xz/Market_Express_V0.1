package com.sistema.pagos.service;

import com.sistema.pagos.model.Pago;
import com.sistema.pagos.model.EstadoPago;
import com.sistema.pagos.model.MetodoPago;
import com.sistema.pagos.dto.PagoRequest;
import com.sistema.pagos.repository.PagoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
public class PagoService {

    @Autowired
    private PagoRepository pagoRepository;

    public Pago procesarPago(PagoRequest request) {
        Pago pago = new Pago();
        pago.setMonto(request.getMonto());
        pago.setMoneda(request.getMoneda());
        pago.setMetodo(MetodoPago.valueOf(request.getMetodo()));
        pago.setEstado(EstadoPago.PROCESANDO);

        // Simulación de generación de ID externo de pasarela
        pago.setTransaccionId(UUID.randomUUID().toString());

        return pagoRepository.save(pago);
    }

    public List<MetodoPago> obtenerMetodosDisponibles() {
        return Arrays.asList(MetodoPago.values());
    }

    public Pago confirmarTransaccion(String transaccionId, String status) {
        Pago pago = pagoRepository.findByTransaccionId(transaccionId)
                .orElseThrow(() -> new RuntimeException("Pago no encontrado con ID: " + transaccionId));

        if ("SUCCESS".equalsIgnoreCase(status)) {
            pago.setEstado(EstadoPago.COMPLETADO);
        } else {
            pago.setEstado(EstadoPago.RECHAZADO);
        }

        return pagoRepository.save(pago);
    }
}