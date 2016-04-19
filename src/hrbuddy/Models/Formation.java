package hrbuddy.Models;

import hrbuddy.Database.Database;
import hrbuddy.Database.Migrations.Migration;
import hrbuddy.Database.QueryBuilder.Predicates.FieldSet;
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
 * Created by guilmarc on 2016-04-18.
 */
public class Formation {
    protected static String[] search_fields = {"diploma","date_formation"};
    protected static String table = "formations";
    protected static String migration_file = "formations.sql";

    protected String id;
    protected Candidate candidate;
    protected String candidate_id;
    protected String diploma;
    protected String date_formation;

    protected boolean stored;

    public Formation(String candidate_id, String diploma,String date_formation){
        this.candidate_id = candidate_id;
        this.diploma = diploma;
        this.date_formation = date_formation;
 
        this.candidate = null;
        this.stored = false;
    }
    public Formation(){
        this.candidate_id = "-1";
        this.diploma = "";
        this.date_formation = "";
    }
    public Formation(HashMap<String,String> list){
        this.id = (list.containsKey("id") ? list.get("id") : "");
        this.candidate_id = (list.containsKey("candidate_id") ? list.get("candidate_id") : "");
        this.diploma = (list.containsKey("diploma") ? list.get("diploma") : "");
        this.date_formation = (list.containsKey("date_formation") ? list.get("date_formation") : "");
        this.stored = true;
    }


    public String getDiploma() {
        return diploma;
    }

    public void setDiploma(String diploma) {
        this.diploma = diploma;
    }

    public String getDateFormation() {
        return date_formation;
    }

    public void setDateFormation(String date_formation) {
        this.date_formation = date_formation;
    }

    public String getId(){ return this.id; }

    public Queryable getQuery(){
        Queryable query;
        if(this.stored){
            PredicateList list = new PredicateList();
            list.getPredicates().add(new Predicate("id",String.valueOf(this.id)));
            query = new UpdateQuery(Formation.table,this.getMap(), list);
        }
        else{
            query = new InsertQuery(Formation.table,this.getMap());
        }
        return query;
    }

    private Map<String,String> getMap(){
        Map<String,String> values = new HashMap<String, String>();
        values.put("candidate_id", this.candidate_id);
        values.put("diploma",this.diploma);
        values.put("date_formation",this.date_formation);
        return values;
    }

    public String toString(){
        return "id : "+this.id+"\n" +
                "diploma : "+this.diploma+"\n" +
                "date_formation : "+this.date_formation+"\n";
    }


    public int save(){
        List<Integer> keys = Database.getInstance().execute(this.getQuery());
        if(!this.stored) {
            int key = -1;
            if (keys.size() > 0) {
                key = keys.get(0);
            }
            this.id = String.valueOf(key);
            this.stored = true;
            return key;
        }
        else {
            return Integer.parseInt(this.id);
        }
    }

    /**
     * Return every instances of the table
     *
     * @return List of all the Formation
     */
    public static List<Formation> all(){
        List<HashMap<String,String>> list = Database.getInstance().select(new SelectQuery(Formation.table));
        List<Formation> Formation = new ArrayList<>();
        for(int i = 0; i < list.size(); i++){
            Formation.add(new Formation(list.get(i)));
        }
        return Formation;
    }
    /**
     * Return every instances of the table where at least one field from the static "search_fields" matched
     *
     * @param criteria, String of the search criteria
     * @return List of the matched Formation
     */
    public static List<Formation> search(String criteria){
        List<HashMap<String,String>> list = Database.getInstance().select(new SelectQuery(Formation.table,new SearchPredicate(criteria, Formation.search_fields), new FieldSet()));
        List<Formation> Formation = new ArrayList<>();
        for(int i = 0; i < list.size(); i++){
            Formation.add(new Formation(list.get(i)));
        }
        return Formation;
    }

    public static List<Formation> raw(SelectQuery query){
        List<HashMap<String,String>> list = Database.getInstance().select(query);
        List<Formation> Formation = new ArrayList<>();
        for(int i = 0; i < list.size(); i++){
            Formation.add(new Formation(list.get(i)));
        }
        return Formation;

    }
    /**
     * Return every instances of the table where at least one field from "search_fields" matched
     *
     * @param criteria, String of the search criteria
     * @param search_fields, String or list of string with all the field to search within
     * @return List of the matched Formation
     */
    public static List<Formation> search(String criteria, List<String> search_fields){
        List<HashMap<String,String>> list = Database.getInstance().select(new SelectQuery(Formation.table,new SearchPredicate(criteria, Formation.search_fields), new FieldSet(search_fields)));
        List<Formation> Formation = new ArrayList<>();
        for(int i = 0; i < list.size(); i++){
            Formation.add(new Formation(list.get(i)));
        }
        return Formation;

    }

    public static List<Formation> related(String key, int id){
        List<HashMap<String,String>> list = Database.getInstance().select(new SelectQuery(Formation.table,new Predicate(key,String.valueOf(id))));
        List<Formation> Formation = new ArrayList<>();
        for(int i = 0; i < list.size(); i++){
            Formation.add(new Formation(list.get(i)));
        }
        return Formation;

    }
    /**
     * Return every instances of the table where at least one field from "search_fields" matched
     *
     * @param criteria, String of the search criteria
     * @param search_fields, String or list of string with all the field to search within
     * @return List of the matched Formation
     */
    public static List<Formation> search(String criteria, String...search_fields){
        List<HashMap<String,String>> list = Database.getInstance().select(new SelectQuery(Formation.table,new SearchPredicate(criteria, Formation.search_fields), new FieldSet(search_fields)));
        List<Formation> Formation = new ArrayList<>();
        for(int i = 0; i < list.size(); i++){
            Formation.add(new Formation(list.get(i)));
        }
        return Formation;

    }
    /**
     * Return first instance of the table matching the given id
     *
     * @param id, Integer of the id you want to retrieve
     * @return Formation instance
     */
    public static Formation find(int id){
        List<HashMap<String,String>> list = Database.getInstance().select(new SelectQuery(Candidate.table,new Predicate("id", String.valueOf(id))));
        if(list.size() > 0){
            return new Formation(list.get(0));
        }
        else{
            Logger.debug("Formation not found");
            return null;
        }
    }
    /**
     * Create the migration, used if the database is not created
     *
     * @return Migration
     */
    public static Migration getMigration(){
        String table ="CREATE TABLE formations (\n" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "candidate_id int NOT NULL,\n" +
                "diploma varchar(150),\n" +
                "date_formation varchar(150)\n" +
                ");";
        return new Migration("Formation",table, Formation.migration_file);
    }

    public static String getTable(){ return Formation.table; }
}
