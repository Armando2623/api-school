package com.ortiz.Proyecto.controller;

import com.ortiz.Proyecto.models.DatosRegistroUsuario;
import com.ortiz.Proyecto.models.Usuario;
import com.ortiz.Proyecto.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Endpoints de gestión de usuarios del sistema.
 * Todos requieren rol ADMINISTRADOR (configurado en SecurityConfig).
 *
 * GET /api/usuarios → lista todos
 * POST /api/usuarios → crea uno nuevo
 * PUT /api/usuarios/{id} → actualiza nombre, usuario, rol y (opcional)
 * contraseña
 * DELETE /api/usuarios/{id} → elimina por ID
 */
@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<List<Usuario>> listar() {
        return ResponseEntity.ok(usuarioService.listarTodos());
    }

    @PostMapping
    public ResponseEntity<?> registrar(@RequestBody DatosRegistroUsuario datos) {
        try {
            Usuario creado = usuarioService.registrar(datos);
            return ResponseEntity.ok(Map.of(
                    "id", creado.getId(),
                    "nombre", creado.getNombre(),
                    "usuario", creado.getUsuario(),
                    "rol", creado.getRol().name()));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody DatosRegistroUsuario datos) {
        try {
            Usuario u = usuarioService.actualizar(id, datos);
            return ResponseEntity.ok(Map.of(
                    "id", u.getId(),
                    "nombre", u.getNombre(),
                    "usuario", u.getUsuario(),
                    "rol", u.getRol().name()));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        usuarioService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
