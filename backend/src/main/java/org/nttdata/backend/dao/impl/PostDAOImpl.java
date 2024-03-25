package org.nttdata.backend.dao.impl;

import org.hibernate.query.Query;
import org.nttdata.backend.dao.PostDAO;
import org.nttdata.backend.model.Post;
import org.nttdata.backend.model.Post;
import org.nttdata.backend.util.HibernateUtil;
import org.hibernate.Session;

import java.util.List;

public class PostDAOImpl implements PostDAO {

    public void addPost(Post post) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(post);
        session.getTransaction().commit();
        session.close();
    }

    public void updatePost(Post post) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.update(post);
        session.getTransaction().commit();
        session.close();
    }

    public List<Post> listPosts() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        List<Post> posts = session.createQuery("from Post").list();
        session.getTransaction().commit();
        session.close();
        return posts;
    }

  public List<Post> getPostsByKeyword(String keyword) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query query = session.createQuery("from Post where title like '%" + keyword + "%' or content like '%" + keyword + "%'");
        List<Post> posts = query.list();
        session.getTransaction().commit();
        session.close();
        return posts;
    }

    public void removePost(int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Post post = (Post) session.get(Post.class, id);
        session.delete(post);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public List<Post> listPostsByUser(int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query query = session.createQuery("from Post where user.id = :id");
        query.setParameter("id", id);
        List<Post> posts = query.list();
        session.getTransaction().commit();
        session.close();
        return posts;
    }


    public Post getPostById(int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Post post = (Post) session.get(Post.class, id);
        session.getTransaction().commit();
        session.close();
        return post;
    }

}
