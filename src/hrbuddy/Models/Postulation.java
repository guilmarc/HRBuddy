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
 * Created by nboisvert on 18/04/16.
 */
public class Postulation {
    protected static String[] search_fields = {"date","job_postulated","status","reason"};
    protected static String migration_file = "postulations.sql";
    protected static String table = "postulations";

    protected String id;
    protected String candidate_id;
    protected String date;
    protected String job_postulated;
    protected String status;
    protected String reason;
    protected boolean stored;

    public Postulation(HashMap<String,String> list){
        this.id = list.get("id");
        this.candidate_id = list.get("candidate_id");
        this.date = list.get("date");
        this.job_postulated = list.get("job_postulated");
        this.status = list.get("status");
        this.reason = list.get("reason");
        this.stored = true;
    }

    private Map<String,String> getMap(){
        Map<String,String> values = new HashMap<String, String>();
        values.put("candidate_id",this.candidate_id);
        values.put("date",this.date);
        values.put("job_postulated",this.job_postulated);
        values.put("status",this.status);
        values.put("reason",this.reason);
        return values;
    }

    public Queryable getQuery(){
        Queryable query;
        if(this.stored){
            PredicateList list = new PredicateList();
            list.getPredicates().add(new Predicate("id",String.valueOf(this.id)));
            query = new UpdateQuery(Postulation.getTable(),this.getMap(), list);
        }
        else{
            query = new InsertQuery(Postulation.getTable(),this.getMap());
        }
        return query;
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

    public static List<Postulation> related(String key, int id){
        List<HashMap<String,String>> list = Database.getInstance().select(new SelectQuery(Postulation.table,new Predicate(key,String.valueOf(id))));
        List<Postulation> postulations = new ArrayList<>();
        for(int i = 0; i < list.size(); i++){
            postulations.add(new Postulation(list.get(i)));
        }
        return postulations;

    }
    /**
     * Return every instances of the table
     *
     * @return List of all the Candidates
     */
    public static List<Postulation> all(){
        SelectQuery query = new SelectQuery(Postulation.getTable());
        List<HashMap<String,String>> list = Database.getInstance().select(query);
        List<Postulation> postulations = new ArrayList<>();
        for(int i = 0; i < list.size(); i++){
            postulations.add(new Postulation(list.get(i)));
        }
        return postulations;
    }
    /**
     * Return every instances of the table where at least one field from "search_fields" matched
     *
     * @param criteria, String of the search criteria
     * @param search_fields, String or list of string with all the field to search within
     * @return List of the matched candidates
     */
    public static List<Postulation> search(String criteria, List<String> search_fields){
        SelectQuery query = new SelectQuery(Postulation.getTable(), new SearchPredicate(criteria,Postulation.search_fields));
        List<HashMap<String,String>> list = Database.getInstance().select(query);
        List<Postulation> postulations = new ArrayList<>();
        for(int i = 0; i < list.size(); i++){
            postulations.add(new Postulation(list.get(i)));
        }
        return postulations;

    }
    /**
     * Return first instance of the table matching the given id
     *
     * @param id, Integer of the id you want to retrieve
     * @return Candidate instance
     */
    public static Postulation find(int id){
        SelectQuery query = new SelectQuery(Postulation.getTable(), new Predicate("id",String.valueOf(id)));
        List<HashMap<String,String>> list = Database.getInstance().select(query);
        if(list.size() > 0){
            return new Postulation(list.get(0));
        }
        else{
            Logger.debug("Candidate not found");
            return null;
        }
    }
    /**
     * Create the migration, used if the database is not created
     *
     * @return Migration
     */
    public static Migration getMigration(){
        String table ="CREATE TABLE postulations (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "candidate_id INTEGER NOT NULL,"+
                "date varchar(255),"+
                "job_postulated varchar(255),"+
                "status varchar(255),"+
                "reason varchar(255)"+
                ");";
        return new Migration("Postulation",table, Postulation.migration_file);
    }
    public static String getTable(){
        return Postulation.table;
    }
}
