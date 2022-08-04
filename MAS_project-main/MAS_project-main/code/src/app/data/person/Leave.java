package app.data.person;

import java.sql.Date;
import java.util.ArrayList;

public class Leave {
    public static ArrayList<Leave> allLeaves = new ArrayList<>();

    public int id;
    public String type;
    public Date data_od;
    public Date data_do;
    Employee pracownik;

    public Leave(int id, String type, Date data_od, Date data_do) {
        this.id = id;
        this.type = type;
        this.data_od = data_od;
        this.data_do = data_do;
    }

    public void cancel(){
        this.data_od = null;
        this.data_do = null;
        this.type = "ANULOWANO " + this.type;
    }

    public static ArrayList<Leave> getLeavesOfEmployee(Employee employee){
        ArrayList<Leave> result = new ArrayList<>();
        for (Leave leave : allLeaves) {
            if(leave.pracownik.equals(employee))
                result.add(leave);
        }
        return result;
    }
}
