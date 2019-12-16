package com.ivlieva.task.dao;

import com.ivlieva.task.ui.myQuery.Q1;
import com.ivlieva.task.ui.myQuery.Q2;
import com.ivlieva.task.utils.HibernateSessionFactoryUtil;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;

public abstract class DAO<T> {

    abstract public T findById(Long id);

    abstract public List<T> findAll();

    public void save(T object) {
        Session session = null;
        try {
            session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.save(object);
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Save error:" + e);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    public void update(T object) {
        Session session = null;
        try {
            session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.update(object);
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Update error: " + e);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    public void delete(T object) {
        Session session = null;
        try {
            session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.delete(object);
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Delete error: " + e);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    public List<Q1> query1() {
        List<Q1> q1List = new ArrayList<>();
        Session session = null;
        try {
            session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
            session.beginTransaction();
            List<Object[]> rows = session.createSQLQuery("SELECT seller.name, SUM(product.price) AS sum_price" +
                    " FROM seller, product WHERE seller.id = product.id_seller " +
                    "GROUP BY seller.name ORDER BY sum_price")
                    .list();
            for (Object[] row : rows) {
                Q1 q1 = new Q1(row[0].toString(), row[1].toString());
                q1List.add(q1);
                System.out.println(q1);
            }
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("query1 error: " + e);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }

        return q1List;
    }

    public List<Q2> query2() {
        List<Q2> q2List = new ArrayList<>();

        Session session = null;
        try {
            session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
            session.beginTransaction();
            List<Object[]> rows = session.createSQLQuery("SELECT customer.name, MAX(delivery.timedelivery) FROM customer JOIN orders ON customer.id = orders.id_customer JOIN delivery ON orders.id_delivery_method = delivery.id GROUP BY customer.name")
                    .list();
            for (Object[] row : rows) {
                Q2 q2 = new Q2(row[0].toString(), row[1].toString());
                q2List.add(q2);
                System.out.println(q2);
            }
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("query2 error: " + e);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }

        return q2List;

    }
}
