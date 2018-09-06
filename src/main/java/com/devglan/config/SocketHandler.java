package com.devglan.config;

import com.google.gson.Gson;
import org.hibernate.dialect.SybaseAnywhereDialect;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import javax.websocket.Session;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class SocketHandler extends TextWebSocketHandler {


	//List<WebSocketSession> sessions = new CopyOnWriteArrayList<>();
	public static WebSocketSession session;
	public String shit;

	@Override
	public void handleTextMessage(WebSocketSession session, TextMessage message)
			throws InterruptedException, IOException {
		Map<String, String> value = new Gson().fromJson(message.getPayload(), Map.class);
		session.sendMessage(new TextMessage("Hello " + value.get("name") + " !"));
		}

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		this.session =session ;
		session.sendMessage(new TextMessage("session initialize" ));
	}


	public void afterConnectionEstablished(String text) throws Exception {
		this.shit = text;
		// without line below  i would get exception :
		// java.lang.IllegalStateException: The remote endpoint was in state [TEXT_PARTIAL_WRITING] which is an invalid state for called method
		synchronized(session) {
			session.sendMessage(new TextMessage(text));
		}

	}


}