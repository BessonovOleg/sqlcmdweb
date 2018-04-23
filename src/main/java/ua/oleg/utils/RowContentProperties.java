package ua.oleg.utils;

public class RowContentProperties {
    private int rowId;
    private String [] columnNames;
    private String [] columnValues;

    public int getRowId() {
        return rowId;
    }

    public void setRowId(int rowId) {
        this.rowId = rowId;
    }

    public String[] getColumnNames() {
        return columnNames;
    }

    public void setColumnNames(String[] columnNames) {
        this.columnNames = columnNames;
    }

    public String[] getColumnValues() {
        return columnValues;
    }

    public void setColumnValues(String[] columnValues) {
        this.columnValues = columnValues;
    }
}
