package ua.oleg;


import org.junit.Test;
import ua.oleg.dao.PostgresConnection;
import ua.oleg.utils.ConnectionProperties;

public class DatabaseConnectionTest {

    @Test
    public void dbConnectTest(){
        ConnectionProperties connectionProperties = new ConnectionProperties();
        connectionProperties.setHost("127.0.0.1:5432");
        connectionProperties.setDbName("contactdb");
        connectionProperties.setLogin("postgres");
        connectionProperties.setPassword("postgres");

        PostgresConnection postgresConnection = new PostgresConnection(connectionProperties);



    }
}
