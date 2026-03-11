package com.ortiz.Proyecto.controller;

import com.ortiz.Proyecto.models.Mensaje;
import com.ortiz.Proyecto.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Map;

/**
 * Controller de chat — maneja mensajes STOMP y REST para historial.
 */
@RestController
public class ChatController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private ChatService chatService;

    // ── STOMP: enviar mensaje privado ──────────────────────────
    /**
     * El cliente envía a /app/chat.privado con body:
     * { "destinatario": "jperez", "contenido": "Hola!" }
     *
     * El servidor lo persiste y lo enruta al destinatario en:
     * /user/{destinatario}/queue/chat
     *
     * Y también refleja el mensaje al remitente en:
     * /user/{remitente}/queue/chat
     * para que aparezca en su propia conversación.
     */
    @MessageMapping("/chat.privado")
    public void enviarMensajePrivado(@Payload Map<String, String> payload,
            Principal principal) {
        String remitente = principal.getName();
        String destinatario = payload.get("destinatario");
        String contenido = payload.get("contenido");

        if (destinatario == null || contenido == null || contenido.isBlank())
            return;

        // Guardar en BD
        Mensaje guardado = chatService.guardar(remitente, destinatario, contenido);

        // Enviar al destinatario
        messagingTemplate.convertAndSendToUser(destinatario, "/queue/chat", guardado);

        // Reflejar al remitente (para que vea su propio mensaje si tiene la
        // conversación abierta en otra pestaña)
        messagingTemplate.convertAndSendToUser(remitente, "/queue/chat", guardado);
    }

    // ── REST: historial de conversación ───────────────────────
    @GetMapping("/api/mensajes/historial")
    public ResponseEntity<List<Mensaje>> historial(@RequestParam String con,
            Principal principal) {
        String yo = principal.getName();
        List<Mensaje> mensajes = chatService.historial(yo, con);
        // Marcar como leídos los que me envió 'con'
        chatService.marcarLeidos(con, yo);
        return ResponseEntity.ok(mensajes);
    }

    // ── REST: cantidad de no leídos (para el badge) ───────────
    @GetMapping("/api/mensajes/no-leidos")
    public ResponseEntity<Map<String, Long>> noLeidos(Principal principal) {
        long count = chatService.noLeidos(principal.getName());
        return ResponseEntity.ok(Map.of("total", count));
    }

    // ── REST: marcar leídos manualmente ───────────────────────
    @PutMapping("/api/mensajes/leidos")
    public ResponseEntity<Void> marcarLeidos(@RequestParam String de,
            Principal principal) {
        chatService.marcarLeidos(de, principal.getName());
        return ResponseEntity.noContent().build();
    }
}
