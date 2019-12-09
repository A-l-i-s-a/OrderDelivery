package com.ivlieva.task.dao;

import com.ivlieva.task.models.Customer;
import com.ivlieva.task.utils.HibernateSessionFactoryUtil;
import org.hibernate.Session;

import javax.persistence.criteria.CriteriaQuery;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAOImpl extends DAO<Customer> {
    @Override
    public Customer findById(Long id) {
        Session session = null;
        Customer customer = null;
        try {
            session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
            customer = session.load(Customer.class, id);
        } catch (Exception e) {
            System.out.println("findById error: " + e);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return customer;
    }


    @Override
    public List<Customer> findAll() {
        Session session = null;
        List<Customer> patients = new ArrayList<>();
        try {
            session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
            CriteriaQuery<Customer> criteriaQuery = session.getCriteriaBuilder().createQuery(Customer.class);
            criteriaQuery.from(Customer.class);
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
