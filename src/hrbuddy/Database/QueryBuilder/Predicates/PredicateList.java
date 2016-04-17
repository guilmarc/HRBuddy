package hrbuddy.Database.QueryBuilder.Predicates;

import hrbuddy.Database.QueryBuilder.Operators.LogicalOperator;
import hrbuddy.Utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nboisvert on 2016-04-17.
 */
public class PredicateList implements Predicable{
    private static LogicalOperator default_operator = LogicalOperator.OR;
    protected List<Predicate> predicates;
    protected LogicalOperator operator;

    public PredicateList(){
        this.predicates = new ArrayList<>();
        this.operator = PredicateList.getDefaultOperator();
    }

    public PredicateList(LogicalOperator operator){
        this.predicates = new ArrayList<>();
        this.operator = operator;
    }

    public PredicateList(Predicate predicate){
        this.predicates = new ArrayList<>();
        this.predicates.add(predicate);
        this.operator = PredicateList.getDefaultOperator();
    }

    public List<String> asStringList(){
        List<String> stringed_predicates = new ArrayList<>();
        for(Predicate predicate : this.predicates){
            stringed_predicates.add(predicate.toString());
        }
        return stringed_predicates;
    }

    public String toString(){
        if(this.predicates.size() > 0) {
            return Utils.implode(" "+this.operator.toString()+" ", this.asStringList());
        }
        return "";
    }

    //      GETTERS
    public List<Predicate> getPredicates() {return predicates;}
    public LogicalOperator getOperator(){ return this.operator; }

    //      SETTERS
    public void setPredicates(List<Predicate> predicates) {this.predicates = predicates;}
    public void setOperator(LogicalOperator operator){ this.operator = operator; }

    @Override
    public String getPredicateString() {return " WHERE "+this.toString();}
    @Override
    public boolean hasPredicate() {return (this.predicates.size() > 0);}

    public static LogicalOperator getDefaultOperator(){ return PredicateList.default_operator; }
    public static void setDefaultOperator(LogicalOperator operator){ PredicateList.default_operator = operator;}
}
