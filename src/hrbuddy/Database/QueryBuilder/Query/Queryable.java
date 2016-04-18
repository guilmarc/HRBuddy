package hrbuddy.Database.QueryBuilder.Query;

import hrbuddy.Exceptions.QueryWithoutPredicateException;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by nboisvert on 2016-04-12.
 */
public interface Queryable {
    public String getQuery() throws QueryWithoutPredicateException;
    public boolean asResultSet();
}
