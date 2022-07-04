package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private Transaction transaction = null;

    public UserDaoHibernateImpl() {
    }


    @Override
    public void createUsersTable() {
        try {
            Session session = Util.getSessionFactory().getCurrentSession();
            transaction = session.beginTransaction();
            session.createSQLQuery("CREATE TABLE IF NOT EXISTS пользователи (id BIGINT PRIMARY KEY AUTO_INCREMENT, " +
                    "имя CHAR(30), фамилия CHAR(30), возраст TINYINT CHECK (возраст < 128))").executeUpdate();
            transaction.commit();
            System.out.println("Таблица пользователей создана!");
        } catch (Exception e) {
            e.printStackTrace();
            connectionRollback();
        }
    }

    @Override
    public void dropUsersTable() {
        try {
            Session session = Util.getSessionFactory().getCurrentSession();
            transaction = session.beginTransaction();
            session.createSQLQuery("DROP TABLE IF EXISTS пользователи").executeUpdate();
            System.out.println("Таблица пользователей сброшена!");
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            connectionRollback();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try {
            Session session = Util.getSessionFactory().getCurrentSession();
            transaction = session.beginTransaction();
            if (name == null || lastName == null || age <= 0) {
                connectionRollback();
                System.out.printf("К сожалению, пользователь %s %s не добавлен в базу данных! " +
                        "Возможно, какие-то данные пусты или введены неверно!%n", name, lastName);
            } else {
                /* вариация sql в рамках hibernate:
                session.createSQLQuery("INSERT INTO пользователи (name, lastName, age) " +
                        "VALUES ('" + name + "', '" + lastName + "', '" + age + "')").executeUpdate(); */
                session.persist(new User(name, lastName, age));
                transaction.commit();
                System.out.printf("Пользователь %s %s добавлен в базу данных!%n", name, lastName);
            }
        } catch (Exception e) {
            e.printStackTrace();
            connectionRollback();
        }
    }

    @Override
    public void removeUserById(long id) {
        try {
            Session session = Util.getSessionFactory().getCurrentSession();
            transaction = session.beginTransaction();
            session.remove(session.get(User.class, id));
            transaction.commit();
            System.out.println("Пользователь удален!");
        } catch (Exception e) {
            e.printStackTrace();
            connectionRollback();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List users = new ArrayList<>();
        try {
            Session session = Util.getSessionFactory().getCurrentSession();
            transaction = session.beginTransaction();
            users = (List<User>) session.createQuery("FROM User").getResultList();
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            connectionRollback();
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        try {
            Session session = Util.getSessionFactory().getCurrentSession();
            transaction = session.beginTransaction();
            session.createSQLQuery("TRUNCATE TABLE пользователи").executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            connectionRollback();
        }
    }

    @Override
    public void connectionRollback() {
        if (transaction != null) {
            transaction.rollback();
        }
    }
}
