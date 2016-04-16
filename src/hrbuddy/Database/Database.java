package hrbuddy.Database;

import hrbuddy.Controllers.DialogController;
import hrbuddy.Database.QueryBuilder.Query;
import hrbuddy.Models.Candidate;
import hrbuddy.Models.Experience;
import hrbuddy.Utils.Migration;
import hrbuddy.Utils.Logger;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.*;

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

    public String getConnectionString(){
        return this.connection_driver+":"+this.database_file;
    }

    public int getLastInsertedId(){
        return this.last_inserted_id;
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(this.getConnectionString());
    }

    private List<String> execSelectColumns(String query){
        List<String> datas = new ArrayList<>();
        try {
            Connection connection = this.getConnection();
            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery(query);
            datas = this.extractResultSetColumns(results);
            connection.close();
        }
        catch (Exception e){
            Logger.except(e.getMessage());
        }
        return datas;
    }

    private List<HashMap<String,String>> execSelect(String query){
        List<HashMap<String,String>> datas = new ArrayList<>();
        try {
            Connection connection = this.getConnection();
            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery(query);
            datas = this.extractResultSet(results);
            connection.close();
        }
        catch (Exception e){
            Logger.except(e.getMessage());
        }
        return datas;
    }
    private List<String> extractResultSetColumns(ResultSet rs) throws SQLException {
        ResultSetMetaData md = rs.getMetaData();
        int columns = md.getColumnCount();
        List<String> list = new ArrayList<>();
        for(int i=1; i<=columns; ++i){
            list.add(md.getColumnName(i));
        }

        return list;
    }
    private List<HashMap<String,String>> extractResultSet(ResultSet rs) throws SQLException {
        ResultSetMetaData md = rs.getMetaData();
        int columns = md.getColumnCount();
        List<HashMap<String,String>> list = new ArrayList<>();
        while (rs.next()){
            HashMap row = new HashMap(columns);
            for(int i=1; i<=columns; ++i){
                row.put(md.getColumnName(i),rs.getString(i));
            }
            list.add(row);
        }

        return list;
    }
    private List<Integer> execUpdate(String query){
        List<Integer> keys = new ArrayList<>();
        try {
            Connection connection = this.getConnection();
            PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.executeUpdate();
            ResultSet rs = statement.getGeneratedKeys();
            while (!rs.next()){
                keys.add(rs.getInt(1));
            }
            rs.close();
            statement.close();
            connection.close();
        }
        catch (Exception e){
            Logger.except(e.getMessage());
        }
        return keys;
    }
    private List<Integer> execUpdate(Query query){
        List<Integer> keys = new ArrayList<>();
        try {
            Connection connection = this.getConnection();
            PreparedStatement statement = connection.prepareStatement(query.getQuery(), Statement.RETURN_GENERATED_KEYS);
            statement.executeUpdate();
            ResultSet rs = statement.getGeneratedKeys();
            while (rs.next()){
                keys.add(rs.getInt(1));
            }
            rs.close();
            statement.close();
            connection.close();
        }
        catch (Exception e){
            Logger.except(e.getMessage());
        }
        return keys;
    }

    public List selectColumns(String table){
        String query = "SELECT * FROM "+table+" LIMIT 1";
        return this.execSelectColumns(query);
    }

    public List select(String table){
        return this.execSelect("SELECT * FROM "+table);
    }

    public List selectId(String table, int id, String key){
        return this.execSelect("SELECT * FROM "+table+" WHERE "+key+" = "+id);
    }
    public List selectId(String table, int id){
        return this.execSelect("SELECT * FROM "+table+" WHERE id = "+id);
    }
    public List selectSearch(String table, String criteria, String...fields){
        return this.execSelect("SELECT * FROM "+table+this.likeQuery(criteria,fields));
    }
    public List selectSearch(String table, String criteria, List<String> fields){
        return this.execSelect("SELECT * FROM "+table+this.likeQuery(criteria,fields));
    }
    public List rawSelect(String query){
        return this.execSelect(query);
    }

    public String likeQuery(String criteria, String...fields){
        String output = " WHERE ";
        criteria = criteria.replace("\'","\'\'");
        criteria = "'%"+criteria+"%'";
        for (int i = 0; i < fields.length; i++){
            output += fields[i]+" LIKE "+criteria;
            if(i < (fields.length-1)){
                output += " OR ";
            }
        }
        return output;
    }

    public String likeQuery(String criteria, List<String> fields){
        String output = " WHERE ";
        criteria = criteria.replace("\'","\'\'");
        criteria = "'%"+criteria+"%'";
        for (int i = 0; i < fields.size(); i++){
            output += fields.get(i)+" LIKE "+criteria;
            if(i < (fields.size()-1)){
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

    public void migrate(Migration...migration){
        for(int i = 0; i < migration.length; i++) {
            this.execUpdate(migration[i].getTableCreation());
            Logger.migrate(migration[i].getName() + " table created");
            for (int j = 0; j < migration[i].getInsertRows().size(); j++) {
                this.execUpdate(migration[i].getInsertRows().get(j));
            }
            Logger.migrate(migration[i].getName() + " " + migration[i].getInsertRows().size() + " rows");
        }
    }

    private Database() {
        try {
            Class.forName("org.sqlite.JDBC");
            if(!this.databaseExists()){
                Alert alert = DialogController.noButton("Appuyez sur OK pour migrer la base de données");
                Logger.warning("Database doesn't exists");

                this.migrate(Migration.getAllMigrations());

                alert.close();
            }
            //Class.forName("org.sqlite.JDBC");
        } catch (Exception e) {
            Logger.except(e.getMessage());
        }
    }

    public boolean getStatus() {
        return this.databaseExists();
    }

    public List<Integer> execute(Query query) {
        return this.execUpdate(query);
    }
}
