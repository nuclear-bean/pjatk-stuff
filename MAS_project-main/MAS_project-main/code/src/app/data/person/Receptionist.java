package app.data.person;

import java.sql.Date;

public class Receptionist extends Employee{
    public Receptionist(int id, String imie, String nazwisko, Date data_ur, Date data_zatr, int numer, double pensja) {
        super(id, imie, nazwisko, data_ur, data_zatr, numer, pensja);
    }

    @Override
    String getName() {
        return imie + " " + nazwisko + " - RECEPCJA";
    }
}
