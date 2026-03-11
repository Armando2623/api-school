package com.ortiz.Proyecto.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Representa un mensaje privado entre dos usuarios del sistema.
 * Se persiste en MySQL para historial.
 */
@Entity
@Table(name = "mensajes")
public class Mensaje {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Username del remitente (campo 'usuario' de la tabla usuarios) */
    @Column(nullable = false, length = 100)
    private String remitente;

    /** Username del destinatario */
    @Column(nullable = false, length = 100)
    private String destinatario;

    /** Contenido del mensaje */
    @Column(nullable = false, columnDefinition = "TEXT")
    private String contenido;

    /** Fecha y hora de envío */
    @Column(nullable = false)
    private LocalDateTime timestamp;

    /** true = el destinatario ya lo leyó */
    @Column(nullable = false)
    private boolean leido = false;

    // ── Constructores ──────────────────────────────────────────
    public Mensaje() {
    }

    public Mensaje(String remitente, String destinatario, String contenido) {
        this.remitente = remitente;
        this.destinatario = destinatario;
        this.contenido = contenido;
        this.timestamp = LocalDateTime.now();
        this.leido = false;
    }

    // ── Getters y Setters ──────────────────────────────────────
    public Long getId() {
        return id;
    }

    public String getRemitente() {
        return remitente;
    }

    public void setRemitente(String r) {
        this.remitente = r;
    }

    public String getDestinatario() {
        return destinatario;
    }

    public void setDestinatario(String d) {
        this.destinatario = d;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String c) {
        this.contenido = c;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime t) {
        this.timestamp = t;
    }

    public boolean isLeido() {
        return leido;
    }

    public void setLeido(boolean l) {
        this.leido = l;
    }
}
