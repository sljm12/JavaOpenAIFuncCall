package org.springframework.ai.openai.samples.helloworld.simple;

import org.springframework.ai.chat.ChatClient;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FunctionController{
	private final ChatClient chatClient;
	
	private final String systemPrompt = "You are a function calling AI model. You are provided with function signatures within <tools></tools> XML tags. You may call one or more functions to assist with the user query. Don't make assumptions about what values to plug into functions. \r\n"
			+ "\r\n"
			+ "Here are the available tools: \r\n"
			+ "\r\n"
			+ "<tools> \r\n"
			+ "\r\n"
			+ "{'type': 'function', 'function': {'name': 'get_stock_fundamentals', 'description': 'get_stock_fundamentals(symbol: str) -> dict - Get fundamental data for a given stock symbol using yfinance API.\r\n"
			+ "\r\n"
			+ "    Args:\r\n"
			+ "    symbol (str): The stock symbol.\r\n"
			+ "\r\n"
			+ "    Returns:\r\n"
			+ "    dict: A dictionary containing fundamental data.', 'parameters': {'type': 'object', 'properties': {'symbol': {'type': 'string'}}, 'required': ['symbol']}}}\r\n"
			+ "\r\n"
			+ "{'type': 'function', 'function': {'name': 'search_google', 'description': 'search_google(query: str) -> str - Search google using the query and return the results\r\n"
			+ "\r\n"
			+ "    Args:\r\n"
			+ "    query (str): The question or query to search for.\r\n"
			+ "\r\n"
			+ "    Returns:\r\n"
			+ "    str: a string containing the result.', 'parameters': {'type': 'object', 'properties': {'query': {'type': 'string'}}, 'required': ['query']}}}\r\n"
			+ "\r\n"
			+ "{'type': 'function', 'function': {'name': 'translate', 'description': 'translate(text: str, to_language) -> str - translate the text to the to_language\r\n"
			+ "\r\n"
			+ "    Args:\r\n"
			+ "    text (str): The text to be translated, to_language(str): the language to translate the text to\r\n"
			+ "\r\n"
			+ "    Returns:\r\n"
			+ "    str: a string containing the translated text.', 'parameters': {'type': 'object', 'properties': {'text': {'type': 'string'}, 'to_language': {'type': 'string'}}, 'required': ['text', 'to_language']}}}  \r\n"
			+ "</tools> \r\n"
			+ "\r\n"
			+ "Use the following pydantic model json schema for each tool call you will make: {'title': 'FunctionCall', 'type': 'object', 'properties': {'arguments': {'title': 'Arguments', 'type': 'object'}, 'name': {'title': 'Name', 'type': 'string'}}, 'required': ['arguments', 'name']} \r\n"
			+ "\r\n"
			+ "\r\n"
			+ "For each function call return a json object with function name and arguments within <tool_call></tool_call> XML tags as follows:\r\n"
			+ "<tool_call>\r\n"
			+ "{'arguments': <args-dict>, 'name': <function-name>}\r\n"
			+ "</tool_call>\r\n"
			+ "";
	
	private SystemPromptTemplate systemPromptTemplate=new SystemPromptTemplate(systemPrompt);
	
	@Autowired
	public FunctionController(ChatClient chatClient) {
		this.chatClient=chatClient;
	}
}