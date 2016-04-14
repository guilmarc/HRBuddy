package hrbuddy.Views;

/**
 * Created by nboisvert on 13/04/16.
 */
public class View {
    protected String name = "";
    protected String path = "";

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public View(String name, String path){
        this.name = name;
        this.path = path;
    }


}
