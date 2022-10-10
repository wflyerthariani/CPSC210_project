package ui;

import model.Loan;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class LoanTableModel extends AbstractTableModel {

    private List<Loan> li;
    private String[] columnNames;

    public LoanTableModel(List<Loan> list) {
        this.li = list;
        this.columnNames = new String[]{"Library", "Loan Date", "Due Date", "Returned?"};
    }

    @Override
    public String getColumnName(int columnIndex) {
        return columnNames[columnIndex];
    }

    @Override
    public int getRowCount() {
        return li.size();
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Loan loan = li.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return loan.getLibrary();
            case 1:
                return loan.getLoanDate().toString();
            case 2:
                return loan.getDueDate().toString();
            default:
                return loan.getReturned().toString();
        }
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return String.class;
    }
}
