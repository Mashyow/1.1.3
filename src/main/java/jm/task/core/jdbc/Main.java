package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

import java.util.ArrayList;

public class Main {


    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();
        ArrayList<User> UsersList = new ArrayList<>();
        User user1 = new User("Den", "Mashyow", (byte) 19);
        User user2 = new User("Lola", "Opa", (byte) 20);
        User user3 = new User("Opa", "Lolaeva", (byte) 22);
        User user4 = new User("Lersi", "Isrelova", (byte) 25);
        UsersList.add(user1);
        UsersList.add(user2);
        UsersList.add(user3);
        UsersList.add(user4);
        userService.createUsersTable();

        for (User u : UsersList) {
            userService.saveUser(u.getName(), u.getLastName(), u.getAge());
            System.out.println("Персона с никнеймом : " + u.getName() + " добавлен в таблицу");
        }
       //userService.getAllUsers();
       //userService.cleanUsersTable();
       // userService.dropUsersTable();



    }
}