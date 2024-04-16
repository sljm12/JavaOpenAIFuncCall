package hermes2pro.function;

import org.junit.jupiter.api.Test;
import org.springframework.ai.openai.samples.helloworld.simple.MockWeatherService;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.annotation.Annotation;

public class ArgumentTest {
    @Test
    public void testRetrival(){
        Argument[] as= MockWeatherService.Response.class.getAnnotationsByType(Argument.class);

        for(Argument a:as){
            System.out.println(a.name());
        }
        assertEquals(2, as.length);
    }
}
