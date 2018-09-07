package com.falcon.config;

import com.google.gson.Gson;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.Map;

@Component
public class SocketHandler extends TextWebSocketHandler {

	public static WebSocketSession session;
	public String receivedText;

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


	public void sendJsonObjectToWebSocketInGETMethod(String text) throws Exception {
		this.receivedText = text;
		// without line below  it would possibly raise exception below :
		// java.lang.IllegalStateException: The remote endpoint was in state [TEXT_PARTIAL_WRITING] which is an invalid state for called method
		synchronized(session) {
			session.sendMessage(new TextMessage(text));
		}

	}


}