package hermes2pro.function;

import java.util.HashMap;
import java.util.Map;

public class RecordParameter {

    private String parameterName;
    private boolean required;

    private Map<String,String> fields=new HashMap<String,String>();

    public void setParameterName(String parameterName){
        this.parameterName = parameterName;
    }

    public void setRequired(boolean required){
        this.required=required;
    }

    public void addField(String name, String type){
        fields.put(name, type);
    }
}
