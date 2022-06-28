package jm.task.core.jdbc.service;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;

import java.util.List;

public class UserServiceImpl implements UserService {
    private final UserDao jdbc = new UserDaoJDBCImpl();
    private final UserDao hibernate = new UserDaoHibernateImpl();

    public void createUsersTable() {
        //jdbc.createUsersTable();
        hibernate.createUsersTable();
    }

    public void dropUsersTable() {
        //jdbc.dropUsersTable();
        hibernate.dropUsersTable();
    }

    public void saveUser(String name, String lastName, byte age) {
        //jdbc.saveUser(name, lastName, age);
        hibernate.saveUser(name, lastName, age);
    }

    public void removeUserById(long id) {
        //jdbc.removeUserById(id);
        hibernate.removeUserById(id);
    }

    public List<User> getAllUsers() {
        //return jdbc.getAllUsers();
        return hibernate.getAllUsers();
    }

    public void cleanUsersTable() {
<<<<<<< HEAD
        userDao.cleanUsersTable();
=======
        //jdbc.cleanUsersTable();
        hibernate.cleanUsersTable();
>>>>>>> hibernate
    }
}
