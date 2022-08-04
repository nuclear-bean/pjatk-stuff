package app.data.person;

import java.sql.Date;

public abstract class Person {
    public final int id;

    public String imie,nazwisko;
    public Date data_ur;
    private int numer;

    protected Person(int id, String imie, String nazwisko, int numer, Date data_ur) {
        this.id = id;
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.data_ur = data_ur;
        setNumer(numer);
    }

    public void setNumer(int numer) {
        this.numer = numer;
    }

    public int getNumer() {
        return numer;
    }
}
