package ua.oleg.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.oleg.dao.PostgresConnection;
import ua.oleg.dao.PostgresDatabaseManager;
import ua.oleg.model.DataTable;
import ua.oleg.utils.ColumnProperties;

import java.util.ArrayList;
import java.util.List;

@Service
public class MainService {
    @Autowired
    PostgresDatabaseManager postgresDatabaseManager;

    public void setConnection(PostgresConnection connection){
        try {
            postgresDatabaseManager.setConnection(connection.getConnection());
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    public List<String> getTables(){
        List<String> result = new ArrayList<String>();

        try {
            result = postgresDatabaseManager.tables();
        }catch (Exception e){
            throw new RuntimeException(e);
        }

        return result;
    }

    public void deleteTable(String tableName){
        postgresDatabaseManager.deleteTable(tableName);
    }


    public void createTable(ColumnProperties columnProperties){
        postgresDatabaseManager.createTable(columnProperties);
    }


    public DataTable getTableData(String tableName){
        DataTable result = new DataTable();

        try{
            result = postgresDatabaseManager.getDataFromTable(tableName);
        }catch (Exception e){
            throw new RuntimeException(e);
        }

        return result;
    }
}
