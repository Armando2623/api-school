package com.ortiz.Proyecto.service;

import com.ortiz.Proyecto.models.Mensaje;
import com.ortiz.Proyecto.repository.MensajeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatService {

    @Autowired
    private MensajeRepository mensajeRepository;

    /** Persiste un mensaje y lo devuelve con ID y timestamp asignados */
    public Mensaje guardar(String remitente, String destinatario, String contenido) {
        Mensaje m = new Mensaje(remitente, destinatario, contenido);
        return mensajeRepository.save(m);
    }

    /** Devuelve el historial de conversación entre dos usuarios */
    public List<Mensaje> historial(String usuarioA, String usuarioB) {
        return mensajeRepository.findConversacion(usuarioA, usuarioB);
    }

    /** Cuenta cuántos mensajes no leídos tiene un usuario en total */
    public long noLeidos(String destinatario) {
        return mensajeRepository.countByDestinatarioAndLeidoFalse(destinatario);
    }

    /** Marca como leídos los mensajes que 'de' le envió a 'para' */
    public void marcarLeidos(String de, String para) {
        mensajeRepository.marcarLeidos(de, para);
    }
}
