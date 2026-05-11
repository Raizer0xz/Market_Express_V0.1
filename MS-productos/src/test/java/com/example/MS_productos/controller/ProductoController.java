package com.example.MS_productos.controller;

import com.example.MS_productos.model.Producto;
import com.example.MS_productos.service.ProductoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v2/productos")
public class ProductoController {

    @Autowired
    private ProductoService service;

    @GetMapping
    public ResponseEntity<List<Producto>> listarProductos() {
        return ResponseEntity.ok(service.listarProductos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Producto> obtenerProducto(@PathVariable Long id) {
        return ResponseEntity.ok(service.obternerPorId(id));
    }

    @GetMapping("/categoria/{catId}")
    public ResponseEntity<List<Producto>> porCategoria(@PathVariable Long catId) {
        return ResponseEntity.ok(service.listarPorCategoria(catId));
    }

    @PostMapping
    public ResponseEntity<Producto> crearProducto(@RequestBody @Valid Producto producto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.crearProducto(producto));}

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProducto(@PathVariable Long id) {
        service.eliminarProducto(id);
        return ResponseEntity.noContent().build();
    }
}