package hrbuddy.Database.Drivers;

/**
 * Created by nboisvert on 17/04/16.
 */
public interface Driver {
    public String getConnectionString();
    public String getDriverClass();
    public String getProtocol();
    public boolean databaseExists();
}
