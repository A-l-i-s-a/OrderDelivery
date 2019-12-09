package com.ivlieva.task.dao;

import com.ivlieva.task.models.Delivery;
import com.ivlieva.task.utils.HibernateSessionFactoryUtil;
import org.hibernate.Session;

import javax.persistence.criteria.CriteriaQuery;
import java.util.ArrayList;
import java.util.List;

public class DeliveryDAOImpl extends DAO<Delivery> {
    @Override
    public Delivery findById(Long id) {
        Session session = null;
        Delivery delivery = null;
        try {
            session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
            delivery = session.load(Delivery.class, id);
        } catch (Exception e) {
            System.out.println("findById error: " + e);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return delivery;
    }


    @Override
    public List<Delivery> findAll() {
        Session session = null;
        List<Delivery> patients = new ArrayList<>();
        try {
            session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
            CriteriaQuery<Delivery> criteriaQuery = session.getCriteriaBuilder().createQuery(Delivery.class);
            criteriaQuery.from(Delivery.class);
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
