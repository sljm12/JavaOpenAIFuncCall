package hermes2pro.function;

import com.github.reinert.jjschema.Attributes;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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


            extractAttributeAnnotation(p, rp);
            
            FieldInfo[] fi=extractArgumentAnnotation(p);
            rp.setFieldInfos(fi);

            rp.setReturnType(extractMethodReturnType(m));

            arrParameters.add(rp);
        }


        return arrParameters;
    }
    
    private void extractAttributeAnnotation(Parameter p, RecordParameter rp) {
    	Attributes a = p.getType().getAnnotation(Attributes.class);
    	if(a != null){
            String[] s={a.annotationType().toString(),Boolean.toString(a.required()),a.description()};
            String result = Arrays.stream(s).map(i -> i.toString()).collect(Collectors.joining(", "));            

            rp.setRequired(a.required());
            rp.setDescription(a.description());
        }

        for(Field field:p.getType().getDeclaredFields()){        	
            rp.addField(field.getName(), field.getType().toString());            
        }
    }
    
    private FieldInfo[] extractArgumentAnnotation(Parameter p) {
    	ArrayList<FieldInfo> infos = new ArrayList<FieldInfo>();
    	Argument[] arguments = p.getType().getAnnotationsByType(Argument.class);
    	for(Argument a:arguments) {
    		FieldInfo info=new FieldInfo(a.name(),a.description(),a.type());
    		infos.add(info);
    	}
    	
    	return infos.toArray(new FieldInfo[0]);
    }

    private FieldInfo[] extractMethodReturnType(Method m){
        List<FieldInfo> fis = new ArrayList<FieldInfo>();
        Argument[] as=m.getReturnType().getAnnotationsByType(Argument.class);

        for(Argument a:as) {
            FieldInfo fi = new FieldInfo(a.name(), a.description(), a.type());
            fis.add(fi);
        }
        return fis.toArray(new FieldInfo[0]);
    }
    
    
    public JSONObject generate() {
    	JSONObject object = new JSONObject();
    	object.put("type", "function");
    	object.put("function", generateFunction());
    	
    	return object;
    }
    
    private JSONObject generateFunction() {
    	JSONObject object=new JSONObject();
    	object.put("name", functionName);
    	object.put("description", generateDescription());
    	object.put("parameters", generateParameters());
    	
    	return object;
    }
    
    private String generateDescription() {
    	StringBuilder builder=new StringBuilder();
    	    	    	
    	builder.append(functionName+"(" + getFunctionParameter() +") -> dict\n\n");
    	
    	//Arguments
    	builder.append("Args:\n\n");
    	
    	RecordParameter rp = this.parameters.get(0);
    	FieldInfo[] fis=rp.getFieldInfos();
    	
    	for(FieldInfo fi:fis) {
    		builder.append(fi.name()+" ("+fi.type()+"): "+fi.description()+"\n\n");
    	}

        //Return Type
        builder.append("\n\nReturns:\n");
        FieldInfo[] returnTypes=rp.getReturnType();

        for(FieldInfo fi: returnTypes) {
            builder.append(fi.type() + ": " + fi.description()+"\n");
        }

    	return builder.toString();
    }
    
    private String getFunctionParameter() {
    	RecordParameter rp = this.parameters.get(0);
    	
    	return Arrays.stream(rp.getFieldInfos()).map(i -> i.name()+":"+i.type()).collect(Collectors.joining(","));    	
    }
    
    private JSONObject generateParameters() {
    	JSONObject object = new JSONObject();
    	object.put("type", "object");
    	
    	FieldInfo[] fis=this.parameters.get(0).getFieldInfos();
    	
    	JSONObject properties=new JSONObject();
    	for(FieldInfo fi:fis) {
    		JSONObject propertiesType=new JSONObject();
    		propertiesType.put("type",fi.type());
    		properties.put(fi.name(), propertiesType);
    	}
    	
    	object.put("properties", properties);
    	return object;
    	
    }

    public static String extractSchema(Function function){
        Method[] methods = function.getClass().getDeclaredMethods();
        return null;

    }
}
