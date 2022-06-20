package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    private static Util instance;
    private final Connection connection;
    private static final String USERNAME = "root";
    private static final String PASSWORD = "Ahrilovescat1@";
    private static final String CONNECTION_URL = "jdbc:mysql://localhost:3306/usersdb";
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";

    private Util() {
        try {
            Class.forName(DRIVER);
            this.connection = DriverManager.getConnection(CONNECTION_URL, USERNAME, PASSWORD);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public static Util getInstance() {
        if (instance == null) {
            instance = new Util();
        } else {
            try {
                if (instance.getConnection().isClosed()) {
                    instance = new Util();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return instance;
    }
}

