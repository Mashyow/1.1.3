package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;


import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    private Transaction transaction = null;
    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        try {
            Session session = Util.getSessionFactory().openSession();

            try {
                session.beginTransaction();
                String create_sql = "CREATE TABLE IF NOT EXISTS users (id INT NOT NULL AUTO_INCREMENT PRIMARY KEY, " +
                        "name VARCHAR(100) NOT NULL, lastName VARCHAR(100) NOT NULL, age SMALLINT NOT NULL)";
                session.createSQLQuery(create_sql).executeUpdate();
                session.getTransaction().commit();
            } catch (Throwable e) {
                if (session != null) {
                    try {
                        session.close();
                    } catch (Throwable e1) {
                        e.addSuppressed(e1);
                    }
                }
                throw e;
            }
            if (session != null) {
                session.close();
            }
        } catch (Exception e) {
            if (this.transaction != null) {
                this.transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        try {
            Session session = Util.getSessionFactory().openSession();

            try {
                session.beginTransaction();
                session.createSQLQuery("DROP TABLE IF EXISTS users").executeUpdate();
                session.getTransaction().commit();
            } catch (Throwable e) {
                if (session != null) {
                    try {
                        session.close();
                    } catch (Throwable e1) {
                        e.addSuppressed(e1);
                    }
                }
                throw e;
            }

            if (session != null) {
                session.close();
            }
        } catch (Exception e) {
            if (this.transaction != null) {
                this.transaction.rollback();
            }
            e.printStackTrace();
        }

    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        User user = new User(name, lastName, age);


        try (Session session = Util.getSessionFactory().openSession()) {

            transaction = session.beginTransaction();

            session.save(user);

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }

    }

    @Override
    public void removeUserById(long id) {

        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.delete(session.get(User.class, id));
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();
        try (Session session = Util.getSessionFactory().openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<User> criteria = builder.createQuery(User.class);
            Root<User> root = criteria.from(User.class);
            criteria.select(root);
            list = session.createQuery(criteria).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public void cleanUsersTable() {
        Transaction transaction = null;
        try (Session session = Util.getSessionFactory().openSession()) {

            transaction = session.beginTransaction();
            session.createNativeQuery("TRUNCATE TABLE users;").executeUpdate();


            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }

    }
}