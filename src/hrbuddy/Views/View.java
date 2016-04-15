package hrbuddy.Views;

/**
 * Created by nboisvert on 13/04/16.
 */
public class View {
    protected static String view_path = "../Views";
    protected String name = "";
    protected String path = "";
    protected boolean multiple_record;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return View.view_path+'/'+path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public View(String name, String path){
        this.name = name;
        this.path = path;
        this.multiple_record = false;
    }

    public View(String name, String path, boolean multiple_record){
        this.name = name;
        this.path = path;
        this.multiple_record = multiple_record;
    }

    public static String getViewPath() {
        return view_path;
    }

    public boolean isMultipleRecord() {
        return multiple_record;
    }

    public void setMultipleRecord(boolean multiple_record) {
        this.multiple_record = multiple_record;
    }
}
