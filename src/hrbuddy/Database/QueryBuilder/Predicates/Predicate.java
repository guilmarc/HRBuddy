package hrbuddy.Database.QueryBuilder.Predicates;

import hrbuddy.Database.QueryBuilder.Operators.CompareOperator;
import hrbuddy.Database.QueryBuilder.Operators.LogicalOperator;
import hrbuddy.Utils.Utils;

/**
 * Created by nboisvert on 2016-04-17.
 */
public class Predicate implements Predicable{
    protected String field;
    protected String criteria;
    protected CompareOperator operator;

    public Predicate(String field, String criteria){
        this.field = field;
        this.operator = CompareOperator.EQUALS;
        this.criteria = criteria;
    }

    public Predicate(String field, String operator, String criteria){
        this.field = field;
        this.operator = CompareOperator.getCompareOperator(operator);
        this.criteria = criteria;
    }

    public Predicate(String field, CompareOperator operator, String criteria){
        this.field = field;
        this.operator = operator;
        this.criteria = criteria;
    }

    public boolean isValid(){
        return !this.field.equals("") && !this.criteria.equals("") && this.operator != null;
    }

    public String toString(){
        String output = this.field+" "+this.operator.getOperator()+" ";
        output += Utils.escapeForSql(this.criteria, ((this.operator == CompareOperator.LIKE) ? "%" : ""));
        return output;
    }

    @Override
    public String getPredicateString() {return " WHERE "+this.toString();}

    @Override
    public boolean hasPredicate() {
        return !this.toString().equals("");
    }
}
