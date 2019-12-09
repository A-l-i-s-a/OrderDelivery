package com.ivlieva.task.dao;

import com.ivlieva.task.models.Product;
import com.ivlieva.task.models.Seller;
import com.ivlieva.task.utils.HibernateSessionFactoryUtil;
import org.hibernate.Session;

import javax.persistence.criteria.CriteriaQuery;
import java.util.ArrayList;
import java.util.List;

public class SellerDAOImpl extends DAO<Seller> {
    @Override
    public Seller findById(Long id) {
        Session session = null;
        Seller seller = null;
        try {
            session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
            seller = session.load(Seller.class, id);
        } catch (Exception e) {
            System.out.println("findById error: " + e);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return seller;
    }


    @Override
    public List<Seller> findAll() {
        Session session = null;
        List<Seller> patients = new ArrayList<>();
        try {
            session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
            CriteriaQuery<Seller> criteriaQuery = session.getCriteriaBuilder().createQuery(Seller.class);
            criteriaQuery.from(Seller.class);
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

    public void query1() {
        Session session = null;
        try {
            session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.createSQLQuery("SELECT seller.name, SUM(product.price) AS sum_price" +
                    " FROM seller, product WHERE seller.id = product.id_seller " +
                    "GROUP BY seller.name ORDER BY sum_price\n")
                    .addEntity(Product.class)
                    .addEntity(Seller.class)
                    .list()
                    .forEach(System.out::println);;
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("query1 error: " + e);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    public void query2() {

    }
}
