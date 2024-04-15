package hermes2pro.function;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.ai.openai.samples.helloworld.simple.MockWeatherService;

class HermesFunctionSchemaExtractorTest {
	
	@Test
	public void testExtractClassInfo() {
		HermesFunctionSchemaExtractor ex = new HermesFunctionSchemaExtractor(new MockWeatherService());
		assertEquals("MockWeatherService", ex.getFunctionName());
		assertEquals("Gets the weather information given a location and the unit", ex.getFunctionDescription());
	}
	
	@Test
	public void testExtractParameters() {
		HermesFunctionSchemaExtractor ex = new HermesFunctionSchemaExtractor(new MockWeatherService());
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

}
