package com.devglan.producer;

import com.devglan.model.DummyJson;

public interface CustomerInfoPublisher {
	
	void publish(DummyJson dummyJson);
}
