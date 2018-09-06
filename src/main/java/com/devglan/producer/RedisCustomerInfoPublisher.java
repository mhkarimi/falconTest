package com.devglan.producer;

import com.devglan.model.DummyJson;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import java.util.concurrent.atomic.AtomicInteger;

public class RedisCustomerInfoPublisher implements CustomerInfoPublisher {


	private final AtomicInteger counter = new AtomicInteger(0);

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

	@Autowired
	private ChannelTopic topic;

	public RedisCustomerInfoPublisher() {
	}

	public RedisCustomerInfoPublisher(RedisTemplate<String, Object> redisTemplate, ChannelTopic topic) {
		this.redisTemplate = redisTemplate;
		this.topic = topic;
	}

	@Override
	public void publish(String givenDummyJson) {

		DummyJson dummyJson = new Gson().fromJson(givenDummyJson,DummyJson.class);
		System.out.println(
				"Publishing... DummyJson with id=" + dummyJson.getId() + ", " + Thread.currentThread().getName());

		redisTemplate.convertAndSend(topic.getTopic(), givenDummyJson);
	}

}
