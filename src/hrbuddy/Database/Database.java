package hrbuddy.Database;

import hrbuddy.Controllers.DialogController;
import hrbuddy.Database.Drivers.*;
import hrbuddy.Database.Drivers.Driver;
import hrbuddy.Database.QueryBuilder.Predicates.FieldSet;
import hrbuddy.Database.QueryBuilder.Predicates.Predicable;
import hrbuddy.Database.QueryBuilder.Predicates.Predicate;
import hrbuddy.Database.QueryBuilder.Query.*;
import hrbuddy.Database.Migrations.Migration;
import hrbuddy.Exceptions.QueryWithoutPredicateException;
import hrbuddy.Utils.Logger;
import javafx.scene.control.Alert;
import sun.rmi.runtime.Log;

import javax.management.Query;
import java.io.File;
import java.sql.*;
import java.util.*;

/**
 * Created by nboisvert on 16-04-11.
 */
public class Database {
    /**
     * Instance of the singleton
     */
    private static Database ourInstance = new Database();

    /**
     * Getter for the Database singleton instance
     *
     * @return Database, the instance of the database
     */
    public static Database getInstance() {
        return ourInstance;
    }

    //  Connection driver
    private Driver driver = new SqliteDriver("HRBuddy.db");

    /**
     * Execute query that provide column
     *
     * @param query, String representing a SELECT * query. It is suggested to add LIMIT 1 to optimize speed
     * @return List of columns as string
     */
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

    /**
     * Execute a given Select Query, see QueryBuilder for documentation about building it
     *
     * @param query, SelectQuery object
     * @return List of Hashmap of String combo, rows fetched by the query. The key of the hashmap matches the column name
     */
    private List<HashMap<String,String>> execSelect(SelectQuery query){
        List<HashMap<String,String>> datas = new ArrayList<>();
        try {
            Connection connection = this.getConnection();
            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery(query.getQuery());
            datas = this.extractResultSet(results);
            connection.close();
        }
        catch (Exception e){
            Logger.except(e.getMessage());
        }
        return datas;
    }

    /**
     * Extract columns from a given ResultSet
     *
     * @param rs, ResulSet from a connection
     * @return  List of string matching the ResultSets columns
     * @throws SQLException
     */
    private List<String> extractResultSetColumns(ResultSet rs) throws SQLException {
        ResultSetMetaData md = rs.getMetaData();
        int columns = md.getColumnCount();
        List<String> list = new ArrayList<>();
        for(int i=1; i<=columns; ++i){
            list.add(md.getColumnName(i));
        }

        return list;
    }

    /**
     * Extract rows from a given ResultSet
     *
     * @param rs, ResultSet from a Connection
     * @return List of Hashmap of String combo, rows fetched by the query. The key of the hashmap matches the column name
     * @throws SQLException
     */
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

    /**
     * Execute a raw query given by a string
     *
     * NOTE : This method should be deleted and every call to it should be refactored to match a Queryable object
     *
     * @param query, String representing the Query
     * @return List of Integer, for modified/inserted keys. Does not return datas
     */
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

    /**
     * Execute a query as an update/insert in the database
     *
     * @param query, Queryable object
     * @return List of Integer, for modified/inserted keys. Does not return datas
     */
    private List<Integer> execUpdate(Queryable query){
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

    /**
     * Launch query execution
     *
     * @param query, Queryable object
     * @return List of Integer, for modified/inserted keys. Does not return datas
     */
    public List<Integer> execute(Queryable query) {
        return this.execUpdate(query);
    }

    /**
     * Select ResultSets columns
     *
     * @param table, given table for columns
     * @return List of String matching columns
     */
    public List<String> selectColumns(String table){
        String query = "SELECT * FROM "+table+" LIMIT 1";
        return this.execSelectColumns(query);
    }
    public List<HashMap<String,String>> select(SelectQuery query){
        return this.execSelect(query);
    }

    public String getConnectionString(){
        return this.getDriver().getConnectionString();
    }
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(this.getConnectionString());
    }
    public boolean databaseExists(){
        return this.getDriver().databaseExists();
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
            Class.forName(this.getDriver().getDriverClass());
            if(!this.databaseExists()){
                Alert alert = DialogController.noButton("Appuyez sur OK pour migrer la base de données");
                Logger.warning("Database doesn't exists");

                this.migrate(Migration.getAllMigrations());

                alert.close();
            }
        } catch (Exception e) {
            Logger.except(e.getMessage());
        }
    }
    public boolean getStatus() {
        return this.databaseExists();
    }
    public Driver getDriver(){ return this.driver; }
    public void setDriver(Driver driver){ this.driver = driver; }
}
