package app.data.person;

import java.sql.Date;

public class Administrator extends Employee{
    String zakres;

    public Administrator(int id, String imie, String nazwisko, Date data_ur, Date data_zatr, int numer, double pensja, String zakres) {
        super(id, imie, nazwisko, data_ur, data_zatr, numer, pensja);
        this.zakres = zakres;
    }

    @Override
    String getName() {
        return imie + " " + nazwisko + " - ADMINISTRACJA";
    }
}
