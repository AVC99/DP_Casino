package persistence.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLConnector {
    private static Connection connection = null;
    private String url;
    private String username;
    private String password;

    private MySQLConnector(String databaseURL, String username, String password) {
        this.url = databaseURL;
        this.username = username;
        this.password = password;
    }

    private void connectDatabase() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(this.url, this.username, this.password);
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("ERROR: Cannot connect to database");
        }
    }

    public static Connection getInstance(String databaseURL, String username, String password) {
        if(connection == null) {
            MySQLConnector mySQLConnector = new MySQLConnector(databaseURL, username, password);
            mySQLConnector.connectDatabase();
        }

        return connection;
    }
}
