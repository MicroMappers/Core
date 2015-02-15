package qa.qcri.mm.trainer.pybossa.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import qa.qcri.mm.trainer.pybossa.dao.AbstractDao;

import java.io.Serializable;
import java.util.List;

public abstract class AbstractDaoImpl<E, I extends Serializable> implements AbstractDao<E,I> {

    private Class<E> entityClass;

    protected AbstractDaoImpl(Class<E> entityClass) {
        this.entityClass = entityClass;
    }

    @Qualifier("sessionFactory")
    @Autowired
    private SessionFactory sessionFactory;



    public Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }


    @Override
    public E findById(I id) {
        return (E) getCurrentSession().get(entityClass, id);
    }

    @Override
    public void saveOrUpdate(E e) {
        getCurrentSession().saveOrUpdate(e);
    }

    @Override
    public void save(E e) {
        getCurrentSession().save(e);
    }


    @Override
    public void delete(E e) {
        getCurrentSession().delete(e);
    }

    @Override
    public List<E> findByCriteria(Criterion criterion) {
        Criteria criteria = getCurrentSession().createCriteria(entityClass);
        criteria.add(criterion);
        return criteria.list();
    }

    @Override
    public List<E> findByCriteria(Criterion criterion, Integer count) {
        Criteria criteria = getCurrentSession().createCriteria(entityClass);
        criteria.add(criterion);

        if(count != null){
            criteria.setMaxResults(count);
        }
        return criteria.list();
    }

    @Override
    public E findByCriterionID(Criterion criterion) {
        Criteria criteria = getCurrentSession().createCriteria(entityClass);
        criteria.add(criterion);
        return (E) criteria.uniqueResult();
    }

    /**  do not use this method. it is for app live testing **/
    @Override
    public List<E> findAll() {
        Criteria criteria = getCurrentSession().createCriteria(entityClass);
        criteria.setProjection(Projections.distinct(Projections.property("crisisID")));

        return criteria.list();
    }

    @Override
    public List<E> findAllByKey(String key, Criterion criterion) {
        Criteria criteria = getCurrentSession().createCriteria(entityClass);
        criteria.add(criterion);
        criteria.setProjection(Projections.distinct(Projections.property(key)));
        criteria.addOrder(Order.asc("created"));
        return criteria.list();
    }

    @Override
    public List<E> getMaxOrderByCriteria(Criterion criterion, String orderBy) {
        Criteria criteria = getCurrentSession().createCriteria(entityClass);
        criteria.add(criterion);
        criteria.addOrder(Order.desc(orderBy));
        criteria.setMaxResults(1);

        return criteria.list();
    }

    @Override
    public List<E> getAll() {
        Criteria criteria = getCurrentSession().createCriteria(entityClass);
        return criteria.list();
    }
}

