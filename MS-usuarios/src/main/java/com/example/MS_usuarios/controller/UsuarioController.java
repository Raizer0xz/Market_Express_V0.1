package com.example.MS_usuarios.controller;

import com.example.MS_usuarios.model.Usuario;
import com.example.MS_usuarios.service.ServiceUsuario;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * ControllerUsuario — Endpoints de MS-usuarios (puerto 9090)
 *
 * POST   /usuarios            → crear usuario
 * GET    /usuarios            → listar todos
 * GET    /usuarios/{id}       → buscar por ID  (lo usa MS-seguridad al registrar)
 * GET    /usuarios/email/{email} → buscar por email
 * GET    /usuarios/rol/{rol}  → listar por rol
 * PUT    /usuarios/{id}       → actualizar usuario
 * DELETE /usuarios/{id}       → eliminar usuario
 * GET    /usuarios/health     → verifica que el servicio está activo
 */
@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final ServiceUsuario serviceUsuario;

    // ─── POST /usuarios ───────────────────────────────────────────────────────
    // MS-seguridad llama esto al registrar: primero crea el usuario aquí,
    // luego guarda las credenciales en su propia BD.
    @PostMapping
    public ResponseEntity<?> crearUsuario(@Valid @RequestBody Usuario usuario) {
        try {
            if (serviceUsuario.existsByEmail(usuario.getEmail()))
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body(Map.of("error", "Ya existe un usuario con ese email"));

            Usuario guardado = serviceUsuario.save(usuario);
            return ResponseEntity.status(HttpStatus.CREATED).body(guardado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    // ─── GET /usuarios ────────────────────────────────────────────────────────
    @GetMapping
    public ResponseEntity<List<Usuario>> listarTodos() {
        return ResponseEntity.ok(serviceUsuario.findAll());
    }

    // ─── GET /usuarios/{id} ───────────────────────────────────────────────────
    // Endpoint clave: MS-seguridad verifica que el usuarioId exista antes de
    // crear credenciales.
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        return serviceUsuario.findById(id)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("error", "Usuario no encontrado con id: " + id)));
    }

    // ─── GET /usuarios/email/{email} ──────────────────────────────────────────
    @GetMapping("/email/{email}")
    public ResponseEntity<?> buscarPorEmail(@PathVariable String email) {
        return serviceUsuario.findByEmail(email)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("error", "Usuario no encontrado con email: " + email)));
    }

    // ─── GET /usuarios/rol/{rol} ──────────────────────────────────────────────
    @GetMapping("/rol/{rol}")
    public ResponseEntity<List<Usuario>> buscarPorRol(@PathVariable String rol) {
        return ResponseEntity.ok(serviceUsuario.findByRol(rol));
    }

    // ─── PUT /usuarios/{id} ───────────────────────────────────────────────────
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarUsuario(@PathVariable Long id,
                                               @Valid @RequestBody Usuario datosNuevos) {
        return serviceUsuario.findById(id)
                .<ResponseEntity<?>>map(existente -> {
                    existente.setNombre(datosNuevos.getNombre());
                    existente.setEmail(datosNuevos.getEmail());
                    existente.setTelefono(datosNuevos.getTelefono());
                    existente.setRol(datosNuevos.getRol());
                    // passwordHash NO se actualiza por aquí — eso es trabajo de MS-seguridad
                    return ResponseEntity.ok(serviceUsuario.save(existente));
                })
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("error", "Usuario no encontrado con id: " + id)));
    }

    // ─── DELETE /usuarios/{id} ────────────────────────────────────────────────
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarUsuario(@PathVariable Long id) {
        boolean eliminado = serviceUsuario.deleteById(id);
        if (eliminado)
            return ResponseEntity.ok(Map.of("mensaje", "Usuario eliminado correctamente"));
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("error", "Usuario no encontrado con id: " + id));
    }

    // ─── GET /usuarios/health ─────────────────────────────────────────────────
    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> health() {
        return ResponseEntity.ok(Map.of(
                "servicio", "ms-usuarios",
                "estado", "activo",
                "puerto", "9090"
        ));
    }
}