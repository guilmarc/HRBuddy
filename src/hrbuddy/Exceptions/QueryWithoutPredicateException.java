package hrbuddy.Exceptions;

/**
 * Created by nboisvert on 2016-04-17.
 */
public class QueryWithoutPredicateException extends Exception{
    public QueryWithoutPredicateException(String message){
        super(message);
    }
}
