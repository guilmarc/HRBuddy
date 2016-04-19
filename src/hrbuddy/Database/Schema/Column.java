package hrbuddy.Database.Schema;

import hrbuddy.Database.Database;

/**
 * Created by nboisvert on 18/04/16.
 */
public class Column {
    protected ColumnType type;
    protected String name;
    protected boolean nullable;
    protected String default_value;
    protected boolean has_default;
    protected boolean auto_increment;
    protected String pk_text = "";

    public Column(ColumnType type, String name){
        this.type = type;
        this.name = name;
        this.nullable = true;
        this.default_value = "";
        this.has_default = false;
        this.auto_increment = false;
    }

    public Column(ColumnType type, String name, boolean nullable){
        this.type = type;
        this.name = name;
        this.nullable = nullable;
        this.default_value = "";
        this.has_default = false;
        this.auto_increment = false;
    }

    public Column(ColumnType type, String name, boolean nullable, String default_value){
        this.type = type;
        this.name = name;
        this.nullable = nullable;
        this.default_value = default_value;
        this.has_default = true;
        this.auto_increment = false;
    }

    @Override
    public String toString() {
        String type = " "+this.type.toString()+" ";
        String nullable = ((!this.nullable) ? " NOT NULL ":"");
        String has_default = ((this.has_default) ? " DEFAULT "+this.default_value+" " : "");
        String auto_inc = ((this.auto_increment) ? " "+pk_text+" "+Database.getInstance().getDriver().getIncrementStatement()+" " : "");
        return this.name+type+nullable+has_default+auto_inc;
    }

    public void isPK(){ this.pk_text = "PRIMARY KEY"; }

    //      GETTERS
    public ColumnType getType() {
        return type;
    }
    public String getName() {
        return name;
    }
    public boolean isNullable() {
        return nullable;
    }
    public String getDefaultValue() {
        return default_value;
    }
    public boolean hasDefault() {
        return has_default;
    }
    public boolean isAutoIncrement() { return auto_increment; }

    //      SETTERS
    public void setType(ColumnType type) {
        this.type = type;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setNullable(boolean nullable) {
        this.nullable = nullable;
    }
    public void setHasDefault(boolean has_default) {
        this.has_default = has_default;
    }
    public void setDefaultValue(String default_value) {
        this.default_value = default_value;
    }
    public void setAutoIncrement(boolean auto_increment) { this.auto_increment = auto_increment; }
}
