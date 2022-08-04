package app.data.person;

import java.sql.Date;

public class Accountant extends Employee {
    String klienci;
    private boolean dostep = false;

    public Accountant(int id, String imie, String nazwisko, Date data_ur, Date data_zatr, int numer, double pensja, String klienci) {
        super(id, imie, nazwisko, data_ur, data_zatr, numer, pensja);
        this.klienci = klienci;
    }

    public void setDostep(boolean dostep) {
        this.dostep = dostep;
    }

    public boolean getDostep() {
        return dostep;
    }

    @Override
    String getName() {
        return imie + " " + nazwisko + " - KSIĘGOWOŚĆ";
    }
}
