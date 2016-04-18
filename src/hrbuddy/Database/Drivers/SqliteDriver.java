package hrbuddy.Database.Drivers;

import java.io.File;

/**
 * Created by nboisvert on 17/04/16.
 */
public class SqliteDriver implements Driver{
    protected String file;
    protected String protocol = "jdbc:sqlite";
    protected String driver_class = "org.sqlite.JDBC";

    public SqliteDriver(String file){
        this.file = file;
    }

    @Override
    public String getConnectionString() {
        return this.getProtocol()+":"+this.file;
    }

    public String getFile() {
        return file;
    }

    public String getProtocol(){
        return this.protocol;
    }

    @Override
    public boolean databaseExists() {
        File file = new File (this.file);
        if(!file.exists()){
            return false;
        }
        return true;
    }

    public String getDriverClass() {
        return driver_class;
    }

    public void setFile(String file) {
        this.file = file;
    }
}
