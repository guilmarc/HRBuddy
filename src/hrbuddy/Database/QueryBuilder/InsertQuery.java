package hrbuddy.Database.QueryBuilder;

import hrbuddy.Utils.Utils;

import java.util.*;

/**
 * Created by nboisvert on 2016-04-12.
 */
public class InsertQuery implements Query {
    String table = "";
    Map<String,String> values = new HashMap<String, String>();

    public InsertQuery(String table, Map<String,String> values){
        this.table = table;
        this.values = values;
    }

    public String getQuery(){
        String query = "INSERT INTO "+this.table;
        List<String> fields = new ArrayList<>();
        List<String> value = new ArrayList<>();
        Iterator it = this.values.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            fields.add(pair.getKey().toString());
            value.add(Utils.escapeForSql(pair.getValue().toString()));
            it.remove();
        }
        return query+" ("+Utils.implode(",",fields)+") VALUES ("+Utils.implode(",",value)+");";
    }
}
