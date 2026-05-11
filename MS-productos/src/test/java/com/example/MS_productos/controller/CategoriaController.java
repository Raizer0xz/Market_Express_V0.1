package com.example.MS_productos.controller;

import com.example.MS_productos.model.Categoria;
import com.example.MS_productos.service.CategoriaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v2/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaService service;

    // GET http://localhost:8083/api/v2/categorias
    @GetMapping
    public ResponseEntity<List<Categoria>> listarCategorias() {
        return ResponseEntity.ok(service.listarCategorias());
    }

    // GET http://localhost:8083/api/v2/categorias/1
    @GetMapping("/{id}")
    public ResponseEntity<Categoria> obtenerCategoria(@PathVariable Long id) {
        return ResponseEntity.ok(service.obtenerPorId(id));
    }

    // POST http://localhost:8083/api/v2/categorias
    @PostMapping
    public ResponseEntity<Categoria> crearCategoria(@RequestBody @Valid Categoria categoria) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.crearCategoria(categoria));
    }

    // DELETE http://localhost:8083/api/v2/categorias/1
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCategoria(@PathVariable Long id) {
        service.eliminarCategoria(id);
        return ResponseEntity.noContent().build();
    }
}