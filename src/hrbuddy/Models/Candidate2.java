package hrbuddy.Models;

import hrbuddy.Database.Database;
import hrbuddy.Database.QueryBuilder.Predicates.Predicate;
import hrbuddy.Database.QueryBuilder.Query.SelectQuery;
import hrbuddy.Utils.Logger;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Nicolas Boisvert on 2016-04-16.
 */
public class Candidate2 extends Model{
    protected static String table = "candidates";
    protected String _table = "candidates";

    public Candidate2(){

    }
    public Candidate2(HashMap<String,String> map){
        super(map);
    }

    /**
     * Return first instance of the table matching the given id
     *
     * @param id, Integer of the id you want to retrieve
     * @return Candidate instance
     */
    public static Candidate2 find(int id){
        List<HashMap<String,String>> list = Database.getInstance().select(new SelectQuery(Candidate2.table, new Predicate("id", String.valueOf(id))));
        if(list.size() > 0){
            return new Candidate2(list.get(0));
        }
        else{
            Logger.debug("Candidate not found");
            return null;
        }
    }

    public List<String> tableColumns(){
        return Database.getInstance().selectColumns(this.getTable());
    }


    //      GETTERS
    @Override
    public String getPrimaryKey() {
        return this._primary_key;
    }
    @Override
    public String getTable() {
        return this._table;
    }
    @Override
    public HashMap<String, Attribute> getAttributes() {
        return this._attributes;
    }
    @Override
    public int getId() {
        return this.id;
    }
    @Override
    public boolean isStored() {
        return this._stored;
    }
}
