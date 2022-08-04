package app.tables;

import app.data.Company;
import app.data.person.Client;

import javax.swing.table.AbstractTableModel;

public class ClientTableModel extends AbstractTableModel {
    @Override
    public int getRowCount() {
        return Client.all_clients.size();
    }

    @Override
    public int getColumnCount() {
        return 7;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Client client = Client.all_clients.get(rowIndex);
        switch (columnIndex){
            case 0:
                return client.id;
            case 1:
                return client.imie;
            case 2: 
                return client.nazwisko;
            case 3:
                if(client.getNumer() == 0)
                    return "Nie podano";
                return client.getNumer();
            case 4:
                return client.data_ur;
            case 5: 
                return client.data_rejestracji;
            case 6:
                if(client.firma_id == 0)
                    return "-";
                Company company = Company.getCompanyById(client.firma_id);
                if(company != null)
                    return company.nazwa;
                else
                    return "-";
            default:
                return "N/A";
        }
    }

    @Override
    public void fireTableDataChanged() {
        super.fireTableDataChanged();
    }

    @Override
    public String getColumnName(int column) {
        return switch (column) {
            case 0 -> "ID";
            case 1 -> "IMIĘ";
            case 2 -> "NAZWISKO";
            case 3 -> "NUMER";
            case 4 -> "DATA UR.";
            case 5 -> "DATA REJ.";
            case 6 -> "FIRMA";
            default -> "N/A";
        };
    }
}
