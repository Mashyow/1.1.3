package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {


    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();

        userService.createUsersTable();

        userService.saveUser("Den", "Mashyow", (byte) 19);
        userService.saveUser("Lola", "Opa", (byte) 20);
        userService.saveUser("Opa", "Lolaeva", (byte) 22);
        userService.saveUser("Lersi", "Isrelova", (byte) 25);

        userService.removeUserById(1);
        userService.getAllUsers();
        userService.cleanUsersTable();
        userService.dropUsersTable();

    }
}