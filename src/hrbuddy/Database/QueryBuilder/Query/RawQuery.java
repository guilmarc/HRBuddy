package hrbuddy.Database.QueryBuilder.Query;

/**
 * Created by nboisvert on 2016-04-16.
 */
public class RawQuery implements Queryable {
    protected String query;

    public RawQuery(String query){
        this.query = query;
    }

    @Override
    public String getQuery() {
        return this.query;
    }
}
