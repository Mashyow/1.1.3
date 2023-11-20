package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;

import java.util.List;

public interface UserDao {
    void createUsersTable();

    void dropUsersTable();

    void saveUser(String name, String lastName, byte age);

    void removeUserById(long id);

    List<User> getAllUsers();

    void cleanUsersTable();
}
//Session - автоклозабл - его не нужно закрывать вручную
//        cleanUsersTable - должен использовать HQL
//
//        не должно быть закомментированного и неиспользуемого кода
//
//        не выполнен вывод в мейне списка всех пользователей
//
//        требованию к мейно сделаны не полностью
//
//        лишних импортов быть не должно - удалил что-то - проверь импорты