package org.springframework.ai.openai.samples.helloworld.simple;

import java.util.Map;

import org.springframework.ai.chat.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SimpleAiController {
	
	private final ChatClient chatClient;
	//private final ChatClient chatClient;

		
	public SimpleAiController(@Autowired
			@Qualifier("openAiChatClient") ChatClient chatClient) {
		this.chatClient = chatClient;
	}

	@GetMapping("/ai/simple")
	public Map<String, String> completion(@RequestParam(value = "message", defaultValue = "Tell me a joke") String message) {
		return Map.of("generation", chatClient.call(message));
	}
}
