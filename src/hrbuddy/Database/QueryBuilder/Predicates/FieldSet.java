package hrbuddy.Database.QueryBuilder.Predicates;

import hrbuddy.Utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by nboisvert on 2016-04-17.
 */
public class FieldSet {
    protected HashMap<String,String> fields;

    public FieldSet(){
        this.fields = new HashMap<>();
    }
    public FieldSet(HashMap<String,String> fields){
        this.fields = fields;
    }
    public FieldSet(List<String> fields) {
        this.fields = new HashMap<>();
        for(String field : fields){
            this.getFields().put(field, "");
        }
    }
    public FieldSet(String...fields) {
        this.fields = new HashMap<>();
        for(String field : fields){
            this.getFields().put(field, "");
        }
    }

    public String toString(){
        if(this.fields.size() > 0){
            List<String> field_list = new ArrayList<>();
            for(Map.Entry<String,String> entry : this.fields.entrySet()){
                field_list.add(entry.getKey()+((entry.getValue().equals("") ? "" : " AS "+ Utils.escapeForSql(entry.getValue()))));
            }
            return Utils.implode(", ", field_list);
        }
        return "*";
    }

    //      GETTERS
    public HashMap<String, String> getFields() {
        return fields;
    }

    //      SETTERS
    public void setFields(HashMap<String, String> fields) {
        this.fields = fields;
    }
}
