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

import org.json.JSONObject;

public class HermesFunctionSchemaExtractor {

    private Function function;

    private String functionName;
    private String functionDescription;

    private ArrayList<RecordParameter> parameters;
    
    public HermesFunctionSchemaExtractor(Function function){
        this.function = function;
        extractClassInfo();
        Method m = extractApply();
        this.parameters=extractParameterInfo(m);
    }

    
    public String getFunctionName() {
		return functionName;
	}


	public void setFunctionName(String functionName) {
		this.functionName = functionName;
	}


	public String getFunctionDescription() {
		return functionDescription;
	}


	public void setFunctionDescription(String functionDescription) {
		this.functionDescription = functionDescription;
	}

	
	public ArrayList<RecordParameter> getParameters() {
		return parameters;
	}


	public void setParameters(ArrayList<RecordParameter> parameters) {
		this.parameters = parameters;
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

    private ArrayList<RecordParameter> extractParameterInfo(Method m){
        ArrayList<RecordParameter> arrParameters =new ArrayList<RecordParameter>();

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
                rp.setDescription(a.description());
            }

            for(Field field:p.getType().getDeclaredFields()){
            	FieldInfo fi =new FieldInfo(field.getName(),a.description(),field.getType().toString());
                rp.addField(field.getName(), field.getType().toString());
                rp.addFieldInfo(fi);
            }

            arrParameters.add(rp);
        }
        return arrParameters;
    }
    
    public JSONObject generate() {
    	
    }

    public static String extractSchema(Function function){
        Method[] methods = function.getClass().getDeclaredMethods();
        return null;

    }
}
