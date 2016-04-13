package hrbuddy.Utils;

import java.util.List;

/**
 * Created by nboisvert on 2016-04-12.
 */
public class Logger {
    public static void log(String title, String message){
        System.out.println(title+" : "+message);
    }
    public static void boxed(String...messages){
        System.out.println("/**********************************************************");
        for( int i = 0; i < messages.length; i++) {
            System.out.println(messages[i]);
        }
        System.out.println("/**********************************************************");
    }
    public static void boxed(List<String> messages){
        System.out.println("/**********************************************************");
        for( int i = 0; i < messages.size(); i++) {
            System.out.println(messages.get(i));
        }
        System.out.println("/**********************************************************");
    }
    public static void except(String message){
        Logger.log("Exception", message);
    }
    public static void init(String message){
        Logger.log("Initialized", message);
    }
    public static void debug(String message){
        Logger.log("Debug", message);
    }
    public static void migrate(String message){
        Logger.log("Migrating", message);
    }
    public static void warning(String message){
        Logger.log("Warning", message);
    }
    public static void danger(String message){
        Logger.log("Danger", message);
    }
}
