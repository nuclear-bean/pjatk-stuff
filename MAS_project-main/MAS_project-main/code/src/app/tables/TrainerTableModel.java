package app.tables;

import app.data.person.Trainer;

import javax.swing.table.AbstractTableModel;

public class TrainerTableModel extends AbstractTableModel {
    @Override
    public int getRowCount() {
        return Trainer.allTrainers.size();
    }

    @Override
    public int getColumnCount() {
        return 7;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Trainer trainer = Trainer.allTrainers.get(rowIndex);
        switch (columnIndex){
            case 0:
                return trainer.id;

            case 1:
                return trainer.imie;

            case 2:
                return trainer.nazwisko;

            case 3:
                if(trainer.getNumer() == 0)
                    return "Nie podano";
                return trainer.getNumer();

            case 4:
                return trainer.data_ur;

            case 5:
                return trainer.getPoziom();

            case 6:
                return trainer.opis;

            default:
                return "N/A";
        }
    }

    @Override
    public String getColumnName(int column) {
        return switch (column) {
            case 0 -> "ID";
            case 1 -> "IMIĘ";
            case 2 -> "NAZWISKO";
            case 3 -> "NUMER";
            case 4 -> "DATA UR.";
            case 5 -> "POZIOM";
            case 6 -> "OPIS";
            default -> "N/A";
        };
    }
}
