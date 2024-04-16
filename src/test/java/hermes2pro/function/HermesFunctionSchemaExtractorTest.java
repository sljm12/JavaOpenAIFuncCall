package hermes2pro.function;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Map;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.ai.openai.samples.helloworld.simple.MockWeatherService;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

class HermesFunctionSchemaExtractorTest {

	private static ObjectMapper mapper = new ObjectMapper();	
	HermesFunctionSchemaExtractor ex = new HermesFunctionSchemaExtractor(new MockWeatherService());
	
	@BeforeAll
	public static void setup() {
		mapper.enable(SerializationFeature.INDENT_OUTPUT);
	}

	@Test
	public void testExtractClassInfo() {
		
		assertEquals("MockWeatherService", ex.getFunctionName());
		assertEquals("Gets the weather information given a location and the unit", ex.getFunctionDescription());
	}
	
	@Test
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
}
