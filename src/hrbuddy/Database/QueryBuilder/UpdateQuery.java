package hrbuddy.Database.QueryBuilder;

import hrbuddy.Utils.Utils;

import java.util.*;

/**
 * Created by nboisvert on 2016-04-12.
 */
public class UpdateQuery implements Query {
    String table = "";
    Map<String,String> values = new HashMap<String, String>();
    String predicates = "";

    public UpdateQuery(String table, Map<String,String> values, String predicates){
        this.table = table;
        this.values = values;
        this.predicates = predicates;
    }

    @Override
    public String getQuery() {
        String output = "UPDATE "+this.table+" SET ";
        List<String> fields = new ArrayList<>();
        Iterator it = this.values.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            fields.add(pair.getKey()+"="+Utils.escapeForSql(pair.getValue().toString()));
            it.remove();
        }
        if(this.predicates.length() > 0){
           this.predicates = " WHERE "+this.predicates;
        }
        return output+Utils.implode(", ",fields)+this.predicates+";";
    }
}
