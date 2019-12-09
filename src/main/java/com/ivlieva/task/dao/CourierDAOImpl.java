package com.ivlieva.task.dao;

import com.ivlieva.task.models.Courier;
import com.ivlieva.task.utils.HibernateSessionFactoryUtil;
import org.hibernate.Session;

import javax.persistence.criteria.CriteriaQuery;
import java.util.ArrayList;
import java.util.List;

public class CourierDAOImpl extends DAO<Courier> {
    @Override
    public Courier findById(Long id) {
        Session session = null;
        Courier courier = null;
        try {
            session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
            courier = session.load(Courier.class, id);
        } catch (Exception e) {
            System.out.println("findById error: " + e);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return courier;
    }


    @Override
    public List<Courier> findAll() {
        Session session = null;
        List<Courier> patients = new ArrayList<>();
        try {
            session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
            CriteriaQuery<Courier> criteriaQuery = session.getCriteriaBuilder().createQuery(Courier.class);
            criteriaQuery.from(Courier.class);
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
