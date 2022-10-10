package ui;

import model.Book;
import model.Film;
import model.Item;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class ItemTableModel extends AbstractTableModel {

    private List<Item> li;
    private String[] columnNames;

    public ItemTableModel(List<Item> list) {
        this.li = list;
        this.columnNames = new String[]{"Title", "Author", "Director", "Producer", "Type"};
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
        return 5;
    }

    @Override
    @SuppressWarnings("methodlength")
    public Object getValueAt(int rowIndex, int columnIndex) {
        Item item = li.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return item.getTitle();
            case 1:
                if (item.getItemType().equals("Book")) {
                    Book book = (Book) item;
                    return book.getAuthor();
                } else {
                    return "n/a";
                }
            case 2:
                if (item.getItemType().equals("Film")) {
                    Film film = (Film) item;
                    return film.getDirector();
                } else {
                    return "n/a";
                }
            case 3:
                if (item.getItemType().equals("Film")) {
                    Film film = (Film) item;
                    return film.getProducer();
                } else {
                    return "n/a";
                }
            default:
                return item.getItemType();
        }
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return String.class;
    }
}
