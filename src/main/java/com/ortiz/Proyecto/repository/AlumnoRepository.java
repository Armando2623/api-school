package com.ortiz.Proyecto.repository;

import com.ortiz.Proyecto.models.Alumno;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlumnoRepository extends JpaRepository<Alumno, Long> {
    List<Alumno> findByApoderadoId(Long visitanteId);
}
