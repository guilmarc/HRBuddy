package hrbuddy.Database.QueryBuilder.Operators;

/**
 * Created by nboisvert on 2016-04-17.
 */
public enum LogicalOperator {
    AND, OR;

    public String toString(){
        switch (this){
            case AND:
                return "AND";
            case OR:
                return "OR";
            default:
                return "";
        }
    }

    public static LogicalOperator getOperator(String operator){
        switch (operator.toUpperCase()){
            case "AND":
                return LogicalOperator.AND;
            case "OR":
                return LogicalOperator.OR;
            default:
                return null;
        }
    }
}
