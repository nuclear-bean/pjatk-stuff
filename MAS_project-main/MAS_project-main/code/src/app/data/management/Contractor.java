package app.data.management;

import java.util.Objects;

public class Contractor {
    int id, NIP;
    String zakres;
    int number,cena;

    public Contractor(int id, int NIP, String zakres, int number, int cena) {
        this.id = id;
        this.NIP = NIP;
        this.zakres = zakres;
        this.number = number;
        this.cena = cena;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Contractor that = (Contractor) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
