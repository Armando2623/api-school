package com.ortiz.Proyecto.controller;

import com.ortiz.Proyecto.models.DatosRegistroVisitante;
import com.ortiz.Proyecto.models.Visitante;
import com.ortiz.Proyecto.service.VisitanteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/visitantes")
public class VisitanteController {

    @Autowired
    private VisitanteService visitanteService;

    /** POST /api/visitantes — Registra o recupera un visitante por DNI */
    @PostMapping
    public ResponseEntity<Visitante> registrar(@RequestBody @Valid DatosRegistroVisitante datos) {
        return ResponseEntity.ok(visitanteService.registrarOBuscar(datos));
    }

    /**
     * GET /api/visitantes/buscar?dni= — Autocompletado por DNI con lista de hijos
     */
    @GetMapping("/buscar")
    public ResponseEntity<?> buscarPorDni(@RequestParam String dni) {
        return visitanteService.buscarPorDni(dni)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /** GET /api/visitantes — Lista todos los visitantes */
    @GetMapping
    public ResponseEntity<List<Visitante>> listar() {
        return ResponseEntity.ok(visitanteService.listar());
    }

    /** PUT /api/visitantes/{id} — Actualiza un visitante existente */
    @PutMapping("/{id}")
    public ResponseEntity<Visitante> actualizar(@PathVariable Long id,
            @RequestBody @Valid DatosRegistroVisitante datos) {
        return ResponseEntity.ok(visitanteService.actualizar(id, datos));
    }
}
