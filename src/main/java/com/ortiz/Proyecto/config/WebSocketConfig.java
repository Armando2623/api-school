package com.ortiz.Proyecto.config;

import com.ortiz.Proyecto.security.WebSocketAuthInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Autowired
    private WebSocketAuthInterceptor wsAuthInterceptor;

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // Endpoint SockJS que permite fallback a HTTP long-polling
        registry.addEndpoint("/ws")
                .setAllowedOriginPatterns("*")
                .withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // Prefijo para métodos @MessageMapping en los controllers
        registry.setApplicationDestinationPrefixes("/app");

        // Broker simple en memoria:
        // /topic → mensajes broadcast
        // /user → mensajes privados (/user/{username}/queue/...)
        registry.enableSimpleBroker("/topic", "/user");

        // Prefijo para /user/{username}/queue/...
        registry.setUserDestinationPrefix("/user");
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        // Validar JWT en el frame STOMP CONNECT
        registration.interceptors(wsAuthInterceptor);
    }
}
