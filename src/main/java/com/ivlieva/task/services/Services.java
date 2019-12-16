package com.ivlieva.task.services;

import com.ivlieva.task.dao.DAO;
import com.ivlieva.task.ui.myQuery.Q1;
import com.ivlieva.task.ui.myQuery.Q2;

import java.util.List;

public class Services<T> {

    private DAO<T> dao;

    public Services(DAO<T> dao) {
        this.dao = dao;
    }

    public T find(Long id) {
        return dao.findById(id);
    }

    public List<T> findAll() {
        return dao.findAll();
    }

    public void save(T object) {
        dao.save(object);
    }

    public void delete(T object) {
        dao.delete(object);
    }

    public void update(T object) {
        dao.update(object);
    }

    public List<Q1> q1(){return dao.query1();}

    public List<Q2> q2(){return dao.query2();}
}
