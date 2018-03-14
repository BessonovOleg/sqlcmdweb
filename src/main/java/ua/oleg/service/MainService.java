package ua.oleg.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.oleg.dao.PostgresConnection;
import ua.oleg.dao.PostgresDatabaseManager;

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

}