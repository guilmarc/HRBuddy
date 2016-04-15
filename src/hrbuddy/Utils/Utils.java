package hrbuddy.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nboisvert on 2016-04-12.
 */
public class Utils {
    public static String implode(String glue, String...items){
        String output = "";
        for( int i = 0; i < items.length; i++){
            output += items[i];
            if(i != (items.length-1)){
                output += glue;
            }
        }
        return output;
    }
    public static String implode(String glue, List<String> items){
        String output = "";
        for( int i = 0; i < items.size(); i++){
            output += items.get(i);
            if(i != (items.size()-1)){
                output += glue;
            }
        }
        return output;
    }
    public static List<String> explode(char limiter, String string){
        List<String> list = new ArrayList<String>();
        String temp = "";
        for( int i = 0; i < string.length(); i++){
            char car = string.charAt(i);
            if(car == limiter){
                if(temp.length() > 0){
                    list.add(temp.trim());
                }
                temp = "";
            }
            else{
                temp += car;
            }
        }
        if(temp.length() > 0){
            list.add(temp.trim());
        }
        return list;
    }
    public static String escapeForSql(String string){
        return "\'"+string.replaceAll("\'","''")+"\'";
    }
    public static String escapeForSql(String string, String borders){
        return "\'"+borders+string.replaceAll("\'","''")+borders+"\'";
    }
}
