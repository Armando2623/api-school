package com.ortiz.Proyecto.interfaces;

import com.ortiz.Proyecto.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IOUsuarios extends JpaRepository<Usuario, Long> {
}
