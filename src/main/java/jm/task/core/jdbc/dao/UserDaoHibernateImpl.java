package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;


public class UserDaoHibernateImpl implements UserDao {
    private Transaction transaction;

    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        Session session = Util.getSessionFactory().openSession();
        transaction = session.beginTransaction();
        try {
            String create_sql = "CREATE TABLE IF NOT EXISTS users (id INT NOT NULL AUTO_INCREMENT PRIMARY KEY, name VARCHAR(100) NOT NULL, lastName VARCHAR(100) NOT NULL, age SMALLINT NOT NULL)";
            session.createSQLQuery(create_sql).executeUpdate();

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
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

        try {
            Session session = Util.getSessionFactory().openSession();

            try {
                session.beginTransaction();
                session.save(user);
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
    public void removeUserById(long id) {
        try {
            Session session = Util.getSessionFactory().openSession();

            try {
                session.beginTransaction();
                User user = (User) session.get(User.class, id);
                if (user != null) {
                    session.delete(user);
                }

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
    public List<User> getAllUsers() {
        List<User> listUser = new ArrayList<>();

        try {
            Session session = Util.getSessionFactory().openSession();

            try {
                session.beginTransaction();
                listUser = session.createQuery("FROM User", User.class).list();
                session.getTransaction().commit();
                for (User user : listUser) {
                    System.out.println(user);
                }
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
        return listUser;
    }

    @Override
    public void cleanUsersTable() {
        try {
            Session session = Util.getSessionFactory().openSession();

            try {
                session.beginTransaction();
                session.createQuery("DELETE FROM User ").executeUpdate();
                session.getTransaction().commit();
            } catch (Throwable e) {
                if (session != null) {
                    try {
                        session.close();
                    } catch (Throwable e1) {
                        e.addSuppressed(e1);
                    }
                }
            }
        } catch (Exception e) {
            if (this.transaction != null) {
                this.transaction.rollback();
            }
            e.printStackTrace();
        }
    }
}

