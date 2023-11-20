package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;


public class UserDaoHibernateImpl implements UserDao {
    private Transaction transaction = null;
    Session session = Util.getSessionFactory().openSession();

    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        try {
            session.beginTransaction();
            String create_sql = "CREATE TABLE IF NOT EXISTS users (id INT NOT NULL AUTO_INCREMENT PRIMARY KEY, name VARCHAR(100) NOT NULL, lastName VARCHAR(100) NOT NULL, age SMALLINT NOT NULL)";
            session.createSQLQuery(create_sql).executeUpdate();
            session.getTransaction().commit();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }


    @Override
    public void dropUsersTable() {
        try {
            session.beginTransaction();
            session.createSQLQuery("DROP TABLE IF EXISTS users").executeUpdate();
            session.getTransaction().commit();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        User user = new User(name, lastName, age);
        try {
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        try {
            session.beginTransaction();
            User user = session.get(User.class, id);
            if (user != null) {
                session.delete(user);
            }

            session.getTransaction().commit();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }


    @Override
    public List<User> getAllUsers() {
        List<User> listUser = null;
        try {
            session.beginTransaction();
            listUser = session.createQuery("FROM User").list();
            session.getTransaction().commit();

            for (User user : listUser) {
                System.out.println(user);
            }
        } catch (Throwable e) {

            e.printStackTrace();
        }
        return listUser;

    }

    @Override
    public void cleanUsersTable() {
        try {
            session.beginTransaction();
            session.createQuery("DELETE FROM User ").executeUpdate();
            session.getTransaction().commit();
        } catch (Throwable e) {

            e.printStackTrace();
        }
    }
}

