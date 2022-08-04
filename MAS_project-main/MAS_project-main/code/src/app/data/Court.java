package app.data;

import java.util.ArrayList;
import java.util.Objects;

public class Court {
    public static ArrayList<Court> allCourts = new ArrayList<>();

    public int id;
    public int cena;
    public String nawierzchnia;
    public boolean oswietlenie,kryty;

    public Court(int id, int cena, String nawierzchnia, boolean oswietlenie, boolean kryty) {
        this.id = id;
        this.cena = cena;
        this.nawierzchnia = nawierzchnia;
        this.oswietlenie = oswietlenie;
        this.kryty = kryty;

        allCourts.add(this);
    }

    public static Court getCourtById(int id){
        for (Court court : allCourts) {
            if(court.id == id)
                return court;
        }
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Court court = (Court) o;
        return id == court.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
