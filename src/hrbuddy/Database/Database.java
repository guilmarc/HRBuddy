package hrbuddy.Database;

import hrbuddy.Database.QueryBuilder.Query;
import hrbuddy.Models.Candidate;
import hrbuddy.Utils.Migration;
import hrbuddy.Utils.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by nboisvert on 16-04-11.
 */
public class Database {
    private static Database ourInstance = new Database();
    public static Database getInstance() {
        return ourInstance;
    }

    private String connection_driver = "jdbc:sqlite";
    private String database_file = "HRBuddy.db";
    private int last_inserted_id = -1;
    private Connection connection = null;

    public String getConnectionString(){
        return this.connection_driver+":"+this.database_file;
    }

    public int getLastInsertedId(){
        return this.last_inserted_id;
    }

    public Connection getConnection() {
        try {
            Class.forName("org.sqlite.JDBC");
            this.connection = DriverManager.getConnection(this.getConnectionString());
        } catch (SQLException ex) {
            this.connection = null;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return this.connection;
    }

    private ResultSet execSelect(String query){
        ResultSet rs = null;
        connection = this.getConnection();
        try {
            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery(query);
            rs =  results;
        }
        catch (Exception e){
            Logger.except(e.getMessage());
        }
        finally {
            try {
                connection.close();
            } catch (SQLException e) {}
        }

        return rs;
    }
    private ResultSet execUpdate(String query){
        ResultSet rs = null;
        connection = this.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.executeUpdate();
            statement.close();
            this.getConnection().close();
            rs = statement.getGeneratedKeys();
        }
        catch (Exception e){
            Logger.except(e.getMessage());
        }
        finally {
            try {
                this.getConnection().close();
            } catch (SQLException e) {}
        }
        return rs;
    }
    private ResultSet execUpdate(Query query){
        ResultSet rs = null;
        connection = this.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(query.getQuery(), Statement.RETURN_GENERATED_KEYS);
            statement.executeUpdate();
            this.getConnection().close();
            rs = statement.getGeneratedKeys();
        }
        catch (Exception e){
            Logger.except(e.getMessage());
        }
        finally {
            try {
                this.getConnection().close();
            } catch (SQLException e) {}
        }
        return rs;
    }

    public ResultSet select(String table){
        return this.execSelect("SELECT * FROM "+table);
    }

    public ResultSet selectId(String table, int id){
        return this.execSelect("SELECT * FROM "+table+" WHERE id = "+id);
    }
    public ResultSet selectSearch(String table, String criteria, String...fields){
        return this.execSelect("SELECT * FROM "+table+this.likeQuery(criteria,fields));
    }

    public ResultSet rawSelect(String query){
        return this.execSelect(query);
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

    public boolean databaseExists(){
        File file = new File (this.database_file);
        if(!file.exists()){
            return false;
        }
        return true;
    }

    public void migrate(Migration migration){
        this.execUpdate(migration.getTableCreation());
        Logger.migrate(migration.getName()+" table created");
        for(int i = 0; i < migration.getInsertRows().size(); i++){
            this.execUpdate(migration.getInsertRows().get(i));
        }
        Logger.migrate(migration.getName()+" "+ migration.getInsertRows().size()+" rows");
    }

    private Database() {
        try {
            if(!this.databaseExists()){
                Logger.warning("Database doesn't exists");
                this.migrate(Candidate.getMigration());
            }
            this.getConnection().close();
            Class.forName("org.sqlite.JDBC");
        } catch (Exception e) {
            Logger.except(e.getMessage());
        }
    }

    public boolean getStatus() {
        return this.databaseExists();
    }

    public ResultSet execute(Query query) {
        return this.execUpdate(query);
    }
}
