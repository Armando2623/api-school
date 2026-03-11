package com.ortiz.Proyecto.controller;

import com.ortiz.Proyecto.models.DatosRegistroVisita;
import com.ortiz.Proyecto.models.RegistroVisita;
import com.ortiz.Proyecto.models.Usuario;
import com.ortiz.Proyecto.service.RegistroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/visitas")
public class RegistroController {

    @Autowired
    private RegistroService registroService;

    /** POST /api/visitas — Registra una nueva visita */
    @PostMapping
    public ResponseEntity<RegistroVisita> registrarVisita(@RequestBody DatosRegistroVisita datosRegistro) {
        return ResponseEntity.ok(registroService.registrar(datosRegistro));
    }

    /** GET /api/visitas — Lista todas las visitas con usuario */
    @GetMapping
    public ResponseEntity<List<RegistroVisita>> listarVisitas() {
        return ResponseEntity.ok(registroService.listar());
    }

    /** GET /api/visitas/usuarios?search= — Autocompletado de "Persona a Visitar" */
    @GetMapping("/usuarios")
    public ResponseEntity<List<Usuario>> buscarUsuarios(@RequestParam String search) {
        return ResponseEntity.ok(registroService.buscarUsuarios(search));
    }

    /**
     * GET /api/visitas/visitante?dni= — Busca el último registro con ese DNI
     * (usado como fallback antes de crear la entidad Visitante)
     */
    @GetMapping("/visitante")
    public ResponseEntity<?> buscarVisitantePorDni(@RequestParam String dni) {
        return registroService.buscarPorDni(dni)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /** PUT /api/visitas/{id} — Actualiza una visita existente */
    @PutMapping("/{id}")
    public ResponseEntity<RegistroVisita> actualizar(@PathVariable Long id,
            @RequestBody DatosRegistroVisita datos) {
        return ResponseEntity.ok(registroService.actualizar(id, datos));
    }
}
