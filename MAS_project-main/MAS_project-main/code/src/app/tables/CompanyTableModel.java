package app.tables;

import app.data.Company;

import javax.swing.table.AbstractTableModel;

public class CompanyTableModel extends AbstractTableModel {
    @Override
    public int getRowCount() {
        return Company.allCompanies.size();
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Company company = Company.allCompanies.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> company.id;
            case 1 -> company.nazwa;
            case 2 -> company.branza;
            case 3 -> company.NIP;
            default -> "N/A";
        };
    }

    @Override
    public String getColumnName(int column) {
        return switch (column) {
            case 0 -> "ID";
            case 1 -> "NAZWA";
            case 3 -> "BRANŻA";
            case 2 -> "NIP";
            default -> "N/A";
        };
    }

    @Override
    public void fireTableDataChanged() {
        super.fireTableDataChanged();
    }
}
