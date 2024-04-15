package hermes2pro.function;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecordParameter {

    private String parameterName;
    private boolean required;
    private String description;

    private Map<String,String> fields=new HashMap<String,String>();
    
    private List<FieldInfo> fieldInfoList = new ArrayList<FieldInfo>();

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
    
    public void addFieldInfo(FieldInfo fi) {
    	this.fieldInfoList.add(fi);
    }

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
    
    
}
