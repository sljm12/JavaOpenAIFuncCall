package hermes2pro.function;

import java.util.function.Function;

import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.openai.samples.helloworld.Application;
import org.springframework.ai.openai.samples.helloworld.simple.MockWeatherService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.Resource;

import com.fasterxml.jackson.core.JsonProcessingException;

import hermes2pro.function.service.MockTranslationService;
import hermes2pro.function.service.SearchGoogle;

@SpringBootTest(classes = Application.class)
@PropertySource("classpath:application.properties")
class Hermes2PromptTemplateTest {
	@Value("classpath:/prompts/hermes2-pro.st")
	private Resource systemResource;
	
	@Test
	void testTemplate() throws JsonProcessingException {
		Hermes2PromptTemplate template=new Hermes2PromptTemplate(systemResource);
		Message msg=template.createTemplate(new Function[] {new MockWeatherService(),new SearchGoogle(), new MockTranslationService()});
		System.out.println(msg.toString());
		
	}

}
