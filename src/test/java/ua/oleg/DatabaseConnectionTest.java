package ua.oleg;


import org.junit.Test;
import ua.oleg.dao.PostgresConnection;
import ua.oleg.dao.PostgresDatabaseManager;
import ua.oleg.service.MainService;
import ua.oleg.utils.ConnectionProperties;

public class DatabaseConnectionTest {

    @Test
    public void dbConnectTest(){
        ConnectionProperties connectionProperties = new ConnectionProperties();
        connectionProperties.setHost("127.0.0.1:5432");
        connectionProperties.setDbName("test");
        connectionProperties.setLogin("test");
        connectionProperties.setPassword("test");

        PostgresConnection postgresConnection = new PostgresConnection(connectionProperties);


        String tblName = "tableFromTest";
        String[] columns = {"column1","column2","column3"};

        //PostgresDatabaseManager postgresDatabaseManager = new PostgresDatabaseManager();

        //MainService mainService = new MainService();

        //mainService.setConnection(postgresConnection);

        //mainService.createTable(tblName,columns);

    }
}
