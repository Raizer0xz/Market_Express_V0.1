package com.example.MS_pedidos.controller;

import com.example.MS_pedidos.model.EstadoPedido;
import com.example.MS_pedidos.model.Pedido;
import com.example.MS_pedidos.service.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/pedidos")
@RequiredArgsConstructor
public class PedidoController {
    @Autowired
    private PedidoService service;

    @GetMapping
    public ResponseEntity<List<Pedido>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Pedido>> findByUsuario(@PathVariable Long usuarioId) {
        List<Pedido> pedidos = service.findByUsuario(usuarioId);
        if (pedidos.isEmpty()) {
            return ResponseEntity.noContent().build(); // 204 — encontró pero está vacío
        }
        return ResponseEntity.ok(pedidos); // 200 — con lista
    }

    @GetMapping("/sucursal/{sucursalId}")
    public ResponseEntity<List<Pedido>> findBySucursalIdAndEstado(@PathVariable Long sucursalId, @RequestParam EstadoPedido estado) {
        List<Pedido> pedidos = service.findBySucursalIdAndEstado(sucursalId, estado);
        if (pedidos.isEmpty()) {
            return ResponseEntity.noContent().build();// 204 — encontró pero está vacío
        }
        return ResponseEntity.ok(pedidos);// 200 — con lista
    }

    @PostMapping
    public ResponseEntity<Pedido> save(@RequestBody Pedido pedido) {
        Pedido nuevo = service.save(pedido);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
    }

    @PutMapping("/{id}/estado")
    public ResponseEntity<Pedido> update(@PathVariable Long id,@RequestParam EstadoPedido nuevoEstado) {
        return service.update(id,nuevoEstado)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    public void deleteById(Long id) {
        service.deleteById(id);
    }
}
