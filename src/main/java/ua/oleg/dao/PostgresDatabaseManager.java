package ua.oleg.dao;

import org.springframework.stereotype.Component;
import ua.oleg.model.DataTable;
import ua.oleg.utils.ColumnProperties;
import ua.oleg.utils.RowContentProperties;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class PostgresDatabaseManager {
    private String notConnectedText = "Cannot connect to database";
    private Connection connection = null;

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public PostgresDatabaseManager() {}

    public List<String> tables() {
        List<String> result = new ArrayList<String>();

        if (isConnectionNull()) {
            return result;
        }

        try {
            Statement stmt = connection.createStatement();

            ResultSet rs = stmt.executeQuery("select table_name as tblName " +
                    " from information_schema.tables " +
                    " where table_schema='public' " +
                    " and table_type='BASE TABLE'" +
                    " order by table_name;");
            while (rs.next()) {
                result.add(rs.getString("tblName"));
            }
            rs.close();
            stmt.close();

        }catch (SQLException ex){
            throw new RuntimeException(ex);
        }

        return result;
    }

    public DataTable getDataFromTable(String tableName) {
        if (isConnectionNull()) {
            throw new RuntimeException(notConnectedText);
        }

        DataTable result = new DataTable();
        ResultSet rs;

        try {
            Statement stmt = connection.createStatement();
            rs = stmt.executeQuery("select * from " + tableName);

            ResultSetMetaData md = rs.getMetaData();
            int countColumns = md.getColumnCount();

            for(int i = 1;i <= countColumns;i++){
                result.getColumnCaptions().add(md.getColumnName(i));
            }

            while (rs.next()){
                List<String> rowData = new ArrayList<String>();
                for (int i = 1; i <= countColumns; i++) {
                    if (rs.getObject(i) != null) {
                        rowData.add(rs.getString(i));
                    }else{
                        rowData.add("");
                    }
                }
                result.getData().add(rowData);
            }

        }catch (SQLException ex){
            throw new RuntimeException(ex);
        }

        return result;
    }

    public String clear(String tableName) {
        if (isConnectionNull()) {
            return notConnectedText;
        }

        StringBuilder result = new StringBuilder();

        try{
            String sql = "delete from " + tableName;
            Statement stm = connection.createStatement();
            int countDelRows = stm.executeUpdate(sql);
            result.append("Команда выполнена. Удалено: ").append(countDelRows).append(" строк");
            stm.close();
        }catch (Exception ex){
            result.append("Ошибка выполнения!");
        }
        return result.toString();
    }

    public void deleteTable(String tableName) {
        if (isConnectionNull()) {
           throw new RuntimeException("Cannot connect to database");
        }

        try{
            String sql = "drop table " + tableName;
            Statement stm = connection.createStatement();
            stm.executeUpdate(sql);
            stm.close();
        }catch (Exception ex){
            throw new RuntimeException(ex);
        }
    }

    public void updateTableContents(RowContentProperties rowContentProperties){
        if (isConnectionNull()) {
            throw new RuntimeException("Cannot connect to database");
        }

        StringBuilder sql = new StringBuilder();
        String[] columnNames = rowContentProperties.getColumnNames();
        String[] columnValues = rowContentProperties.getColumnValues();

        //Insert
        if(rowContentProperties.getRowId() == 0){
            sql.append("INSERT INTO ");
            sql.append(rowContentProperties.getTableName());
            sql.append("(");

            for (int indexCloumns = 0; indexCloumns < columnNames.length; indexCloumns++) {
                if (indexCloumns > 0) {
                    sql.append(",");
                }
                sql.append(columnNames[indexCloumns]);
            }

            sql.append(") VALUES (");

            for (int indexValues = 0; indexValues < columnValues.length; indexValues ++) {
                if (indexValues > 0) {
                    sql.append(",");
                }
                sql.append("'");
                sql.append(columnValues[indexValues]);
                sql.append("'");
            }
            sql.append(");");
            executeSql(sql.toString());
        }else {
            //Update
            sql.append("UPDATE ");
            sql.append(rowContentProperties.getTableName());
            sql.append(" SET ");
            for (int indexColumn = 0;indexColumn < columnNames.length;indexColumn++){
                if(indexColumn > 0){
                    sql.append(",");
                }
                sql.append(columnNames[indexColumn]);
                sql.append(" ");
                sql.append("='");
                sql.append(columnValues[indexColumn]);
                sql.append("'");
            }

            sql.append(" where ID = ");
            sql.append(rowContentProperties.getRowId());
            executeSql(sql.toString());
        }
    }

    public void deleteRowByID(String tableName,int rowID){
        if (isConnectionNull()) {
            throw new RuntimeException("Cannot connect to database");
        }

        if(tableName == null){
            return;
        }
        String sql = "DELETE FROM " + tableName + " WHERE ID = " + rowID;
        executeSql(sql);
    }

    private void executeSql(String sql){
        try{
            Statement stm = connection.createStatement();
            stm.executeUpdate(sql);
            stm.close();
        }catch (Exception ex){
            throw new RuntimeException(ex);
        }
    }

    public void createTable(ColumnProperties columnProperties) {
        if (isConnectionNull()) {
            throw new RuntimeException("Cannot connect to database");
        }

        String result = "";
        StringBuilder sql = new StringBuilder();
        String[] tableColumns = columnProperties.getTblcolumns();

        try{
            sql.append("CREATE TABLE IF NOT EXISTS ");
            sql.append(columnProperties.getTblname());
            sql.append("(ID SERIAL");

            if (tableColumns != null) {
                for (int i = 0; i < tableColumns.length; i++) {
                    sql.append(",").append(tableColumns[i]).append(" varchar(225)");
                }
            }

            sql.append(")");
        }catch (Exception ex){
            throw new RuntimeException(ex);
        }

        executeSql(sql.toString());
    }

    public boolean isConnectionNull(){
        return (connection == null);
    }

    public void closeConnection(){
        if(connection!=null){
            try {
                connection.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
    }
}
