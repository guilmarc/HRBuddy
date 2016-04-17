package hrbuddy.Database.QueryBuilder.Query;

import hrbuddy.Database.QueryBuilder.Predicates.Predicable;
import hrbuddy.Exceptions.QueryWithoutPredicateException;
import hrbuddy.Utils.Utils;

import java.util.*;

/**
 * Created by nboisvert on 2016-04-12.
 */
public class UpdateQuery implements Queryable {
    protected String table = "";
    protected Map<String,String> values = new HashMap<String, String>();
    protected Predicable predicates;
    protected boolean safety = true;


    public UpdateQuery(String table, Map<String,String> values, Predicable predicates){
        this.table = table;
        this.values = values;
        this.predicates = predicates;
    }

    @Override
    public String getQuery() throws QueryWithoutPredicateException {
        if(this.safety && !this.getPredicates().hasPredicate()){
            throw new QueryWithoutPredicateException("Update query would overwrite all data");
        }
        String output = "UPDATE "+this.table+" SET ";
        List<String> fields = new ArrayList<>();
        for(Map.Entry<String,String> entry : this.values.entrySet()){
            fields.add(entry.getKey()+"="+Utils.escapeForSql(entry.getValue().toString()));

        }
        return output+Utils.implode(", ",fields)+this.getPredicates().getPredicateString();
    }

    //      GETTERS
    public String getTable() {
        return table;
    }
    public Map<String, String> getValues() {
        return values;
    }
    public Predicable getPredicates() {
        return predicates;
    }
    public boolean isSafety() {
        return safety;
    }

    //      SETTERS
    public void setTable(String table) {
        this.table = table;
    }
    public void setValues(Map<String, String> values) {
        this.values = values;
    }
    public void setPredicates(Predicable predicates) {
        this.predicates = predicates;
    }
    public void setSafety(boolean safety) {
        this.safety = safety;
    }
}
