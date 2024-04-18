package hermes2pro.function;

import java.util.HashMap;
import java.util.Map;

public class RecordParameter {

    private String parameterName;
    private boolean required;
    private String description;
    private FieldInfo[] fieldInfos;

	private FieldInfo[] returnType;

    private Map<String,String> fields=new HashMap<String,String>();
 

    public void setParameterName(String parameterName){
        this.parameterName = parameterName;
    }

    public String getParameterName() {
		return parameterName;
	}

	public boolean isRequired() {
		return required;
	}

	public void setRequired(boolean required){
        this.required=required;
    }

    public void addField(String name, String type){
        fields.put(name, type);
    }
    
    public Map<String,String> getFields(){
    	return this.fields;
    }

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public FieldInfo[] getFieldInfos() {
		return fieldInfos;
	}

	public void setFieldInfos(FieldInfo[] fieldInfos) {
		this.fieldInfos = fieldInfos;
	}

	public FieldInfo[] getReturnType() {
		return returnType;
	}

	public void setReturnType(FieldInfo[] returnType) {
		this.returnType = returnType;
	}
}
