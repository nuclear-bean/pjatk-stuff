package app.data.events;

import app.data.Company;

import java.util.ArrayList;

public class Event {
    public static ArrayList<Event> wydarzenia = new ArrayList<>();

    public ArrayList<Reservation> reservations = new ArrayList<>();

    public int id;
    public Company company;
    public String name;
    public int participants;
    public boolean buffet = false;

    public static ArrayList<Event> getEventsForReservation(Reservation reservation){
        ArrayList<Event> result = new ArrayList<>();
        for (Event event : wydarzenia) {
            if(event.reservations.contains(reservation))
                result.add(event);
        }
        return result;
    }
}
