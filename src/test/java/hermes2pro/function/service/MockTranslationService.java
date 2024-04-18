package hermes2pro.function.service;

import com.github.reinert.jjschema.Attributes;
import hermes2pro.function.Argument;
import org.springframework.ai.openai.samples.helloworld.simple.MockWeatherService;

import java.util.function.Function;

@Attributes(title = "translate", description = "Translate the text to the specified language")
public class MockTranslationService implements Function<MockTranslationService.Request, MockTranslationService.Response> {

    @Argument(name="text",description = "The text to be translated",type="str")
    @Argument(name="language",description = "the language to translate the text to",type="str")
    public record Request(String text, String language){};

    @Argument(name="text", description = "The translated text", type="str")
    public record Response(String text){};

    @Override
    public Response apply(Request request) {
        return new Response("This is the translated text");
    }
}
