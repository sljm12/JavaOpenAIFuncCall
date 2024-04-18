package org.springframework.ai.openai.samples.helloworld.simple;

import java.util.function.Function;

import com.github.reinert.jjschema.Attributes;
import hermes2pro.function.Argument;
import org.springframework.ai.openai.samples.helloworld.simple.MockWeatherService.Request;
import org.springframework.ai.openai.samples.helloworld.simple.MockWeatherService.Response;
import org.springframework.context.annotation.Description;
@Description("Get the weather in location")
@Attributes(title = "MockWeatherService", description = "Gets the weather information given a location and the unit")
public class MockWeatherService implements Function<Request, Response>{
	public enum Unit { C, F }

	@Attributes(required=true, description = "location: location to look for, unit: the temperature unit to return the answer in")
	@Argument(name="location", description = "location to look for",type="str")
	@Argument(name="unit", description="the unit of the temperature that the answer is in",type="str")
	public record Request(String location, Unit unit) {}

	@Attributes(required=true, description = "temp (double): the numeric value of the temperature, unit: the unit of the temperature that the answer is in")
	@Argument(name="temp", description = "the numeric value of the temperature",type="double")
	@Argument(name="unit", description="the unit of the temperature that the answer is in",type="str")	
	public record Response(double temp, Unit unit) {}

	@Attributes(title = "MockWeatherService", description = "Gets the weather information given a location and the unit")
	public Response apply(Request request) {
		return new Response(30.0, Unit.C);
	}
}
