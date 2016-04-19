package hrbuddy.Database.Schema;

import hrbuddy.Database.Database;
import hrbuddy.Database.QueryBuilder.Query.Queryable;
import hrbuddy.Exceptions.QueryWithoutPredicateException;
import hrbuddy.Utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by nboisvert on 18/04/16.
 */
public class Schema implements Queryable {
    protected HashMap<String,Column> columns;
    protected String table_name;
    protected String primary_key;

    public Schema(String table_name){
        this.table_name = table_name;
        this.primary_key = primary_key;
        this.columns = new HashMap<>();
    }

    public Schema(String table_name, String primary_key){
        this.table_name = table_name;
        this.primary_key = primary_key;
        this.columns = new HashMap<>();
        Column column = new Column(ColumnType.INTEGER,primary_key);
        column.setAutoIncrement(true);
        column.isPK();
        this.getColumns().put(primary_key,column);
    }

    public void string(String name){
        this.columns.put(name,new Column(ColumnType.STRING,name));
    }
    public void text(String name){
        this.columns.put(name,new Column(ColumnType.TEXT,name));
    }
    public void integer(String name){
        this.columns.put(name,new Column(ColumnType.INTEGER,name));
    }
    public void decimal(String name){
        this.columns.put(name,new Column(ColumnType.DECIMAL,name));
    }
    public void date(String name){
        this.columns.put(name,new Column(ColumnType.DATE,name));
    }
    public void datetime(String name){
        this.columns.put(name,new Column(ColumnType.DATETIME,name));
    }
    public String toString(){
        String output = "CREATE TABLE "+this.table_name+" (";
        List<String> cols = new ArrayList<>();
        for(Map.Entry<String,Column> entry : this.getColumns().entrySet()){
            cols.add(entry.getValue().toString());
        }
        //cols.add("PRIMARY KEY("+this.primary_key+")");
        output += Utils.implode(", ",cols)+");";
        return output;
    }
    public void create(){
        Database.getInstance().execute(this);
    }

    //      GETTERS
    public HashMap<String, Column> getColumns() {
        return columns;
    }
    public String getTable_name() {
        return table_name;
    }
    public String getPrimaryKey() {
        return primary_key;
    }

    //      SETTERS
    public void setPrimaryKey(String primary_key) {
        this.primary_key = primary_key;
    }
    public void setColumns(HashMap<String, Column> columns) {
        this.columns = columns;
    }
    public void setTable_name(String table_name) {
        this.table_name = table_name;
    }

    @Override
    public String getQuery() {
        return this.toString();
    }

    @Override
    public boolean asResultSet() {
        return false;
    }
}
