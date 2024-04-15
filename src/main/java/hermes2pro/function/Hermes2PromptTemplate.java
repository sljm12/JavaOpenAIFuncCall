package hermes2pro.function;

import java.util.Map;

import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

@Component
public class Hermes2PromptTemplate {
	public static final String TOOLS="tools";
	public static final String TOOL_FORMAT="tool_format";
	public static final String ARGUMENT_CALL="argument_call";
	
	private final String toolformat = "{\"title\": \"FunctionCall\", \"type\": \"object\", \"properties\": {\"arguments\": {\"title\": \"Arguments\", \"type\": \"object\"}, \"name\": {\"title\": \"Name\", \"type\": \"string\"}}, \"required\": [\"arguments\", \"name\"]}";
	private final String argumentcall= "{\"arguments\": <args-dict>, \"name\": <function-name>}";
	
	@Value("classpath:/prompts/hermes2-pro.st")
	private Resource systemResource;

	public Hermes2PromptTemplate(Resource resource){
		this.systemResource = resource;
	}
	public Message createTemplate() {
		SystemPromptTemplate systemPromptTemplate = new SystemPromptTemplate(systemResource);
		return systemPromptTemplate.createMessage(Map.of("tools","",TOOL_FORMAT,toolformat,ARGUMENT_CALL,argumentcall));
	}
	
	public boolean testResource(){
		return systemResource.exists();
	}
}
