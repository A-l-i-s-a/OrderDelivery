package com.ivlieva.task.dao;

import com.ivlieva.task.models.Product;
import com.ivlieva.task.utils.HibernateSessionFactoryUtil;
import org.hibernate.Session;

import javax.persistence.criteria.CriteriaQuery;
import java.util.ArrayList;
import java.util.List;

public class ProductDAOImpl extends DAO<Product> {
    @Override
    public Product findById(Long id) {
        Session session = null;
        Product product = null;
        try {
            session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
            product = session.load(Product.class, id);
        } catch (Exception e) {
            System.out.println("findById error: " + e);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return product;
    }


    @Override
    public List<Product> findAll() {
        Session session = null;
        List<Product> patients = new ArrayList<>();
        try {
            session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
            CriteriaQuery<Product> criteriaQuery = session.getCriteriaBuilder().createQuery(Product.class);
            criteriaQuery.from(Product.class);
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
