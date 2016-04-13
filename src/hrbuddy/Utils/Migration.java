package hrbuddy.Utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by nboisvert on 2016-04-12.
 */
public class Migration {
    private static String migration_folder = "./src/hrbuddy/Database/Migrations/";

    private String name ="";
    private String table_creation = "";
    private List<String> insert_rows;

    public String getName(){
        return this.name;
    }

    public void setName(String name){
        this.name = name;
    }

    public List<String> getInsertRows() {
        return insert_rows;
    }

    public void setInsertRows(List<String> insert_rows) {
        this.insert_rows = insert_rows;
    }

    public String getTableCreation() {
        return table_creation;
    }

    public void setTableCreation(String table_creation) {
        this.table_creation = table_creation;
    }

    public Migration(String name, String table_creation, String filename){
        this.name = name;
        this.table_creation = table_creation;
        Scanner scanner = null;
        this.insert_rows = new ArrayList<String>();
        try {
            scanner = new Scanner(new File(Migration.getMigrationPath(filename)));
            while (scanner.hasNextLine()){
                this.insert_rows.add(scanner.nextLine());
            }
            scanner.close();
        } catch (Exception e) {
            this.insert_rows = new ArrayList<String>();
            Logger.debug(e.getMessage());
        }
    }



    public static String getMigrationPath(String filename){
        return Migration.migration_folder+filename;
    }

    public static String getMigrationFolder() {
        return migration_folder;
    }

    public static void setMigrationFolder(String migration_folder) {
        Migration.migration_folder = migration_folder;
    }
}
