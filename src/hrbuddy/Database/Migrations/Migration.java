package hrbuddy.Database.Migrations;

import hrbuddy.Database.Schema.Schema;
import hrbuddy.Models.*;
import hrbuddy.Utils.Logger;

import java.io.File;
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
    private Schema schema;
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

    public Schema getSchema() { return this.schema; }

    public void setSchema(Schema schema){ this.schema = schema; }

    public Migration(String name, Schema schema, String filename){
        this.name = name;
        this.schema = schema;
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

    public static Migration[] getAllMigrations(){
        Migration[] migrations = new Migration[5];
        migrations[0] = Candidate.getMigration();
        migrations[1] = Experience.getMigration();
        migrations[2] = Formation.getMigration();
        migrations[3] = Postulation.getMigration();
        migrations[4] = Gender.getMigration();
        return migrations;
    }
}
