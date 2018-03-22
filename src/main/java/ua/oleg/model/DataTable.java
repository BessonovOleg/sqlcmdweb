package ua.oleg.model;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DataTable {
    private List<String> columnCaptions;
    private List<List<String>> data;

    public DataTable() {
        columnCaptions = new ArrayList<String>();
        data = new ArrayList<List<String>>();
    }

    public List<String> getColumnCaptions() {
        return columnCaptions;
    }

    public void setColumnCaptions(List<String> columnCaptions) {
        this.columnCaptions = columnCaptions;
    }

    public List<List<String>> getData() {
        return data;
    }

    public void setData(List<List<String>> data) {
        this.data = data;
    }
}
