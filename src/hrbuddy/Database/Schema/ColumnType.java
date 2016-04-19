package hrbuddy.Database.Schema;

/**
 * Created by nboisvert on 18/04/16.
 */
public enum ColumnType {
    STRING("varchar",255), INT("int",11), DECIMAL("decimal",16,2), DATE("date"), DATETIME("datetime"), TEXT("text"), INTEGER("INTEGER");

    String name;
    int size;
    int floating;
    boolean has_size;
    boolean has_floating;

    ColumnType(String name){
        this.name = name;
        this.size = -1;
        this.floating = -1;
        this.has_size = false;
        this.has_floating = false;
    }

    ColumnType(String name, int size){
        this.name = name;
        this.size = size;
        this.floating = -1;
        this.has_size = true;
        this.has_floating = false;
    }
    ColumnType(String name, int size, int floating){
        this.name = name;
        this.size = size;
        this.floating = floating;
        this.has_size = true;
        this.has_floating = true;
    }

    @Override
    public String toString() {
        return name+((has_size) ? "("+size+((has_floating) ? ","+floating+")" : ")") : "");
    }
}
