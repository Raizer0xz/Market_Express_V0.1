package com.example.MS_pagos.modelo;
import lombok.Data;

@Data
public class PagoRequest {
    private Double monto;
    private String moneda;
    private String metodo; // Se puede mapear a Enum en el servicio