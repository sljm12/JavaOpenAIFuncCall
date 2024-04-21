package hermes2pro.function;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.ai.openai.samples.helloworld.simple.MockWeatherService;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.io.JsonStringEncoder;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.github.reinert.jjschema.Attributes;

import hermes2pro.function.service.MockTranslationService;
import hermes2pro.function.service.MockTranslationService.Response;
import hermes2pro.function.service.SearchGoogle;


class HermesFunctionSchemaExtractorTest {

	private static ObjectMapper mapper = new ObjectMapper();	
	HermesFunctionSchemaExtractor ex = new HermesFunctionSchemaExtractor(new MockWeatherService());
	
	@BeforeAll
	public static void setup() {
		mapper.enable(SerializationFeature.INDENT_OUTPUT);
	}

	//@Test
	public void testExtractClassInfo() {
		
		assertEquals("MockWeatherService", ex.getFunctionName());
		assertEquals("Gets the weather information given a location and the unit", ex.getFunctionDescription());
	}
	
//	@Test
	public void testExtractParameters() {		
		ArrayList<RecordParameter> arr=ex.getParameters();
		assertEquals(1, arr.size());
		RecordParameter rp = arr.get(0);
		
		assertEquals("request",rp.getParameterName());
		assertEquals("location: location to look for, unit: the temperature unit to return the answer in", rp.getDescription());
		assertTrue(rp.isRequired());
		
		Map<String,String> map = rp.getFields();
		assertEquals(map.size(),2);
		assertTrue(map.containsKey("location"));
		assertTrue(map.containsKey("unit"));
	}

	
	@Test
	public void generate() throws JsonProcessingException {		
		JSONObject object= ex.generate();
		//System.out.println(object.toString());
		System.out.println(mapper.writeValueAsString(object.toMap()));
	}

//	@Test
	public void generateFunctions() throws JsonProcessingException {

		Function[] functions = new Function[]{new MockWeatherService(), new MockTranslationService(), new SearchGoogle()};
		List<String> tools=new ArrayList<>();

		for(Function f:functions){
			HermesFunctionSchemaExtractor ex=new HermesFunctionSchemaExtractor(f);
			tools.add(ex.generateString());
		}

		String s = tools.stream().collect(Collectors.joining("\n\n"));
		System.out.println(s);
	}

//	@Test
	public void testJsonString(){
		JsonStringEncoder encoder =new JsonStringEncoder();
		String s=new String(encoder.quoteAsString("Test\n"));
		System.out.println(s);
	}

	//@Test
	public void testSearchGoogle(){
		HermesFunctionSchemaExtractor ex=new HermesFunctionSchemaExtractor(new SearchGoogle());
		RecordParameter rp = ex.getParameters().get(0);
		assertNotNull(rp);
	}
	
	//@Test
	public void testReturnType() {
		SearchGoogle g=new SearchGoogle();
		Method[] ms =g.getClass().getDeclaredMethods();
		
		for(Method m:ms) {
			Attributes a = m.getAnnotation(Attributes.class);
			if(m.getName()=="apply" && a!=null) {
				System.out.println("Got method");
				System.out.println(m.getReturnType());
				Argument[] ans =m.getReturnType().getAnnotationsByType(Argument.class);
				for(Argument as:ans) {
					System.out.println(as.annotationType());
				}
				//System.out.println(m.getReturnType().getAnnotations());
			}
		}
	}
	
//	@Test
	public void testReturn() {
		Response response=new Response("test");
		Argument[] as=response.getClass().getAnnotationsByType(Argument.class);
		System.out.println(as.length);
	}
}
