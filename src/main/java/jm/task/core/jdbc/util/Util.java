package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


public class Util {
    private static Util instance;
    private final Connection connection;
    private static final String USERNAME = "root";
    private static final String PASSWORD = "Ahrilovescat1@";
    private static final String CONNECTION_URL = "jdbc:mysql://localhost:3306/usersdb?autoReconnect=true&useSSL=false";
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static SessionFactory sessionFactory;

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
            } catch (RuntimeException | SQLException e) {
                e.printStackTrace();
            }
        }
        return instance;
    }

    public static synchronized SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                //sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
                Configuration configuration = new Configuration();
                Properties properties = new Properties();
                properties.put(AvailableSettings.DRIVER, DRIVER);
                properties.put(AvailableSettings.URL, CONNECTION_URL);
                properties.put(AvailableSettings.USER, USERNAME);
                properties.put(AvailableSettings.PASS, PASSWORD);
                properties.put(AvailableSettings.DIALECT, "org.hibernate.dialect.MySQL5Dialect");
                properties.put(AvailableSettings.SHOW_SQL, "true");
                properties.put(AvailableSettings.CURRENT_SESSION_CONTEXT_CLASS, "thread");
                properties.put(AvailableSettings.HBM2DDL_AUTO, "create-drop");
                configuration.setProperties(properties);
                configuration.addAnnotatedClass(User.class);
                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties()).build();
                sessionFactory = configuration.buildSessionFactory(serviceRegistry);
            } catch (Exception e) {
                e.getCause();
                e.printStackTrace();
            }
        }
        return sessionFactory;
    }
}


