package com.ivlieva.task.dao;

import com.ivlieva.task.models.Warehouse;
import com.ivlieva.task.utils.HibernateSessionFactoryUtil;
import org.hibernate.Session;

import javax.persistence.criteria.CriteriaQuery;
import java.util.ArrayList;
import java.util.List;

public class WarehouseDAOImpl extends DAO<Warehouse> {
    @Override
    public Warehouse findById(Long id) {
        Session session = null;
        Warehouse warehouse = null;
        try {
            session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
            warehouse = session.load(Warehouse.class, id);
        } catch (Exception e) {
            System.out.println("findById error: " + e);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return warehouse;
    }

    @Override
    public List<Warehouse> findAll() {
        Session session = null;
        List<Warehouse> recipes = new ArrayList<>();
        try {
            session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
            CriteriaQuery<Warehouse> criteriaQuery = session.getCriteriaBuilder().createQuery(Warehouse.class);
            criteriaQuery.from(Warehouse.class);
            recipes = session.createQuery(criteriaQuery).getResultList();

        } catch (Exception e) {

        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return recipes;
    }
}
