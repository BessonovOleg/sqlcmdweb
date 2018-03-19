package ua.oleg.dao;

import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class PostgresDatabaseManager {

    private String notConnectedText = "Подключение к базе не установлено!";
    private Connection connection = null;

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public PostgresDatabaseManager() {

    }


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


    public String create(String command) {
        if (isConnectionNull()) {
            return notConnectedText;
        }

        StringBuilder result = new StringBuilder();
        String[] arrayCommand = command.split("[|]");
        String tableName;
        StringBuilder sql = new StringBuilder();

        try{
            tableName = arrayCommand[0];
            sql.append("CREATE TABLE IF NOT EXISTS ");
            sql.append(tableName);
            sql.append("(");

            for (int i = 1; i < arrayCommand.length; i++) {
                if(i > 1){
                    sql.append(",");
                }
                sql.append(arrayCommand[i]);
                sql.append(" varchar(225)");
            }

            sql.append(")");

        }catch (Exception ex){
            return "Ошибка формата команды";
        }

        try{
            Statement stm = connection.createStatement();
            stm.executeUpdate(sql.toString());
            result.append("команда выполнена успешно");
            stm.close();
        }catch (Exception ex){
            result.append("Ошибка выполнения!");
        }

        return result.toString();
    }


    public ResultSet find(String tableName) {
        if (isConnectionNull()) {
            throw new RuntimeException(notConnectedText);
        }

        ResultSet rs;

        try {
            Statement stmt = connection.createStatement();
            rs = stmt.executeQuery("select * from " + tableName);
        }catch (SQLException ex){
            throw new RuntimeException(ex);
        }
        return rs;
    }


    public String insert(String command) {
        if (isConnectionNull()) {
            return notConnectedText;
        }

        StringBuilder result = new StringBuilder();
        String[] arrayCommand = command.split("[|]");
        String tableName;
        StringBuilder sql = new StringBuilder();

        try {
            tableName = arrayCommand[0];
            sql.append("INSERT INTO ");
            sql.append(tableName);
            sql.append("(");

            for (int indexCloumns = 1; indexCloumns < arrayCommand.length; indexCloumns += 2) {
                if (indexCloumns > 1) {
                    sql.append(",");
                }
                sql.append(arrayCommand[indexCloumns]);
            }

            sql.append(") VALUES (");

            for (int indexValues = 2; indexValues < arrayCommand.length; indexValues += 2) {
                if (indexValues > 2) {
                    sql.append(",");
                }
                sql.append("'");
                sql.append(arrayCommand[indexValues]);
                sql.append("'");
            }

            sql.append(");");

        }catch (Exception ex){
            return "Ошибка формата команды";
        }

        try {
            Statement stm = connection.createStatement();
            stm.executeUpdate(sql.toString());
            result.append("Команда выполнена успешно");
        }catch (SQLException ex){
            return ("Ошибка вставки:" + ex.getMessage());
        }
        return result.toString();
    }


    public ResultSet update(String command) {
        if (isConnectionNull()) {
            throw new RuntimeException(notConnectedText);
        }

        ResultSet rs;
        String[] arrayCommand = command.split("[|]");
        String tableName;
        StringBuilder sqlUpdate = new StringBuilder();
        StringBuilder sqlWhere  = new StringBuilder();
        StringBuilder sqlSelect = new StringBuilder();

        try {
            tableName = arrayCommand[0];
            sqlUpdate.append("UPDATE ");
            sqlUpdate.append(tableName);
            sqlUpdate.append(" set ");
            sqlSelect.append("select ");

            for (int indexColumn = 1; indexColumn < arrayCommand.length; indexColumn += 4) {
                if (indexColumn > 1) {
                    sqlUpdate.append(",");
                    sqlSelect.append(",");
                }

                sqlUpdate.append(arrayCommand[indexColumn]);
                sqlUpdate.append("='");
                sqlUpdate.append(arrayCommand[indexColumn+1]);
                sqlUpdate.append("'");

                sqlSelect.append(arrayCommand[indexColumn]);
            }

            sqlWhere.append(" where ");

            for (int indexValues = 3; indexValues < arrayCommand.length; indexValues += 4) {
                if (indexValues > 3) {
                    sqlWhere.append(" and ");
                }
                sqlWhere.append(arrayCommand[indexValues]);
                sqlWhere.append(" = '");
                sqlWhere.append(arrayCommand[indexValues+1]);
                sqlWhere.append("'");
            }

            sqlUpdate.append(sqlWhere.toString());

            sqlSelect.append(" from ");
            sqlSelect.append(tableName);
            sqlSelect.append(sqlWhere.toString());

        }catch (Exception ex){
            throw new RuntimeException("Ошибка формата команды");
        }

        try {
            Statement stmt = connection.createStatement();
            rs = stmt.executeQuery(sqlSelect.toString());

        }catch (SQLException ex){
            throw new RuntimeException("Ошибка:" + ex.getMessage());
        }

        try {
            Statement stm = connection.createStatement();
            stm.executeUpdate(sqlUpdate.toString());
        }catch (SQLException ex){
            throw new RuntimeException("Ошибка обновления:" + ex.getMessage());
        }

        return rs;
    }


    public ResultSet delete(String command) {
        if (isConnectionNull()) {
            throw new RuntimeException(notConnectedText);
        }

        ResultSet rs;
        String[] arrayCommand = command.split("[|]");
        String tableName;
        StringBuilder sqlDelete = new StringBuilder();
        StringBuilder sqlSelect = new StringBuilder();
        StringBuilder sqlWhere  = new StringBuilder();

        try {
            tableName = arrayCommand[0];
            sqlDelete.append("Delete from ");
            sqlDelete.append(tableName);
            sqlWhere.append(" where ");
            sqlSelect.append("select ");

            for (int indexColumns = 1; indexColumns < arrayCommand.length; indexColumns += 2) {
                if (indexColumns > 1) {
                    sqlWhere.append(" and ");
                    sqlSelect.append(",");
                }

                sqlWhere.append(arrayCommand[indexColumns]);
                sqlWhere.append("='");
                sqlWhere.append(arrayCommand[indexColumns+1]);
                sqlWhere.append("'");

                sqlSelect.append(arrayCommand[indexColumns]);
            }

            sqlSelect.append(" from ");
            sqlSelect.append(tableName);
            sqlSelect.append(sqlWhere.toString());
            sqlDelete.append(sqlWhere.toString());

        }catch (Exception ex){
            throw new RuntimeException("Ошибка формата команды");
        }

        try {
            Statement stmt = connection.createStatement();
            rs = stmt.executeQuery(sqlSelect.toString());
        }catch (SQLException ex){
            throw new RuntimeException("Ошибка:" + ex.getMessage());
        }

        try {
            Statement stm = connection.createStatement();
            stm.executeUpdate(sqlDelete.toString());
        }catch (SQLException ex){
            throw new RuntimeException("Ошибка обновления:" + ex.getMessage());
        }

        return rs;
    }

    private boolean isConnectionNull(){
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
