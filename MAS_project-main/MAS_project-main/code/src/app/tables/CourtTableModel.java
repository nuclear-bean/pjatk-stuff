package app.tables;

import app.data.Court;

import javax.swing.table.AbstractTableModel;

public class CourtTableModel extends AbstractTableModel {
    @Override
    public int getRowCount() {
        return Court.allCourts.size();
    }

    @Override
    public int getColumnCount() {
        return 5;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Court court = Court.allCourts.get(rowIndex);
        switch (columnIndex){
            case 0: return court.id;
            case 1: return court.nawierzchnia;
            case 2: return court.cena;
            case 3:
                if(court.oswietlenie)
                    return "TAK";
                else return "NIE";
            case 4:
                if(court.kryty)
                    return "TAK";
                else return "NIE";
            default: return "N/A";
        }
    }

    @Override
    public String getColumnName(int column) {
        return switch (column) {
            case 0 -> "ID";
            case 1 -> "NAWIERZCHNIA";
            case 2 -> "CENA";
            case 3 -> "OŚWIETLENIE";
            case 4 -> "KRYCIE";
            default -> "N/A";
        };
    }
}
