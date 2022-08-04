package app.data.management;

import app.data.Court;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class ServiceWork {
    public static HashMap<ServiceWork, Court> serviceWorkCourtHashMap = new HashMap<>();

    int id;
    Date data_od, data_do;
    String opis;
    Contractor contractor;

    public ServiceWork(int id, Date data_od, Date data_do, String opis, Contractor contractor) {
        this.id = id;
        this.data_od = data_od;
        this.data_do = data_do;
        this.opis = opis;
        this.contractor = contractor;
    }

    public static ArrayList<ServiceWork> getServiceWorksForCourt(Court court){
        ArrayList<ServiceWork> result = new ArrayList<>();
        for (ServiceWork serviceWork : serviceWorkCourtHashMap.keySet()) {
            Court serviced_court = serviceWorkCourtHashMap.get(serviceWork);
            if(serviced_court.id == court.id)
                result.add(serviceWork);
        }
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ServiceWork that = (ServiceWork) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
