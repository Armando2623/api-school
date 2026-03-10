package com.ortiz.Proyecto.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity(name = "RegistroVisita")
@Table(name = "registro_visita")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class RegistroVisita {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombreVisitante;

    private String dniVisitante;

    private String motivo;

    private LocalDateTime horaIngreso;

    @Enumerated(EnumType.STRING)
    private EstadoRegistro estadoRegistro;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    public RegistroVisita(DatosRegistroVisita datosRegistroVisita){
         this.nombreVisitante = datosRegistroVisita.nombreVisitante();
        this.dniVisitante = datosRegistroVisita.dniVisitante();
        this.motivo = datosRegistroVisita.motivo();
        this.horaIngreso = datosRegistroVisita.horaIngreso();
        if (datosRegistroVisita.estadoRegistro() == null ) {
            this.estadoRegistro = EstadoRegistro.REGISTRADO;
        }


        this.usuario = getUsuario() ;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreVisitante() {
        return nombreVisitante;
    }

    public void setNombreVisitante(String nombreVisitante) {
        this.nombreVisitante = nombreVisitante;
    }

    public String getDniVisitante() {
        return dniVisitante;
    }

    public void setDniVisitante(String dniVisitante) {
        this.dniVisitante = dniVisitante;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public LocalDateTime getHoraIngreso() {
        return horaIngreso;
    }

    public void setHoraIngreso(LocalDateTime horaIngreso) {
        this.horaIngreso = horaIngreso;
    }

    public EstadoRegistro getEstadoRegistro() {
        return estadoRegistro;
    }

    public void setEstadoRegistro(EstadoRegistro estadoRegistro) {
        this.estadoRegistro = estadoRegistro;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
