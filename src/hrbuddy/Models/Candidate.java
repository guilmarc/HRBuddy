package hrbuddy.Models;

import com.sun.xml.internal.ws.api.ha.StickyFeature;
import hrbuddy.Database.Database;
import hrbuddy.Database.QueryBuilder.Predicates.FieldSet;
import hrbuddy.Database.QueryBuilder.Predicates.Predicate;
import hrbuddy.Database.QueryBuilder.Predicates.PredicateList;
import hrbuddy.Database.QueryBuilder.Predicates.SearchPredicate;
import hrbuddy.Database.QueryBuilder.Query.*;
import hrbuddy.Database.Schema.Schema;
import hrbuddy.Utils.Logger;
import hrbuddy.Database.Migrations.Migration;
import javafx.geometry.Pos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by nboisvert on 16-04-11.
 */

public class Candidate{
    protected static String[] search_fields = {"firstname","lastname","address","email","home_phone","cell_phone"};
    protected static String table = "candidates";
    protected static String migration_file = "candidates.sql";

    protected String id;
    protected String firstname;
    protected String lastname;
    protected String address;
    protected String email;
    protected String home_phone;
    protected String cell_phone;
    protected List<Experience> experiences;
    protected List<Postulation> postulations;
    protected List<Formation> formations;
    protected boolean stored = false;

    public Candidate(String firstname,String lastname,String address,String email,String home_phone,String cell_phone){
        this.firstname = firstname;
        this.lastname = lastname;
        this.address = address;
        this.email = email;
        this.home_phone = home_phone;
        this.cell_phone = cell_phone;
        this.experiences = null;
        this.stored = false;

    }
    public Candidate(){
        this.firstname = "";
        this.lastname = "";
        this.address = "";
        this.email = "";
        this.home_phone = "";
        this.cell_phone = "";
        this.experiences = null;
    }
    public Candidate(HashMap<String,String> list){
        this.id = (list.containsKey("id") ? list.get("id") : "");
        this.firstname = (list.containsKey("firstname") ? list.get("firstname") : "");
        this.lastname = (list.containsKey("lastname") ? list.get("lastname") : "");
        this.address = (list.containsKey("address") ? list.get("address") : "");
        this.email = (list.containsKey("email") ? list.get("email") : "");
        this.home_phone = (list.containsKey("home_phone") ? list.get("home_phone") : "");
        this.cell_phone = (list.containsKey("cell_phone") ? list.get("cell_phone") : "");
        this.experiences = null;
        this.stored = true;
    }

    public String getId(){
        return this.id;
    }
    public String getCellPhone() {
        return cell_phone;
    }
    public void setCellPhone(String cell_phone) {
        this.cell_phone = cell_phone;
    }
    public String getFirstname() {
        return firstname;
    }
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }
    public String getLastname() {
        return lastname;
    }
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getHomePhone() {
        return home_phone;
    }
    public void setHomePhone(String home_phone) {
        this.home_phone = home_phone;
    }

    private Map<String,String> getMap(){
        Map<String,String> values = new HashMap<String, String>();
        values.put("firstname",this.firstname);
        values.put("lastname",this.lastname);
        values.put("address",this.address);
        values.put("email",this.email);
        values.put("home_phone",this.home_phone);
        values.put("cell_phone",this.cell_phone);
        return values;
    }

    public Queryable getQuery(){
        Queryable query;
        if(this.stored){
            PredicateList list = new PredicateList();
            list.getPredicates().add(new Predicate("id",String.valueOf(this.id)));
            query = new UpdateQuery(Candidate.getTable(),this.getMap(), list);
        }
        else{
            query = new InsertQuery(Candidate.getTable(),this.getMap());
        }
        return query;
    }

    public String toString(){
        return "id : "+this.id+"\n" +
                        "firstname : "+this.firstname+"\n" +
                        "lastname : "+this.lastname+"\n" +
                        "address : "+this.address+"\n" +
                        "email : "+this.email+"\n" +
                        "home_phone : "+this.home_phone+"\n" +
                        "cell_phone : "+this.cell_phone+"\n";
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

    public List<Experience> getExperiences(boolean refresh){
        if((this.experiences == null || refresh) && this.stored == true){
            this.experiences = Experience.related("candidate_id",Integer.parseInt(this.id));
        }
        else if(!this.stored || this.experiences == null){
            this.experiences = new ArrayList<>();
        }
        return this.experiences;
    }

    public List<Formation> getFormations(boolean refresh){
        if((this.formations == null || refresh) && this.stored == true){
            this.formations = Formation.related("candidate_id",Integer.parseInt(this.id));
        }
        else if(!this.stored || this.formations == null){
            this.formations = new ArrayList<>();
        }
        return this.formations;
    }

    public List<Postulation> getPostulations(boolean refresh){
        if((this.postulations == null || refresh) && this.stored == true){
            this.postulations = Postulation.related("candidate_id",Integer.parseInt(this.id));
        }
        else if(!this.stored || this.formations == null){
            this.postulations = new ArrayList<>();
        }
        return this.postulations;
    }

    /**
     * Return every instances of the table
     *
     * @return List of all the Candidates
     */
    public static List<Candidate> all(){
        SelectQuery query = new SelectQuery(Candidate.getTable());
        List<HashMap<String,String>> list = Database.getInstance().select(query);
        List<Candidate> candidates = new ArrayList<>();
        for(int i = 0; i < list.size(); i++){
            candidates.add(new Candidate(list.get(i)));
        }
        return candidates;
    }
    /**
     * Return every instances of the table where at least one field from "search_fields" matched
     *
     * @param criteria, String of the search criteria
     * @param search_fields, String or list of string with all the field to search within
     * @return List of the matched candidates
     */
    public static List<Candidate> search(String criteria, List<String> search_fields){
        SelectQuery query = new SelectQuery(Candidate.getTable(), new SearchPredicate(criteria,Candidate.search_fields));
        List<HashMap<String,String>> list = Database.getInstance().select(query);
        List<Candidate> candidates = new ArrayList<>();
        for(int i = 0; i < list.size(); i++){
            candidates.add(new Candidate(list.get(i)));
        }
        return candidates;

    }
    /**
     * Return first instance of the table matching the given id
     *
     * @param id, Integer of the id you want to retrieve
     * @return Candidate instance
     */
    public static Candidate find(int id){
        SelectQuery query = new SelectQuery(Candidate.getTable(), new Predicate("id",String.valueOf(id)));
        List<HashMap<String,String>> list = Database.getInstance().select(query);
        if(list.size() > 0){
            return new Candidate(list.get(0));
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
        String table ="CREATE TABLE candidates (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "firstname varchar(150)," +
                "lastname varchar(150)," +
                "city varchar(150)," +
                "address varchar(150)," +
                "cell_phone varchar(150)," +
                "home_phone varchar(150)," +
                "email varchar(150)" +
                ");";
        return new Migration("Candidate",table, Candidate.migration_file);
    }

    public static String getTable(){
        return Candidate.table;
    }
}
