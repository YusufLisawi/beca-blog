package org.nttdata.backend.dao.impl;

import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.nttdata.backend.dao.UserDAO;
import org.nttdata.backend.model.User;
import org.nttdata.backend.util.HibernateUtil;
import org.hibernate.Session;

import java.util.List;

public class UserDAOImpl implements UserDAO {

    public void addUser(User user) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(user);
        session.getTransaction().commit();
        session.close();
    }

    public void updateUser(User user) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.update(user);
        session.getTransaction().commit();
        session.close();
    }

    public List<User> listUsers() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        List<User> users = session.createQuery("from User").list();
        session.getTransaction().commit();
        session.close();
        return users;
    }

    public User getUserById(int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        User user = (User) session.get(User.class, id);
        session.getTransaction().commit();
        session.close();
        return user;
    }

    public void removeUser(int id) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();

            // Delete all posts related to the user
            String hqlDeletePosts = "delete from Post where user.id = :id";
            Query queryDeletePosts = session.createQuery(hqlDeletePosts);
            queryDeletePosts.setParameter("id", id);
            queryDeletePosts.executeUpdate();

            // Delete the user
            String hqlDeleteUser = "delete from User where id = :id";
            Query queryDeleteUser = session.createQuery(hqlDeleteUser);
            queryDeleteUser.setParameter("id", id);
            queryDeleteUser.executeUpdate();

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        } finally {
            if (session != null) {
                session.close();
            }
        }

    }

    public User getUserByUsername(String username) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query query = session.createQuery("from User where username = :username");
        query.setParameter("username", username);
        User user = (User) query.uniqueResult();
        session.getTransaction().commit();
        session.close();
        return user;
    }

    public User getUserByNameAndPassword(String username, String password) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query query = session.createQuery("from User where username = :username and password = :password");
        query.setParameter("username", username);
        query.setParameter("password", password);
        User user = (User) query.uniqueResult();
        session.getTransaction().commit();
        session.close();
        return user;
    }


}
