package hrbuddy.Exceptions;

/**
 * Created by nboisvert on 2016-04-16.
 */
public class AttributeNotFoundException extends Exception {
    public AttributeNotFoundException(String message){
        super(message+" This is a safety prevention for database integrity, can be disabled by turning safety attribute to false");
    }
}
