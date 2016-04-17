package hrbuddy.Database.QueryBuilder.Predicates;

/**
 * Created by nboisvert on 2016-04-17.
 */
public class RawPredicate implements Predicable {
    protected String raw;

    public RawPredicate(String raw){
        this.raw = raw.replace("WHERE","");
    }

    public String toString(){
        return this.raw;
    }

    @Override
    public String getPredicateString() {return " WHERE "+this.toString();}

    @Override
    public boolean hasPredicate() {
        return !this.raw.equals("");
    }
}
