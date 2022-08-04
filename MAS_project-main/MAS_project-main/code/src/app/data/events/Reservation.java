package app.data.events;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Objects;

public class Reservation {
    public static ArrayList<Reservation> allReservations = new ArrayList<>();

    public int id, kortId, klientId, trenerId;
    public Date data;
    public Time czas_od, czas_do;
    public ReservationStatus status;

    public Reservation(int id, int kortId, int klientId, int trenerId, Date data, Time czas_od, Time czas_do, ReservationStatus status) {
        this.id = id;
        this.kortId = kortId;
        this.klientId = klientId;
        this.trenerId = trenerId;
        this.data = data;
        this.czas_od = czas_od;
        this.czas_do = czas_do;
        this.status = status;

        allReservations.add(this);
    }

    public boolean timeInBetween(String timeString) {
        Time time = Time.valueOf(timeString+":00");
        return time.getTime() >= czas_od.getTime() && time.getTime() < czas_do.getTime();
    }

    public static Reservation getReservationById(int id){
        for (Reservation res : allReservations) {
            if (res.id == id)
                return res;
        }
        return null;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", Kort_id=" + kortId +
                ", Klient_id=" + klientId +
                ", Trener_id=" + trenerId +
                ", data=" + data +
                ", czas_od=" + czas_od +
                ", czas_do=" + czas_do +
                ", status=" + status +
                '}';
    }

    public boolean collisionWith(Time from, Time to) {
        if(this.czas_do.getTime() > from.getTime())
            return true;
        return this.czas_od.getTime() < to.getTime();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reservation that = (Reservation) o;
        return id == that.id && kortId == that.kortId && klientId == that.klientId && trenerId == that.trenerId && Objects.equals(data, that.data) && Objects.equals(czas_od, that.czas_od) && Objects.equals(czas_do, that.czas_do) && status == that.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, kortId, klientId, trenerId, data, czas_od, czas_do, status);
    }
}
