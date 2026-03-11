package com.ortiz.Proyecto.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "alumno")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Alumno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    private String grado;

    private String seccion;

    /**
     * Apoderado (padre/madre/tutor) del alumno.
     * Nullable para no romper alumnos ya existentes que no tienen apoderado
     * asignado.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "visitante_id", nullable = true)
    private Visitante apoderado;

    // Constructor de conveniencia sin apoderado (compatibilidad hacia atrás)
    public Alumno(String nombre, String grado, String seccion) {
        this.nombre = nombre;
        this.grado = grado;
        this.seccion = seccion;
    }

    // Constructor con apoderado
    public Alumno(String nombre, String grado, String seccion, Visitante apoderado) {
        this.nombre = nombre;
        this.grado = grado;
        this.seccion = seccion;
        this.apoderado = apoderado;
    }

    public void setApoderado(Visitante apoderado) {
        this.apoderado = apoderado;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setGrado(String grado) {
        this.grado = grado;
    }

    public void setSeccion(String seccion) {
        this.seccion = seccion;
    }
}
