package com.ivlieva.task.dao;


import com.ivlieva.task.models.BankCard;
import com.ivlieva.task.utils.HibernateSessionFactoryUtil;
import org.hibernate.Session;

import javax.persistence.criteria.CriteriaQuery;
import java.util.ArrayList;
import java.util.List;

public class BankCardDAOImpl extends DAO<BankCard> {
    @Override
    public BankCard findById(Long id) {
        Session session = null;
        BankCard bankCard = null;
        try {
            session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
            bankCard = session.load(BankCard.class, id);
        } catch (Exception e) {
            System.out.println("findById error: " + e);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return bankCard;
    }

    @Override
    public List<BankCard> findAll() {
        Session session = null;
        List<BankCard> doctors = new ArrayList<>();
        try {
            session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
            CriteriaQuery<BankCard> criteriaQuery = session.getCriteriaBuilder().createQuery(BankCard.class);
            criteriaQuery.from(BankCard.class);
            doctors = session.createQuery(criteriaQuery).getResultList();
        } catch (Exception e) {
            System.out.println("findAll error: " + e);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return doctors;
    }
}
