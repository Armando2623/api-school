package com.ortiz.Proyecto.interfaces;

import com.ortiz.Proyecto.models.RegistroVisita;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IORegistro extends JpaRepository<RegistroVisita, Long> {
    List<RegistroVisita> findAll();
    Optional<RegistroVisita> findTopByDniVisitanteOrderByHoraIngresoDesc(String dniVisitante);

}
