package com.devglan.config;

import org.apache.tomcat.websocket.WsSession;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import javax.websocket.DeploymentException;
import javax.websocket.Session;

@Configuration
@EnableWebSocket
//@CrossOrigin(origins = "*")
public class WebSocketConfig implements WebSocketConfigurer {


	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		registry.addHandler(new SocketHandler(), "/name").setAllowedOrigins("*");
	}


}