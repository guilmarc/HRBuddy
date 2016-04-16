package hrbuddy.Models;

import hrbuddy.Database.Database;
import hrbuddy.Database.QueryBuilder.InsertQuery;
import hrbuddy.Database.QueryBuilder.Query;
import hrbuddy.Database.QueryBuilder.UpdateQuery;
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
        Iterator it = values.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String,String> pair = (Map.Entry)it.next();
            String key = pair.getKey();
            this.getAttributes().put(key,new Attribute(pair.getValue()));
            if(key.equals(this.getPrimaryKey())){
                this.id = Integer.parseInt(pair.getValue());
            }
            it.remove();
        }
        this.setStored(true);
    }
    public Model(){}

    public boolean setAttribute(String key,String value){
        if(this.getAttributes().containsKey(key)) {
            this.getAttribute(key).changeTo(value);
            return this.getAttribute(key).hasChange();
        }
        return false;
    }

    public Map<String,String> getMap(){
        Map<String,String> values = new HashMap<>();
        Iterator it = this.getAttributes().entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String,Attribute> pair = (Map.Entry)it.next();
            if(!pair.getKey().equals(this.getPrimaryKey())) {
                values.put(pair.getKey(), pair.getValue().getValue());
            }
            it.remove();
        }
        Logger.warning(values.toString());
        return values;
    }

    public Query getQuery(){
        Query query;
        Map<String,String> map = this.getMap();
        List<String> columns = this.tableColumns();
        for(int i = 0; i < columns.size(); i++){
            if(!map.containsKey(columns.get(i))){
                map.remove(columns.get(i));
            }
        }
        if(this.isStored()){
            query = new UpdateQuery(this.getTable(),map, ("id = "+this.id));
        }
        else{
            query = new InsertQuery(this.getTable(),map);
        }
        Logger.warning(query.getQuery());
        return query;
    }

    public boolean attributesHasChanged(){
        Iterator it = this.getAttributes().entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String,Attribute> pair = (Map.Entry)it.next();
            if(pair.getValue().hasChange()){
                return true;
            }
            it.remove();
        }
        return false;
    }

    public List<String> tableColumns(){
        return Database.getInstance().selectColumns(this.getTable());
    }

    public int save(){
        Logger.debug(this._table);
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

    public Attribute getAttribute(String key){
        if(this._attributes.containsKey(key)) {
            return this._attributes.get(key);
        }
        return null;
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
