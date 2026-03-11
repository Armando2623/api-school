package com.ortiz.Proyecto.controller;

import com.ortiz.Proyecto.models.Alumno;
import com.ortiz.Proyecto.models.DatosRegistroAlumno;
import com.ortiz.Proyecto.service.AlumnoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/alumnos")
public class AlumnoController {

    @Autowired
    private AlumnoService alumnoService;

    /** GET /api/alumnos — Lista todos los alumnos */
    @GetMapping
    public ResponseEntity<List<Alumno>> listar() {
        return ResponseEntity.ok(alumnoService.listar());
    }

    /** GET /api/alumnos/{id} — Obtiene un alumno por ID */
    @GetMapping("/{id}")
    public ResponseEntity<Alumno> obtener(@PathVariable Long id) {
        return alumnoService.obtener(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /** POST /api/alumnos — Registra un nuevo alumno con apoderado opcional */
    @PostMapping
    public ResponseEntity<Alumno> registrar(@RequestBody @Valid DatosRegistroAlumno datos) {
        return ResponseEntity.ok(alumnoService.registrar(datos));
    }

    /** GET /api/alumnos/apoderado/{visitanteId} — Alumnos de un apoderado */
    @GetMapping("/apoderado/{visitanteId}")
    public ResponseEntity<List<Alumno>> porApoderado(@PathVariable Long visitanteId) {
        return ResponseEntity.ok(alumnoService.listarPorApoderado(visitanteId));
    }

    /** PUT /api/alumnos/{id} — Actualiza un alumno existente */
    @PutMapping("/{id}")
    public ResponseEntity<Alumno> actualizar(@PathVariable Long id,
            @RequestBody @Valid DatosRegistroAlumno datos) {
        return ResponseEntity.ok(alumnoService.actualizar(id, datos));
    }
}
