package hrbuddy.Models;

import hrbuddy.Database.Database;
import hrbuddy.Utils.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nboisvert on 16-04-11.
 */


public class Candidate {
    protected static String[] search_fields = {"firstname","lastname","address","email","home_phone","cell_phone"};

    protected String firstname;
    protected String lastname;
    protected String address;
    protected String email;
    protected String home_phone;
    protected String cell_phone;

    public Candidate(String firstname,String lastname,String address,String email,String home_phone,String cell_phone){
        this.firstname = firstname;
        this.lastname = lastname;
        this.address = address;
        this.email = email;
        this.home_phone = home_phone;
        this.cell_phone = cell_phone;
    }

    public Candidate(ResultSet resultSet){
        try{
            this.firstname = resultSet.getString("firstname");
            this.lastname = resultSet.getString("lastname");
            this.address = resultSet.getString("address");
            this.email = resultSet.getString("email");
            this.home_phone = resultSet.getString("home_phone");
            this.cell_phone = resultSet.getString("cell_phone");
        }
        catch (Exception e){
            Logger.except(e.getMessage());
        }
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

    /**
     * Return every instances of the table
     *
     * @return List of all the Candidates
     */
    public static List<Candidate> all(){
        ResultSet rs = Database.getInstance().select("candidates");
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
        ResultSet rs = Database.getInstance().selectSearch("candidates",criteria, Candidate.search_fields);
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
        ResultSet rs = Database.getInstance().selectSearch("candidates",criteria, search_fields);
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

    public static Candidate find(int id){
        ResultSet rs = Database.getInstance().selectId("candidates", id);
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
}
