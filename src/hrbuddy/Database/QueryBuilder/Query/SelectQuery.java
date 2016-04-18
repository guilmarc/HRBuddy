package hrbuddy.Database.QueryBuilder.Query;

import hrbuddy.Database.QueryBuilder.Predicates.FieldSet;
import hrbuddy.Database.QueryBuilder.Predicates.Predicable;
import hrbuddy.Database.QueryBuilder.Predicates.RawPredicate;
import hrbuddy.Utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by nboisvert on 2016-04-17.
 */
public class SelectQuery implements Queryable {
    protected FieldSet fields;
    protected Predicable predicates;
    protected String table;

    public SelectQuery(String table){
        this.table = table;
        this.predicates = new RawPredicate("");
        this.fields = new FieldSet();
    }
    public SelectQuery(String table, Predicable predicates){
        this.table = table;
        this.predicates = predicates;
        this.fields = new FieldSet();
    }
    public SelectQuery(String table, Predicable predicates, FieldSet fields){
        this.table = table;
        this.predicates = predicates;
        this.fields = fields;
    }

    @Override
    public String getQuery() {
        String output = "SELECT "+this.getFields().toString()+" FROM "+this.table;
        if(this.getPredicates().hasPredicate()) {
            output += this.getPredicates().getPredicateString();
        }
        return output;
    }

    @Override
    public boolean asResultSet() {
        return true;
    }

    //      GETTERS
    public FieldSet getFields() {
        return fields;
    }
    public Predicable getPredicates() {
        return predicates;
    }
    public String getTable() {
        return table;
    }

    //      SETTERS
    public void setFields(FieldSet fields) {
        this.fields = fields;
    }
    public void setPredicates(Predicable predicates) {
        this.predicates = predicates;
    }
    public void setTable(String table) {
        this.table = table;
    }
}
