package hrbuddy.Models;

import hrbuddy.Database.Database;

import java.sql.ResultSet;

/**
 * Created by nboisvert on 16-04-11.
 */


public class Candidate extends Model { static { table = "candidates"; }

    //On ne doit pas redéclarer mais réinitialiser (voir code ci-haut)
    //protected static String table = "Candidate";

    //PATCH TEMPORAIRE : À retirer quand on aura compris pourquoi la variable statique n'est pas prise en compte.
    public static ResultSet all(){
        return Database.getInstance().select("candidates");
    }

    protected String firstname;
    protected String lastname;
    protected String address;
    protected String email;
    protected String home_phone;
    protected String cell_phone;

    public String getCellPhone() {
        return cell_phone;
    }

    public void setCellPhone(String cell_phone) {
        this.cell_phone = cell_phone;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHomePhone() {
        return home_phone;
    }

    public void setHomePhone(String home_phone) {
        this.home_phone = home_phone;
    }
}
