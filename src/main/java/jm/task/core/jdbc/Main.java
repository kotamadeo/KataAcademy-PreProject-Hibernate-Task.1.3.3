package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();
        userService.createUsersTable();
        userService.saveUser("Игорь","Кузнецов", (byte) 25);
        userService.saveUser("Иван","Иванов", (byte) 27);
        userService.saveUser("Федор","Петров", (byte) 25);
        userService.saveUser("Анна","Петрова", (byte) 27);
        List<User> allUsers = userService.getAllUsers();
        for(User user : allUsers){
            System.out.println(user);
        }
        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}
