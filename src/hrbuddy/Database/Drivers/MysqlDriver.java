package hrbuddy.Database.Drivers;

import sun.nio.cs.ext.MS874;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Created by nboisvert on 17/04/16.
 */
public class MysqlDriver implements Driver{
    protected static String increment_statement = "AUTO_INCREMENT";
    protected String protocol = "jdbc:mysql";
    protected String driver_class = "com.mysql.jdbc.Driver";
    protected int port;
    protected String database;
    protected String user;
    protected String password;
    protected String host;

    public MysqlDriver(String host, String database, String user, String password){
        this.host = host;
        this.database = database;
        this.user = user;
        this.password = password;
        this.port = 3306;
    }

    public MysqlDriver(String host, String database, String user, String password, int port){
        this.host = host;
        this.database = database;
        this.user = user;
        this.password = password;
        this.port = port;
    }

    //      GETTERS
    public int getPort() {
        return port;
    }
    public String getDatabase() {
        return database;
    }
    public String getUser() {
        return user;
    }
    public String getPassword() {
        return password;
    }
    public String getHost() {
        return host;
    }

    //      SETTERS
    public void setPort(int port) {
        this.port = port;
    }
    public void setDatabase(String database) {
        this.database = database;
    }
    public void setUser(String user) {
        this.user = user;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setHost(String host) {
        this.host = host;
    }

    @Override
    public String getConnectionString() {
        return this.getProtocol()+"://"+this.getHost()+":"+String.valueOf(this.getPort())+"/"+this.getDatabase();
    }

    @Override
    public String getDriverClass() {
        return this.driver_class;
    }

    @Override
    public String getProtocol() {
        return this.protocol;
    }

    @Override
    public boolean databaseExists() {
        Connection connection = null;
        try{
            connection = DriverManager.getConnection(this.getConnectionString());
            return true;
        }
        catch (Exception ex){
            return false;
        }
    }

    @Override
    public String getIncrementStatement() {
        return MysqlDriver.increment_statement;
    }
}
