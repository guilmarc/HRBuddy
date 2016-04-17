package hrbuddy.Models;

import hrbuddy.Database.Database;
import hrbuddy.Database.QueryBuilder.Predicates.Predicate;
import hrbuddy.Database.QueryBuilder.Predicates.PredicateList;
import hrbuddy.Database.QueryBuilder.Query.InsertQuery;
import hrbuddy.Database.QueryBuilder.Query.Queryable;
import hrbuddy.Database.QueryBuilder.Query.UpdateQuery;
import hrbuddy.Exceptions.AttributeNotFoundException;
import hrbuddy.Utils.Logger;

import java.util.*;

/**
 * Created by Nicolas Boisvert on 2016-04-16.
 */
public abstract class Model{
    protected static String[] default_attributes;

    protected String _primary_key = "id";
    protected String _table;
    protected boolean _stored;
    protected int id;
    protected HashMap<String,Attribute> _attributes;

    public Model(HashMap<String,String> values){
        this.setAttributes(new HashMap<>());

        for(Map.Entry<String, String> entry : values.entrySet()) {
            String key = entry.getKey();
            this.getAttributes().put(key,new Attribute(entry.getValue()));
            if(key.equals(this.getPrimaryKey())){
                this.id = Integer.parseInt(entry.getValue());
            }
        }
        this.setStored(true);
    }
    public Model(){}

    public boolean setAttribute(String key,String value){
        try {
            if (this.getAttributes().containsKey(key)) {
                this.getAttribute(key).changeTo(value);
                return this.getAttribute(key).hasChange();
            }
        }
        catch (Exception e){
            Logger.warning(e.getMessage());
        }
        return false;
    }

    public Map<String,String> getMap(){
        Map<String,String> values = new HashMap<>();
        for(Map.Entry<String, Attribute> entry : this.getAttributes().entrySet()) {
            if(!entry.getKey().equals(this.getPrimaryKey())) {
                values.put(entry.getKey(), entry.getValue().getValue());
            }
        }
        return values;
    }

    public Queryable getQuery(){
        Queryable query;
        Map<String,String> map = this.getMap();
        List<String> columns = this.tableColumns();
        for(int i = 0; i < columns.size(); i++){
            if(!map.containsKey(columns.get(i))){
                map.remove(columns.get(i));
            }
        }
        if(this.isStored()){
            PredicateList list = new PredicateList();
            list.getPredicates().add(new Predicate("id",String.valueOf(this.id)));
            query = new UpdateQuery(this.getTable(),map, list);
        }
        else{
            query = new InsertQuery(this.getTable(),map);
        }
        return query;
    }

    public boolean attributesHasChanged(){
        for(Map.Entry<String, Attribute> entry : this.getAttributes().entrySet()) {
            if(entry.getValue().hasChange()){
                return true;
            }
        }
        return false;
    }

    public List<String> tableColumns(){
        return Database.getInstance().selectColumns(this.getTable());
    }

    public int save(){
        List<Integer> keys = Database.getInstance().execute(this.getQuery());
        if(!this.isStored()) {
            int key = -1;
            if (keys.size() > 0) {
                key = keys.get(0);
            }
            this.id = key;
            this.setStored(true);
            return key;
        }
        else {
            return this.id;
        }
    }

    public String toString(){
        return this.getAttributes().values().toString();
    }

    //      GETTERS

    public Attribute getAttribute(String key) throws AttributeNotFoundException {
        if(this.getAttributes().containsKey(key)) {
            return this.getAttributes().get(key);
        }
        throw new AttributeNotFoundException("Attribute "+key+" not found");
    }

    public String getPrimaryKey() {
        return _primary_key;
    }

    public String getTable() {
        return _table;
    }

    public HashMap<String, Attribute> getAttributes() {
        return _attributes;
    }

    public int getId() {
        return id;
    }

    public boolean isStored() {
        return _stored;
    }

    //      SETTERS
    public void setStored(boolean _stored) {
        this._stored = _stored;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setAttributes(HashMap<String, Attribute> _attributes) {
        this._attributes = _attributes;
    }

    public void setTable(String _table) {
        this._table = _table;
    }

    public void setPrimaryKey(String primary_key) {
        this._primary_key = primary_key;
    }
}
