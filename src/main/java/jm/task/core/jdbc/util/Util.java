package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    private static final String USERNAME = "root";
    private static final String PASSWORD = "Ahrilovescat1@";
    private static final String CONNECTION_URL = "jdbc:mysql://localhost:3306/usersdb";

    public static Connection connectToBD() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            //System.out.println("Успешно подключились к БД!");
            return DriverManager.getConnection(CONNECTION_URL, USERNAME, PASSWORD);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }/* finally {
            System.out.println("Закрыли соединение с БД!");
        }*/
    }
}

