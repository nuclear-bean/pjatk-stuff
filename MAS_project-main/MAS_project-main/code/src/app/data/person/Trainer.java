package app.data.person;

import java.sql.Date;
import java.util.ArrayList;

public class Trainer extends Person{
    public static ArrayList<Trainer> allTrainers = new ArrayList<>();

    public ArrayList<Level> poziom = new ArrayList<>();
    public String opis;

    public Trainer(int id, String imie, String nazwisko, int numer, Date data_ur, String poziom, String opis) {
        super(id, imie, nazwisko, numer, data_ur);
        this.opis = opis;
        parseLevel(poziom);

        allTrainers.add(this);
    }

    private void parseLevel(String poziom) {
        poziom = poziom.replaceAll(" ","");
        String [] levelArr = poziom.split(",");
        for (String level : levelArr) {
            this.poziom.add(Level.valueOf(level));
        }
    }

    public String getPoziom(){
        StringBuilder res = new StringBuilder();
        for (Level level : this.poziom)
            res.append(level.toString()).append(", ");
        return res.toString();
    }

    public static Trainer getTrainerById(int id){
        for (Trainer trainer : allTrainers) {
            if(trainer.id == id)
                return trainer;
        }
        return null;
    }
}
