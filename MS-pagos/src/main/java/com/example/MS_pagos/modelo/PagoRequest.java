package com.example.MS_pagos.modelo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PagoRequest {
    private Long pedidoId;
    private Double monto;
    private String moneda;
    private String metodo; // Se puede mapear a Enum en el servicio
}