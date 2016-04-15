package hrbuddy.Database.QueryBuilder;

import hrbuddy.Database.Database;
import hrbuddy.Utils.Utils;

import javax.xml.crypto.Data;
import java.util.*;

/**
 * Created by Nicolas Boisvert on 2016-04-15.
 */
public class SearchQuery implements Query {
    protected HashMap<String,String> criterias;
    protected HashMap<String,String> fields;
    protected String table;

    public SearchQuery(String table, HashMap<String,String> criterias, HashMap<String,String> fields){
        this.criterias = criterias;
        this.fields = fields;
        this.table = table;
    }

    public SearchQuery(String table, HashMap<String,String> criterias){
        this.criterias = criterias;
        this.fields = new HashMap<>();
        this.table = table;
    }

    public HashMap<String, String> getFields() {
        return fields;
    }

    public void setFields(HashMap<String, String> fields) {
        this.fields = fields;
    }

    public HashMap<String, String> getCriterias() {
        return criterias;
    }

    public void setCriterias(HashMap<String, String> criterias) {
        this.criterias = criterias;
    }

    @Override
    public String getQuery() {
        String output = "SELECT ";
        if(this.fields.size() > 0){
            List<String> field_list = new ArrayList<>();
            Iterator it = this.fields.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry pair = (Map.Entry)it.next();
                String field = pair.getKey()+" AS "+(Utils.escapeForSql(pair.getValue().toString()));
                field_list.add(field);
                it.remove();
            }
            output += Utils.implode(", ", field_list);
        }
        else{
            output += " * ";
        }
        output += " FROM "+this.table;
        if(this.criterias.size() > 0){
            List<String> predicate_list = new ArrayList<>();
            Iterator it = this.criterias.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry pair = (Map.Entry)it.next();
                String predicate = pair.getKey()+" LIKE "+(Utils.escapeForSql(pair.getValue().toString(),"%"));
                predicate_list.add(predicate);
                it.remove();
            }
            output += " WHERE " +Utils.implode(" AND ", predicate_list);
        }
        return output;
    }
}
