package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class UserDaoJDBCImpl implements UserDao {
    private final Connection connection = Util.getInstance().getConnection();

    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() {
        try (PreparedStatement statement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS пользователи " +
                "(id BIGINT PRIMARY KEY AUTO_INCREMENT, имя CHAR(30), фамилия CHAR(30), возраст TINYINT CHECK (возраст < 128))")) {
            connection.setAutoCommit(false);
            statement.executeUpdate();
            connection.commit();
            System.out.println("Таблица пользователей создана!");
        } catch (SQLException e) {
            connectionRollback();
            throw new RuntimeException(e);
        }
    }

    public void dropUsersTable() {
        try (PreparedStatement statement = connection.prepareStatement("DROP TABLE IF EXISTS пользователи")) {
            connection.setAutoCommit(false);
            statement.executeUpdate();
            connection.commit();
            System.out.println("Таблица пользователей сброшена!");
        } catch (SQLException e) {
            connectionRollback();
            throw new RuntimeException(e);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (PreparedStatement statement = connection.prepareStatement("INSERT INTO пользователи " +
                "(имя, фамилия, возраст) VALUES (?, ?, ?)")) {
            connection.setAutoCommit(false);
            if (name == null || lastName == null || age <= 0) {
                connectionRollback();
                System.out.printf("К сожалению, пользователь %s %s не добавлен в базу данных! " +
                        "Возможно, какие-то данные пусты или введены неверно!%n", name, lastName);
            } else {
                statement.setString(1, name);
                statement.setString(2, lastName);
                statement.setByte(3, age);
                statement.executeUpdate();
                connection.commit();
                System.out.printf("Пользователь %s %s добавлен в базу данных!%n", name, lastName);
            }
        } catch (SQLException e) {
            connectionRollback();
            throw new RuntimeException(e);
        }
    }

    public void removeUserById(long id) {
        try (PreparedStatement statement = connection.prepareStatement("DELETE FROM пользователи WHERE id = ?")) {
            connection.setAutoCommit(false);
            statement.setLong(1, id);
            statement.executeUpdate();
            connection.commit();
            System.out.println("Пользователь удален!");
        } catch (SQLException e) {
            connectionRollback();
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM пользователи")) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("имя"));
                user.setLastName(resultSet.getString("фамилия"));
                user.setAge(resultSet.getByte("возраст"));
                users.add(user);
            }
        } catch (SQLException e) {
            connectionRollback();
            throw new RuntimeException(e);
        }
        return users;
    }

    public void cleanUsersTable() {
        try (PreparedStatement statement = connection.prepareStatement("TRUNCATE TABLE пользователи")) {
            connection.setAutoCommit(false);
            statement.executeUpdate();
            System.out.println("Таблица очищена!");
            connection.commit();
        } catch (SQLException e) {
            connectionRollback();
            throw new RuntimeException(e);
        }
    }

    @Override
    public void connectionRollback() {
        try {
            connection.rollback();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
