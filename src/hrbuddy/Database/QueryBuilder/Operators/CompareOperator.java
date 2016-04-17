package hrbuddy.Database.QueryBuilder.Operators;

/**
 * Created by nboisvert on 2016-04-17.
 */
public enum CompareOperator {
    EQUALS, NOT_EQUALS, LESS, LESS_EQUALS, GREATER, GREATER_EQUALS, LIKE;

    public String getOperator(){
        switch (this){
            case EQUALS:
                return "=";
            case NOT_EQUALS:
                return "!=";
            case LESS:
                return "<";
            case LESS_EQUALS:
                return "<=";
            case GREATER:
                return ">";
            case GREATER_EQUALS:
                return ">=";
            case LIKE:
                return "LIKE";
            default:
                return "";
        }
    }

    public static CompareOperator getCompareOperator(String operator){
        switch (operator){
            case "=":
                return CompareOperator.EQUALS ;
            case "!=":
                return CompareOperator.NOT_EQUALS ;
            case "<":
                return CompareOperator.LESS ;
            case "<=":
                return CompareOperator.LESS_EQUALS ;
            case ">":
                return CompareOperator.GREATER ;
            case ">=":
                return CompareOperator.GREATER_EQUALS ;
            case "LIKE":
                return CompareOperator.LIKE;
            default:
                return null;
        }

    }
}
