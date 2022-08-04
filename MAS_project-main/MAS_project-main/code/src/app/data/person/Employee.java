package app.data.person;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Objects;

public abstract class Employee {
    public static ArrayList<Employee> allEmployees = new ArrayList<>();

    private int id;
    public String imie,nazwisko;
    public Date data_ur, data_zatr;
    public int numer;
    public double pensja;

    public Employee(int id, String imie, String nazwisko, Date data_ur, Date data_zatr, int numer, double pensja) {
        this.id = id;
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.data_ur = data_ur;
        this.data_zatr = data_zatr;
        this.numer = numer;
        this.pensja = pensja;

        allEmployees.add(this);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    abstract String getName();

    public ArrayList<Leave> getLeaves(){
        return Leave.getLeavesOfEmployee(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return id == employee.id && numer == employee.numer && Double.compare(employee.pensja, pensja) == 0 && Objects.equals(imie, employee.imie) && Objects.equals(nazwisko, employee.nazwisko) && Objects.equals(data_ur, employee.data_ur) && Objects.equals(data_zatr, employee.data_zatr);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, imie, nazwisko, data_ur, data_zatr, numer, pensja);
    }
}
