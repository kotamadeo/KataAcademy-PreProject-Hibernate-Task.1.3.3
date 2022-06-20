package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();
        userService.createUsersTable();
        userService.saveUser("Игорь", "Кузнецов", (byte) 25);
        userService.saveUser("Иван", "Иванов", (byte) 27);
        userService.saveUser("Федор", "Петров", (byte) 25);
        userService.saveUser("Анна", "Петрова", (byte) 27);
        userService.saveUser(null, "Ivanov", (byte) 40);
        List<User> allUsers = userService.getAllUsers();
        printUsers(allUsers);
        userService.removeUserById(1);
        userService.cleanUsersTable();
        userService.dropUsersTable();
        userService.close();
    }

    private static void printUsers(List<User> users) {
        for (User user : users) {
            System.out.println(user);
        }
    }
}
