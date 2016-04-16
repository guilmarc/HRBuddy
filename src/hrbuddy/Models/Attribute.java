package hrbuddy.Models;

/**
 * Created by Nicolas Boisvert on 2016-04-16.
 */
public class Attribute{
    protected String old_value;
    protected String new_value;

    public Attribute(String value){
        this.old_value = value;
        this.new_value = value;
    }

    public void rollback(){
        this.new_value = old_value;
    }

    public void apply(){
        this.old_value = new_value;
    }

    public boolean hasChange(){
        return this.new_value.equals(this.old_value);
    }

    public boolean sameAs(String value){
        return this.new_value.equals(value);
    }

    public boolean sameAsOld(String value){
        return this.old_value.equals(value);
    }

    public String getOldValue() {
        return old_value;
    }

    public String getValue() {
        return new_value;
    }

    public void changeTo(String new_value) {
        this.new_value = new_value;
    }

    public String toString(){
        return this.new_value;
    }
}
