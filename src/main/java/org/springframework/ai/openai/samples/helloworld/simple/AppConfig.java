package org.springframework.ai.openai.samples.helloworld.simple;

import org.springframework.ai.chat.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

	@Autowired
	private ApplicationContext appContext;
	
	/*
	@Bean
	public ChatClient ChatClient(@Value("${chatclient}") String chatclientName) {
		return (ChatClient) appContext.getBean(chatclientName);
	}*/
}
