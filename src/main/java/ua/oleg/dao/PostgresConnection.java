package ua.oleg.dao;

import ua.oleg.utils.ConnectionProperties;

import java.sql.Connection;
import java.sql.DriverManager;


public class PostgresConnection {
    private Connection connection = null;
    private ConnectionProperties connectionProperties;


    public PostgresConnection(ConnectionProperties connectionProperties) {
        this.connectionProperties = connectionProperties;
    }


    public void connect() throws Exception {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new Exception(e);
        }

        try {
            connection = DriverManager.getConnection("jdbc:postgresql://"+connectionProperties.getHost()+"/" + connectionProperties.getDbName()
                                                        +"?loggerLevel=OFF", connectionProperties.getLogin(), connectionProperties.getPassword());
        } catch (Exception ex) {
            connection = null;
            throw new Exception(ex);
        }
    }

    public Connection getConnection() {
        return connection;
    }
}
