package org.springframework.ai.openai.samples.helloworld;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.reinert.jjschema.Attributes;
import com.github.reinert.jjschema.JsonSchemaGenerator;
import com.github.reinert.jjschema.SchemaGeneratorBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.ai.openai.samples.helloworld.simple.MockWeatherService;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.Arrays;
import java.util.StringJoiner;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = Application.class)
@PropertySource("classpath:application.properties")
public class TestJacksonObjectMapper {

    public static final String JSON_$SCHEMA_DRAFT4_VALUE = "http://json-schema.org/draft-04/schema#";
    public static final String JSON_$SCHEMA_ELEMENT = "$schema";

    static ObjectMapper mapper=new ObjectMapper();
    @Test
    public void testSerialise() throws JsonProcessingException {
        String s = mapper.writeValueAsString(new MockWeatherService());
        System.out.println(s);
    }


    public void testJJSchema() throws JsonProcessingException {
        JsonSchemaGenerator v4generator = SchemaGeneratorBuilder.draftV4Schema().build();

        // use the schema builder to generate JSON schema from Java class
        JsonNode schemaNode = v4generator.generateSchema(MockWeatherService.class);

        // add the $schema node to the schema. By default, JJSchema v0.6 does not add it
        ((ObjectNode) schemaNode).put(JSON_$SCHEMA_ELEMENT, JSON_$SCHEMA_DRAFT4_VALUE);

        // print the generated schema
        prettyPrintSchema(schemaNode);
    }

    private static void prettyPrintSchema(JsonNode schema) throws JsonProcessingException{
        System.out.println(mapper.writeValueAsString(schema));
    }

    @Test
    public void testReflect(){
        Field[] fields=MockWeatherService.class.getDeclaredFields();
        Class[] classes = MockWeatherService.class.getDeclaredClasses();
        for (Field f:fields){
            Attributes a = f.getAnnotation(Attributes.class);
            String[] s={f.getName(), a.description()};
            System.out.println(f.getName());
            System.out.println(a.description());
            StringJoiner joiner=new StringJoiner(", ");
            joiner.add(s[0]);
            joiner.add(s[1]);
            System.out.println(joiner.toString());
        }
    }

    @Test
    public void testMethod() throws NoSuchMethodException {
        Method[] methods = MockWeatherService.class.getDeclaredMethods();
        Attributes class_a = MockWeatherService.class.getAnnotation(Attributes.class);
        assertEquals(class_a.title(), "MockWeatherService");

        for(Method m:methods) {
            int mod=m.getModifiers();
            Boolean pu = Modifier.isPublic(mod);
            Attributes m_a = m.getAnnotation(Attributes.class);
            String[] m_attributes = {"Method", m.getName(), m_a.description()};
            String result = Arrays.stream(m_attributes).map(i -> i.toString()).collect(Collectors.joining(", "));

            System.out.println(result);

            Parameter[] parameters = m.getParameters();
            for (Parameter p : parameters) {
                System.out.println("Parameter "+p.getName());

                for(Field field:p.getType().getDeclaredFields()){
                    System.out.println(field.getName());
                }
                Attributes a = p.getType().getAnnotation(Attributes.class);
                if(a != null){
                    String[] s={a.annotationType().toString(),Boolean.toString(a.required()),a.description()};
                    result = Arrays.stream(s).map(i -> i.toString()).collect(Collectors.joining(", "));
                    System.out.println(result);
                }
            }
        }
    }
}
