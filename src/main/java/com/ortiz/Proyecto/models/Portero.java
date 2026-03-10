package com.ortiz.Proyecto.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "portero")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Portero {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;

    @Enumerated(EnumType.STRING)
    private Turno turno;

    @OneToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;



    public Portero(Turno turno, Usuario usuario) {
        this.turno = turno;
        this.usuario = usuario;
    }
}
