package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.Query;
import java.util.List;

import static jm.task.core.jdbc.util.Util.getSessionFactory;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {
    }


    @Override
    public void createUsersTable() {
        try {
            Session session = getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();
            String command = "CREATE TABLE IF NOT EXISTS User (id INT PRIMARY KEY AUTO_INCREMENT, name VARCHAR (30),lastName VARCHAR (30), age INT )";
            Query query = session.createSQLQuery(command).addEntity(User.class);
            query.executeUpdate();
            transaction.commit();
            session.close();
        } catch (HibernateException e) {
            System.out.println("ошибка создания таблицы ");
        }
    }

    @Override
    public void dropUsersTable() {
        try {
            Session session = getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();
            String command = "DROP TABLE IF EXISTS User";
            Query query = session.createSQLQuery(command).addEntity(User.class);
            query.executeUpdate();
            transaction.commit();
            session.close();
        } catch (HibernateException e) {
            System.out.println("ошибка удаления таблицы ");
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try {
            Session session = getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();
            User user = new User(name, lastName, age);
            session.save(user);
            System.out.println("User с именем " + name + " добавлен в таблицу");
            transaction.commit();
            session.close();
        } catch (HibernateException e) {
             System.out.println("ошибка добавления user");
        }
    }

    @Override
    public void removeUserById(long id) {
        try {
            Session session = getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();
            session.delete(session.get(User.class, id));
            transaction.commit();
            session.close();
        } catch (HibernateException e) {
            System.out.println("ошибка, user не найден");
        }

    }

    @Override
    public List<User> getAllUsers() {
        try {
            Session session = getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();
            List<User> list = session.createQuery("From User", User.class).list();
            System.out.println(list);
            transaction.commit();
            session.close();
            return list;
        } catch (HibernateException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void cleanUsersTable() {
        try {
            Session session = getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();
            String command = "DELETE FROM User";
            Query query = session.createSQLQuery(command);
            query.executeUpdate();
            transaction.commit();
            session.close();
        } catch (HibernateException e) {
            throw new RuntimeException(e);
        }
    }
}
