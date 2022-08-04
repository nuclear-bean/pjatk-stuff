package app.data;

import java.util.ArrayList;

public class Company {
    public static ArrayList<Company> allCompanies = new ArrayList<>();

    public int id;
    public String nazwa, branza, NIP;

    public Company(int id, String nazwa, String branza, String NIP) {
        this.id = id;
        this.nazwa = nazwa;
        this.branza = branza;
        this.NIP = NIP;

        allCompanies.add(this);
    }

    public static Company getCompanyById(int id){
        for (Company company : allCompanies) {
            if(company.id == id)
                return company;
        }
        return null;
    }
}
