package ua.oleg.utils;

public class ConnectionProperties {
    private String host;
    private String login;
    private String password;
    private String dbName;

    public ConnectionProperties(){

    }

    public ConnectionProperties(String host, String login, String password, String dbName) {
        this.host = host;
        this.login = login;
        this.password = password;
        this.dbName = dbName;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }
}
