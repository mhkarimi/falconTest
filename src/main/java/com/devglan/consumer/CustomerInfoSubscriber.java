package com.devglan.consumer;

import com.devglan.config.SocketHandler;
import com.devglan.model.DummyJson;
import com.devglan.repository.DummyJsonRepository;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

@Component
public class CustomerInfoSubscriber implements MessageListener {

	@Autowired
	SocketHandler socketHandler;

	@Autowired
	DummyJsonRepository dummyJsonRepository;

	@Override
	public void onMessage(Message message, byte[] pattern) {
		try {
			System.out.println("\n\n\n\n\n  message received from the publisher  \n" + message+"\n\n\n\n\n");
			dummyJsonRepository.save(new GsonBuilder().create().fromJson(message.toString(),DummyJson.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
