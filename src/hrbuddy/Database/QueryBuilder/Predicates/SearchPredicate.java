package hrbuddy.Database.QueryBuilder.Predicates;

import hrbuddy.Database.QueryBuilder.Operators.CompareOperator;
import hrbuddy.Database.QueryBuilder.Operators.LogicalOperator;
import hrbuddy.Utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nboisvert on 2016-04-17.
 */
public class SearchPredicate implements Predicable {
    protected String criteria;
    protected List<String> fields;
    protected CompareOperator compare_operator;
    protected LogicalOperator logical_operator;

    public SearchPredicate(String criteria, List<String> fields){
        this.criteria = criteria;
        this.fields = fields;
        this.compare_operator = CompareOperator.LIKE;
        this.logical_operator = LogicalOperator.OR;
    }

    public SearchPredicate(String criteria, String...fields){
        this.criteria = criteria;
        this.fields = new ArrayList<>();
        for(String field : fields){
            this.fields.add(field);
        }
        this.compare_operator = CompareOperator.LIKE;
        this.logical_operator = LogicalOperator.OR;
    }

    public SearchPredicate(String criteria, List<String> fields, CompareOperator compare_operator){
        this.criteria = criteria;
        this.fields = fields;
        this.compare_operator = compare_operator;
    }

    public List<String> asStringList(){
        List<String> stringed_predicates = new ArrayList<>();
        for(String field : this.fields){
            stringed_predicates.add(field+" "+this.compare_operator.toString()+" "+Utils.escapeForSql(criteria, ((this.compare_operator == CompareOperator.LIKE) ? "%" : "")));
        }
        return stringed_predicates;
    }

    public String toString(){
        return Utils.implode(" "+this.logical_operator+" ",this.asStringList());
    }

    @Override
    public String getPredicateString() {
        return " WHERE "+this.toString();
    }

    @Override
    public boolean hasPredicate() {
        return (this.fields.size() > 0);
    }

    //      GETTERS
    public String getCriteria() {
        return criteria;
    }
    public List<String> getFields() {
        return fields;
    }
    public CompareOperator getCompareOperator() {
        return compare_operator;
    }
    public LogicalOperator getLogicalOperator() {
        return logical_operator;
    }

    //      SETTERS
    public void setCriteria(String criteria) {
        this.criteria = criteria;
    }
    public void setFields(List<String> fields) {
        this.fields = fields;
    }
    public void setCompareOperator(CompareOperator compare_operator) {
        this.compare_operator = compare_operator;
    }
    public void setLogicalOperator(LogicalOperator logical_operator) {
        this.logical_operator = logical_operator;
    }
}
