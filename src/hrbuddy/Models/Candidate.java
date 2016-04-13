package hrbuddy.Models;

import hrbuddy.Database.Database;
import hrbuddy.Database.QueryBuilder.InsertQuery;
import hrbuddy.Database.QueryBuilder.Query;
import hrbuddy.Database.QueryBuilder.UpdateQuery;
import hrbuddy.Utils.Logger;
import hrbuddy.Utils.Migration;
import sun.rmi.runtime.Log;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by nboisvert on 16-04-11.
 */

public class Candidate {
    protected static String[] search_fields = {"firstname","lastname","address","email","home_phone","cell_phone"};
    protected static String table = "candidates";

    protected String id;
    protected String firstname;
    protected String lastname;
    protected String address;
    protected String email;
    protected String home_phone;
    protected String cell_phone;
    protected boolean stored = false;

    public Candidate(String firstname,String lastname,String address,String email,String home_phone,String cell_phone){
        this.firstname = firstname;
        this.lastname = lastname;
        this.address = address;
        this.email = email;
        this.home_phone = home_phone;
        this.cell_phone = cell_phone;
        this.stored = false;
    }
    public Candidate(){
        this.firstname = "";
        this.lastname = "";
        this.address = "";
        this.email = "";
        this.home_phone = "";
        this.cell_phone = "";
    }
    public Candidate(ResultSet resultSet){
        try{
            this.id = resultSet.getString("id");
            this.firstname = resultSet.getString("firstname");
            this.lastname = resultSet.getString("lastname");
            this.address = resultSet.getString("address");
            this.email = resultSet.getString("email");
            this.home_phone = resultSet.getString("home_phone");
            this.cell_phone = resultSet.getString("cell_phone");
            this.stored = true;
        }
        catch (Exception e){
            Logger.except(e.getMessage());
        }
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

    public Query getQuery(){
        Query query;
        if(this.stored){
            query = new UpdateQuery(Candidate.table,this.getMap(), ("id = "+this.id));
        }
        else{
            query = new InsertQuery(Candidate.table,this.getMap());
        }
        return query;
    }

    public int save(){
        ResultSet keys = Database.getInstance().execute(this.getQuery());
        int key = -1;
        try {
            keys.next();
            key = keys.getInt(1);
            this.id = String.valueOf(key);
            keys.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return key;
    }

    /**
     * Return every instances of the table
     *
     * @return List of all the Candidates
     */
    public static List<Candidate> all(){
        ResultSet rs = Database.getInstance().select(Candidate.table);
        List<Candidate> candidates = new ArrayList<>();
        try {
            while(rs.next()){
                candidates.add(new Candidate(rs));
            }
        } catch (SQLException e) {
            Logger.except(e.getMessage());
            return null;
        }
        return candidates;
    }
    /**
     * Return every instances of the table where at least one field from the static "search_fields" matched
     *
     * @param criteria, String of the search criteria
     * @return List of the matched candidates
     */
    public static List<Candidate> search(String criteria){
        ResultSet rs = Database.getInstance().selectSearch(Candidate.table,criteria, Candidate.search_fields);
        List<Candidate> candidates = new ArrayList<>();
        try {
            while(rs.next()){
                candidates.add(new Candidate(rs));
            }
        } catch (SQLException e) {
            Logger.except(e.getMessage());
            return null;
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
    public static List<Candidate> search(String criteria, String...search_fields){
        ResultSet rs = Database.getInstance().selectSearch(Candidate.table,criteria, search_fields);
        List<Candidate> candidates = new ArrayList<>();
        try {
            while(rs.next()){
                candidates.add(new Candidate(rs));
            }
        } catch (SQLException e) {
            Logger.except(e.getMessage());
            return null;
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
        ResultSet rs = Database.getInstance().selectId(Candidate.table, id);
        try {
            if(rs.next()){
                return new Candidate(rs);
            }
            else{
                return null;
            }
        } catch (SQLException e) {

            Logger.except(e.getMessage());
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
        return new Migration("Candidate",table, "candidates.sql");
    }
}
