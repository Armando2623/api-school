package com.ortiz.Proyecto.repository;

import com.ortiz.Proyecto.models.Mensaje;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface MensajeRepository extends JpaRepository<Mensaje, Long> {

    /**
     * Devuelve la conversación entre dos usuarios, ordenada por fecha ascendente.
     */
    @Query("""
            SELECT m FROM Mensaje m
            WHERE (m.remitente = :a AND m.destinatario = :b)
               OR (m.remitente = :b AND m.destinatario = :a)
            ORDER BY m.timestamp ASC
            """)
    List<Mensaje> findConversacion(@Param("a") String a, @Param("b") String b);

    /**
     * Cuenta los mensajes no leídos que le llegan a un usuario.
     */
    long countByDestinatarioAndLeidoFalse(String destinatario);

    /**
     * Marca como leídos todos los mensajes enviados por 'remitente' a
     * 'destinatario'.
     */
    @Modifying
    @Transactional
    @Query("""
            UPDATE Mensaje m SET m.leido = true
            WHERE m.remitente = :remitente AND m.destinatario = :destinatario AND m.leido = false
            """)
    void marcarLeidos(@Param("remitente") String remitente,
            @Param("destinatario") String destinatario);
}
