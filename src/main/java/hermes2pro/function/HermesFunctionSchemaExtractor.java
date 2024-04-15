package hermes2pro.function;

import com.github.reinert.jjschema.Attributes;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Function;
import java.util.stream.Collectors;

public class HermesFunctionSchemaExtractor {

    private Function function;

    private String functionName;
    private String functionDescription;

    public HermesFunctionSchemaExtractor(Function function){
        this.function = function;
        extractClassInfo();
    }

    public void extractClassInfo(){
        Attributes a = this.function.getClass().getDeclaredAnnotation(Attributes.class);
        this.functionName =  a.title();
        this.functionDescription = a.description();
    }

    private void extractParameterInfo(){
        Method m = extractApply();
    }

    private Method extractApply(){
        Method[] methods = this.function.getClass().getDeclaredMethods();
        for(Method m: methods){
            Attributes a=m.getDeclaredAnnotation(Attributes.class);
            if(m.getName().equals("apply") && a != null){
                return m;
            }
        }

        return null;
    }

    private void extractParameterInfo(Method m){
        ArrayList arrParameters =new ArrayList<RecordParameter>();

        Parameter[] parameters = m.getParameters();
        for (Parameter p : parameters) {
            RecordParameter rp = new RecordParameter();
            System.out.println("Parameter "+p.getName());
            rp.setParameterName(p.getName());


            Attributes a = p.getType().getAnnotation(Attributes.class);
            if(a != null){
                String[] s={a.annotationType().toString(),Boolean.toString(a.required()),a.description()};
                String result = Arrays.stream(s).map(i -> i.toString()).collect(Collectors.joining(", "));
                System.out.println(result);

                rp.setRequired(a.required());
            }

            for(Field field:p.getType().getDeclaredFields()){
                rp.addField(field.getName(), field.getType().toString());
            }

            arrParameters.add(rp);
        }
    }

    public static String extractSchema(Function function){
        Method[] methods = function.getClass().getDeclaredMethods()

    }
}
