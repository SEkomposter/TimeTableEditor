package by.alt.Object;


import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MyTableModel extends AbstractTableModel {
    private Set<TableModelListener> listeners = new HashSet<TableModelListener>();

    private List<TableEntry> entries;

    public MyTableModel(List<TableEntry> entries) {
        this.entries = entries;
    }

    public void addTableModelListener(TableModelListener listener) {
        listeners.add(listener);
    }

    public Class<?> getColumnClass(int columnIndex) {
        return String.class;
    }

    public int getColumnCount() {
        return 4;
    }

    public String getColumnName(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return "Имя";
            case 1:
                return "Режим";
            case 2:
                return "Время начала";
            case 3:
                return "Время окончания";
        }
        return "";
    }

    public int getRowCount() {
        return entries.size();
    }

    public void removeRow(int rowIndex){
        entries.remove(rowIndex);
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        TableEntry entry = entries.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return entry.getName();
            case 1:
                return entry.getShedule();
            case 2:
                return entry.getTimeFrom();
            case 3:
                return entry.getTimeTo();
        }
        return "";
    }

    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    public void removeTableModelListener(TableModelListener listener) {
        listeners.remove(listener);
    }

    public void setValueAt(Object value, int rowIndex, int columnIndex){
        TableEntry entry = entries.get(rowIndex);
        switch (columnIndex) {
            case 0:
                entry.setName(value.toString());
            case 1:
                entry.setShedule(value.toString());
            case 2:
                entry.setTimeFrom(value.toString());
            case 3:
                entry.setTimeTo(value.toString());
        }

    }

}
