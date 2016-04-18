package hrbuddy.Models;

import hrbuddy.Database.Database;
import hrbuddy.Database.QueryBuilder.Predicates.FieldSet;
import hrbuddy.Database.QueryBuilder.Predicates.Predicate;
import hrbuddy.Database.QueryBuilder.Predicates.PredicateList;
import hrbuddy.Database.QueryBuilder.Predicates.SearchPredicate;
import hrbuddy.Database.QueryBuilder.Query.InsertQuery;
import hrbuddy.Database.QueryBuilder.Query.Queryable;
import hrbuddy.Database.QueryBuilder.Query.SelectQuery;
import hrbuddy.Database.QueryBuilder.Query.UpdateQuery;
import hrbuddy.Utils.Logger;
import hrbuddy.Database.Migrations.Migration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Nicolas Boisvert on 2016-04-16.
 */
public class Experience {
    protected static String[] search_fields = {"job_function","start_date","end_date","organisation"};
    protected static String table = "experiences";
    protected static String migration_file = "experiences.sql";

    protected String id;
    protected Candidate candidate;
    protected String candidate_id;
    protected String job_function;
    protected String start_date;
    protected String end_date;
    protected String organisation;

    protected boolean stored;

    public Experience(String candidate_id, String job_function,String start_date,String end_date,String organisation){
        this.candidate_id = candidate_id;
        this.job_function = job_function;
        this.start_date = start_date;
        this.end_date = end_date;
        this.organisation = organisation;
        this.candidate = null;
        this.stored = false;
    }
    public Experience(){
        this.candidate_id = "-1";
        this.job_function = "";
        this.start_date = "";
        this.end_date = "";
        this.organisation = "";
    }
    public Experience(HashMap<String,String> list){
        this.id = (list.containsKey("id") ? list.get("id") : "");
        this.candidate_id = (list.containsKey("candidate_id") ? list.get("candidate_id") : "");
        this.job_function = (list.containsKey("job_function") ? list.get("job_function") : "");
        this.start_date = (list.containsKey("start_date") ? list.get("start_date") : "");
        this.end_date = (list.containsKey("end_date") ? list.get("end_date") : "");
        this.organisation = (list.containsKey("organisation") ? list.get("organisation") : "");
        this.stored = true;
    }

    public String getJobFunction() {
        return job_function;
    }

    public void setJobFunction(String job_function) {
        this.job_function = job_function;
    }

    public String getStartDate() {
        return start_date;
    }

    public void setStartDate(String start_date) {
        this.start_date = start_date;
    }

    public String getEndDate() {
        return end_date;
    }

    public void setEndDate(String end_date) {
        this.end_date = end_date;
    }

    public String getOrganisation() {
        return organisation;
    }

    public void setOrganisation(String organisation) {
        this.organisation = organisation;
    }

    public String getId(){ return this.id; }

    public Queryable getQuery(){
        Queryable query;
        if(this.stored){
            PredicateList list = new PredicateList();
            list.getPredicates().add(new Predicate("id",String.valueOf(this.id)));
            query = new UpdateQuery(Experience.table,this.getMap(), list);
        }
        else{
            query = new InsertQuery(Experience.table,this.getMap());
        }
        return query;
    }

    private Map<String,String> getMap(){
        Map<String,String> values = new HashMap<String, String>();
        values.put("candidate_id", this.candidate_id);
        values.put("job_function",this.job_function);
        values.put("start_date",this.start_date);
        values.put("end_date",this.end_date);
        values.put("organisation",this.organisation);
        return values;
    }

    public String toString(){
        return "id : "+this.id+"\n" +
                "job_function : "+this.job_function+"\n" +
                "start_date : "+this.start_date+"\n" +
                "end_date : "+this.end_date+"\n" +
                "organisation : "+this.organisation+"\n";
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
     * @return List of all the Experience
     */
    public static List<Experience> all(){
        List<HashMap<String,String>> list = Database.getInstance().select(new SelectQuery(Experience.table));
        List<Experience> Experience = new ArrayList<>();
        for(int i = 0; i < list.size(); i++){
            Experience.add(new Experience(list.get(i)));
        }
        return Experience;
    }
    /**
     * Return every instances of the table where at least one field from the static "search_fields" matched
     *
     * @param criteria, String of the search criteria
     * @return List of the matched Experience
     */
    public static List<Experience> search(String criteria){
        List<HashMap<String,String>> list = Database.getInstance().select(new SelectQuery(Experience.table,new SearchPredicate(criteria, Experience.search_fields), new FieldSet()));
        List<Experience> Experience = new ArrayList<>();
        for(int i = 0; i < list.size(); i++){
            Experience.add(new Experience(list.get(i)));
        }
        return Experience;
    }

    public static List<Experience> raw(SelectQuery query){
        List<HashMap<String,String>> list = Database.getInstance().select(query);
        List<Experience> Experience = new ArrayList<>();
        for(int i = 0; i < list.size(); i++){
            Experience.add(new Experience(list.get(i)));
        }
        return Experience;

    }
    /**
     * Return every instances of the table where at least one field from "search_fields" matched
     *
     * @param criteria, String of the search criteria
     * @param search_fields, String or list of string with all the field to search within
     * @return List of the matched Experience
     */
    public static List<Experience> search(String criteria, List<String> search_fields){
        List<HashMap<String,String>> list = Database.getInstance().select(new SelectQuery(Experience.table,new SearchPredicate(criteria, Experience.search_fields), new FieldSet(search_fields)));
        List<Experience> Experience = new ArrayList<>();
        for(int i = 0; i < list.size(); i++){
            Experience.add(new Experience(list.get(i)));
        }
        return Experience;

    }

    public static List<Experience> related(String key, int id){
        List<HashMap<String,String>> list = Database.getInstance().select(new SelectQuery(Experience.table,new Predicate(key,String.valueOf(id))));
        List<Experience> Experience = new ArrayList<>();
        for(int i = 0; i < list.size(); i++){
            Experience.add(new Experience(list.get(i)));
        }
        return Experience;

    }
    /**
     * Return every instances of the table where at least one field from "search_fields" matched
     *
     * @param criteria, String of the search criteria
     * @param search_fields, String or list of string with all the field to search within
     * @return List of the matched Experience
     */
    public static List<Experience> search(String criteria, String...search_fields){
        List<HashMap<String,String>> list = Database.getInstance().select(new SelectQuery(Experience.table,new SearchPredicate(criteria, Experience.search_fields), new FieldSet(search_fields)));
        List<Experience> Experience = new ArrayList<>();
        for(int i = 0; i < list.size(); i++){
            Experience.add(new Experience(list.get(i)));
        }
        return Experience;

    }
    /**
     * Return first instance of the table matching the given id
     *
     * @param id, Integer of the id you want to retrieve
     * @return Experience instance
     */
    public static Experience find(int id){
        List<HashMap<String,String>> list = Database.getInstance().select(new SelectQuery(Candidate.table,new Predicate("id", String.valueOf(id))));
        if(list.size() > 0){
            return new Experience(list.get(0));
        }
        else{
            Logger.debug("Experience not found");
            return null;
        }
    }
    /**
     * Create the migration, used if the database is not created
     *
     * @return Migration
     */
    public static Migration getMigration(){
        String table ="CREATE TABLE experiences (\n" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "candidate_id int NOT NULL,\n" +
                "job_function varchar(150),\n" +
                "start_date varchar(150),\n" +
                "end_date varchar(150),\n" +
                "organisation varchar(150)\n" +
                ");";
        return new Migration("Experience",table, Experience.migration_file);
    }

    public static String getTable(){ return Experience.table; }
}
