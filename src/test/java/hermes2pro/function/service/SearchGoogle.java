package hermes2pro.function.service;

import com.github.reinert.jjschema.Attributes;
import hermes2pro.function.Argument;

import java.util.function.Function;

@Attributes(title = "search_google", description = "Search google using the query and return the results")
public class SearchGoogle implements Function<SearchGoogle.Request, SearchGoogle.Response> {


    @Argument(name="query",description = "The question or query to search for.",type="str")
    public record Request(String query){};
    
    @Argument(name="result",description = "A string containing the result.",type="str")
    public record Response(String result){};

    @Override
    @Attributes(title = "search_google", description = "Search google using the query and return the results")
    public Response apply(Request request) {
        return new Response("This is the search result");
    }
}
