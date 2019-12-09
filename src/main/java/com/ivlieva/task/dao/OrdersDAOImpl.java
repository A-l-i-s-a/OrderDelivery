package com.ivlieva.task.dao;

import com.ivlieva.task.models.Orders;
import com.ivlieva.task.utils.HibernateSessionFactoryUtil;
import org.hibernate.Session;

import javax.persistence.criteria.CriteriaQuery;
import java.util.ArrayList;
import java.util.List;

public class OrdersDAOImpl extends DAO<Orders> {
    @Override
    public Orders findById(Long id) {
        Session session = null;
        Orders orders = null;
        try {
            session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
            orders = session.load(Orders.class, id);
        } catch (Exception e) {
            System.out.println("findById error: " + e);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return orders;
    }


    @Override
    public List<Orders> findAll() {
        Session session = null;
        List<Orders> patients = new ArrayList<>();
        try {
            session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
            CriteriaQuery<Orders> criteriaQuery = session.getCriteriaBuilder().createQuery(Orders.class);
            criteriaQuery.from(Orders.class);
            patients = session.createQuery(criteriaQuery).getResultList();
        } catch (Exception e) {
            System.out.println("findAll error: " + e);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return patients;
    }
}
