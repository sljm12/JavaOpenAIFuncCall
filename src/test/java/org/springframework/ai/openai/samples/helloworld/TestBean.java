package org.springframework.ai.openai.samples.helloworld;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.function.Function;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ai.openai.samples.helloworld.simple.MockWeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import hermes2pro.function.Hermes2PromptTemplate;


@SpringBootTest(classes = Application.class)
@PropertySource("classpath:application.properties")
public class TestBean {
	
	@Autowired
	private Function<MockWeatherService.Request, MockWeatherService.Response> service;
	private AnnotationConfigApplicationContext context;
	
	@Value("classpath:/prompts/hermes2-pro.st")
	private Resource systemResource;
	
	@BeforeEach
	void setUp() {
		//context= new AnnotationConfigApplicationContext();
		//context.scan("org.springframework.ai.openai.samples.helloworld");
		//context.refresh();
	}

	@Test
	void test() {
		//Object b = context.getBean("CurrentWeather");
		System.out.println(service.getClass().descriptorString());
		Annotation[] d=service.getClass().getDeclaredAnnotations();
		System.out.println(d.length);
		for(Annotation a:d) {
			System.out.println(a.toString());
		}
		
	}
	
	@Test
	void testParameters() {
		Method[] ms= service.getClass().getDeclaredMethods();		
		for(Method m:ms) {
			m.getName();			
			Parameter[] ps=m.getParameters();
			String s="";
			for(Parameter p:ps) {
				s=s+p.getName();
			}
			System.out.println(m.getName()+" "+s);
		}
	}
	
	@Test
	void testPrompt() {
		Hermes2PromptTemplate template=new Hermes2PromptTemplate(systemResource);
		System.out.println(template.createTemplate());
		/*
		SystemPromptTemplate systemPromptTemplate = new SystemPromptTemplate(systemResource);
		Message message= systemPromptTemplate.createMessage(Map.of(Hermes2PromptTemplate.TOOLS,"",
				Hermes2PromptTemplate.TOOL_FORMAT,""
				,Hermes2PromptTemplate.ARGUMENT_CALL,""));
		
		System.out.print(message);
		*/
	}
	
	@Test
	void testClasspathResourceFromClass() {
		Hermes2PromptTemplate template=new Hermes2PromptTemplate(systemResource);
		assertTrue(template.testResource());
	}
	
	

}
