package hrbuddy.Models;

import hrbuddy.Database.Database;

import java.sql.ResultSet;

/**
 * Created by nboisvert on 16-04-11.
 */
public class Model {
    protected static String table;

    public static ResultSet all(){
        return Database.getInstance().select(Model.table);
    }
}
