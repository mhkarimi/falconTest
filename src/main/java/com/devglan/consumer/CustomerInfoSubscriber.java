package com.devglan.consumer;

import com.devglan.config.SocketHandler;
import com.devglan.model.DummyJson;
import com.devglan.repository.DummyJsonRepository;
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
		//System.out.println("Received >> " + message +  ", " + Thread.currentThread().getName() );
		try {
			//socketHandler.afterConnectionEstablished(message.toString());
			dummyJsonRepository.save()
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
