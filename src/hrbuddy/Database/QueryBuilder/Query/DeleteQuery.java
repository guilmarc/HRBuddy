package hrbuddy.Database.QueryBuilder.Query;

import hrbuddy.Database.QueryBuilder.Predicates.Predicable;
import hrbuddy.Exceptions.QueryWithoutPredicateException;

/**
 * Created by nboisvert on 2016-04-17.
 */
public class DeleteQuery implements Queryable {
    protected String table;
    protected Predicable predicates;
    protected boolean safety = true;

    public DeleteQuery(String table, Predicable predicates){
        this.table = table;
        this.predicates = predicates;
    }

    @Override
    public String getQuery() throws QueryWithoutPredicateException {
        if(!this.predicates.hasPredicate() && !this.isSafety()){
            throw new QueryWithoutPredicateException("Delete query would delete any data in the database");
        }
        return "DELETE FROM "+this.getTable()+" "+this.getPredicates().getPredicateString();
    }

    @Override
    public boolean asResultSet() {
        return false;
    }

    //      GETTERS
    public String getTable() {
        return table;
    }
    public Predicable getPredicates() {
        return predicates;
    }
    public boolean isSafety() {
        return safety;
    }

    //      SETTERS
    public void setTable(String table) {
        this.table = table;
    }
    public void setPredicates(Predicable predicates) {
        this.predicates = predicates;
    }
    public void setSafety(boolean safety) {
        this.safety = safety;
    }
}
