package app.tables;

import app.data.events.Reservation;
import gui.GUI;
import javax.swing.table.AbstractTableModel;
import java.sql.Date;

public class ScheduleTableModel extends AbstractTableModel {
    @Override
    public int getRowCount() {
        return 9;
    }

    @Override
    public int getColumnCount() {
        return 21;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        String time = switch (rowIndex) {
            case 0 -> "10:00";
            case 1 -> "11:00";
            case 2 -> "12:00";
            case 3 -> "13:00";
            case 4 -> "14:00";
            case 5 -> "15:00";
            case 6 -> "16:00";
            case 7 -> "17:00";
            case 8 -> "18:00";
            default -> "";
        };

        // time column
        if (columnIndex == 0)
            return time;

        // all other columns
        else {
            Date date = Date.valueOf(GUI.currentDate.toString());

            for (Reservation reservation : Reservation.allReservations)
                if(reservation.data.equals(date) && reservation.kortId == columnIndex && reservation.timeInBetween(time))
                    return "ZAJĘTY :" + reservation.id;          // text to be show in occupied reservation cell
        }
        return "-";
    }

    @Override
    public String getColumnName(int column) {
        if(column == 0)
            return "GODZINA";
        return "Kort " + column;
    }

    @Override
    public void fireTableDataChanged() {
        super.fireTableDataChanged();
    }
}
