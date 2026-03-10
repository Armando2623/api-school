package com.ortiz.Proyecto.controller;


import com.ortiz.Proyecto.interfaces.IORegistro;
import com.ortiz.Proyecto.interfaces.IOUsuarios;
import com.ortiz.Proyecto.models.DatosRegistroVisita;
import com.ortiz.Proyecto.models.EstadoRegistro;
import com.ortiz.Proyecto.models.RegistroVisita;
import com.ortiz.Proyecto.models.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/visitas")
public class RegistroController {

    @Autowired
    private IORegistro repository;

    @Autowired
    private IOUsuarios usuarioRepo;
    @Autowired
    private IORegistro registroVisitaRepository;

    @PostMapping
    public ResponseEntity<RegistroVisita> registrarVisita(@RequestBody DatosRegistroVisita datosRegistro) {
        RegistroVisita visita = new RegistroVisita(datosRegistro);
        if (visita.getHoraIngreso() == null) {
            visita.setHoraIngreso(LocalDateTime.now());
        }
        if (visita.getEstadoRegistro() == null) {
            visita.setEstadoRegistro(EstadoRegistro.REGISTRADO);
        }

        // Fetch User
        Usuario usuario = usuarioRepo.findById(datosRegistro.usuario_id())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + datosRegistro.usuario_id()));
        visita.setUsuario(usuario);

        RegistroVisita saved = repository.save(visita);
        return ResponseEntity.ok(saved);
    }

    @GetMapping
    public ResponseEntity<List<RegistroVisita>> listarVisitas() {
        List<RegistroVisita> visitas = repository.findAll()
                .stream()
                .filter(v -> v.getUsuario() != null)
                .collect(Collectors.toList());
        return ResponseEntity.ok(visitas);
    }

    @GetMapping("/usuarios")
    public ResponseEntity<List<Usuario>> buscarUsuarios(@RequestParam String search) {
        List<Usuario> usuarios = usuarioRepo.findAll()
                .stream()
                .filter(u -> u.getNombre().toLowerCase().contains(search.toLowerCase()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(usuarios);
    }
    // En tu VisitaController (o donde tengas los endpoints de /api/visitas)

    @GetMapping("/visitante")
    public ResponseEntity<?> buscarVisitantePorDni(@RequestParam String dni) {
        // Busca en la tabla de registros de visitas el visitante más reciente con ese DNI
        Optional<RegistroVisita> visita = registroVisitaRepository
                .findTopByDniVisitanteOrderByHoraIngresoDesc(dni);

        if (visita.isEmpty()) {
            return ResponseEntity.notFound().build(); // HTTP 404 → visitante nuevo
        }

        return ResponseEntity.ok(visita.get()); // HTTP 200 → devuelve el JSON del visitante
    }

}

