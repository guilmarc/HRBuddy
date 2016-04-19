package hrbuddy.Models;

import hrbuddy.Database.Database;
import hrbuddy.Database.Migrations.Migration;
import hrbuddy.Database.QueryBuilder.Predicates.Predicate;
import hrbuddy.Database.QueryBuilder.Predicates.PredicateList;
import hrbuddy.Database.QueryBuilder.Predicates.SearchPredicate;
import hrbuddy.Database.QueryBuilder.Query.InsertQuery;
import hrbuddy.Database.QueryBuilder.Query.Queryable;
import hrbuddy.Database.QueryBuilder.Query.SelectQuery;
import hrbuddy.Database.QueryBuilder.Query.UpdateQuery;
import hrbuddy.Utils.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by nboisvert on 16-04-11.
 */

public class Gender {
    protected static String[] search_fields = {"gender"};
    protected static String table = "genders";
    protected static String migration_file = "genders.sql";

    protected String id;
    protected String gender;

    protected boolean stored = false;

    public Gender(){
        this.gender = "";
    }

    public Gender(HashMap<String,String> list){
        this.id = (list.containsKey("id") ? list.get("id") : "");
        this.gender = (list.containsKey("gender") ? list.get("gender") : "");
        this.stored = true;
    }

    public String getId(){
        return this.id;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    private Map<String,Object> getMap(){
        Map<String,Object> values = new HashMap<String, Object>();
        values.put("gender",this.gender);
        return values;
    }

    /*public Queryable getQuery(){
        Queryable query;
        if(this.stored){
            PredicateList list = new PredicateList();
            list.getPredicates().add(new Predicate("id",String.valueOf(this.id)));
            query = new UpdateQuery(Gender.getTable(),this.getMap(), list);
        }
        else{
            query = new InsertQuery(Gender.getTable(),this.getMap());
        }
        return query;
    }*/

    public String toString(){
        return this.gender;
    }


    /**
     * Return every instances of the table
     *
     * @return List of all the Candidates
     */
    public static List<Gender> all(){
        SelectQuery query = new SelectQuery(Gender.getTable());
        List<HashMap<String,String>> list = Database.getInstance().select(query);
        List<Gender> genders = new ArrayList<>();
        for(int i = 0; i < list.size(); i++){
            genders.add(new Gender(list.get(i)));
        }
        return genders;
    }
    /**
     * Return every instances of the table where at least one field from "search_fields" matched
     *
     * @param criteria, String of the search criteria
     * @param search_fields, String or list of string with all the field to search within
     * @return List of the matched candidates
     */
    public static List<Gender> search(String criteria, List<String> search_fields){
        SelectQuery query = new SelectQuery(Gender.getTable(), new SearchPredicate(criteria, Gender.search_fields));
        List<HashMap<String,String>> list = Database.getInstance().select(query);
        List<Gender> candidates = new ArrayList<>();
        for(int i = 0; i < list.size(); i++){
            candidates.add(new Gender(list.get(i)));
        }
        return candidates;

    }
    /**
     * Return first instance of the table matching the given id
     *
     * @param id, Integer of the id you want to retrieve
     * @return Candidate instance
     */
    public static Gender find(int id){
        SelectQuery query = new SelectQuery(Gender.getTable(), new Predicate("id",String.valueOf(id)));
        List<HashMap<String,String>> list = Database.getInstance().select(query);
        if(list.size() > 0){
            return new Gender(list.get(0));
        }
        else{
            Logger.debug("Gender not found");
            return null;
        }
    }
    /**
     * Create the migration, used if the database is not created
     *
     * @return Migration
     */
    public static Migration getMigration(){
        String table ="CREATE TABLE genders (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "gender varchar(5)" +
                ");";
        return new Migration("Gender",table, Gender.migration_file);
    }

    public static String getTable(){
        return Gender.table;
    }
}
