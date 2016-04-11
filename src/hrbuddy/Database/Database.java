package hrbuddy.Database;

import java.sql.*;

/**
 * Created by nboisvert on 16-04-11.
 */
public class Database {
    private static Database ourInstance = new Database();

    public static Database getInstance() {
        return ourInstance;
    }

    private String connection_driver = "jdbc:sqlite:datatidge.db";

    private Connection connection = null;

    public Connection getConnection() {
        try {
            this.connection = DriverManager.getConnection(connection_driver);
        } catch (SQLException ex) {
            this.connection = null;
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
            System.out.println(e.getMessage());
            return null;
        }
    }

    private Database() {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
