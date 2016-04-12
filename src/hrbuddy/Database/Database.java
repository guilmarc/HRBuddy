package hrbuddy.Database;

import hrbuddy.Utils.Logger;
import javafx.collections.ObservableList;

import java.sql.*;

/**
 * Created by nboisvert on 16-04-11.
 */
public class Database {
    private static Database ourInstance = new Database();

    public static Database getInstance() {
        return ourInstance;
    }

    private String connection_driver = "jdbc:sqlite:HRBuddy.db";

    private Connection connection = null;

    public Connection getConnection() {
        try {
            Class.forName("org.sqlite.JDBC");
            this.connection = DriverManager.getConnection(connection_driver);
        } catch (SQLException ex) {
            this.connection = null;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return this.connection;
    }

    public ResultSet select(String table){
        try {
            Statement statement = this.getConnection().createStatement();
            ResultSet results = statement.executeQuery("SELECT * FROM "+table);

            return results;
        }
        catch (Exception e){
            Logger.except(e.getMessage());
            return null;
        }
    }

    public ResultSet selectId(String table, int id){
        try {
            Statement statement = this.getConnection().createStatement();
            ResultSet results = statement.executeQuery("SELECT * FROM "+table+" WHERE id = "+id);

            return results;
        }
        catch (Exception e){
            Logger.except(e.getMessage());
            return null;
        }
    }
    public ResultSet selectSearch(String table, String criteria, String...fields){
        try {
            Statement statement = this.getConnection().createStatement();
            ResultSet results = statement.executeQuery("SELECT * FROM "+table+this.likeQuery(criteria,fields));

            return results;
        }
        catch (Exception e){
            Logger.except(e.getMessage());
            return null;
        }
    }

    public ResultSet rawSelect(String query){
        try {
            Statement statement = this.getConnection().createStatement();
            ResultSet results = statement.executeQuery(query);

            return results;
        }
        catch (Exception e){
            Logger.except(e.getMessage());
            return null;
        }

    }

    private String likeQuery(String criteria, String...fields){
        String output = " WHERE ";
        criteria = "'%"+criteria+"%'";
        for (int i = 0; i < fields.length; i++){
            output += fields[i]+" LIKE "+criteria;
            if(i < (fields.length-1)){
                output += " OR ";
            }
        }
        return output;
    }

    private Database() {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (Exception e) {
            Logger.except(e.getMessage());
        }
    }
}
