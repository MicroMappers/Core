package qa.qcri.mm.media.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.LockOptions;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.springframework.beans.factory.annotation.Autowired;
import qa.qcri.mm.media.dao.AbstractDao;


import java.io.Serializable;
import java.util.List;

public abstract class AbstractDaoImpl<E, I extends Serializable> implements AbstractDao<E,I> {

    private Class<E> entityClass;

    protected AbstractDaoImpl(Class<E> entityClass) {
        this.entityClass = entityClass;
    }

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
        Session session = getCurrentSession();
        session.saveOrUpdate(e);
    }

    @Override
    public void saveOrMerge(E e) {
        Session session = getCurrentSession();
        session.merge(e);
    }

    @Override
    public void save(E e) {
        Session session = getCurrentSession();
        session.save(e);
    }

    @Override
    public List<E> findAll() {
        Criteria criteria = getCurrentSession().createCriteria(entityClass);
        criteria.setProjection(Projections.distinct(Projections.property("id")));
        return criteria.list();
    }

    @Override
    public void delete(E e) {
        Session session = getCurrentSession();
        session.buildLockRequest(LockOptions.UPGRADE).lock(e);
        session.delete(e);
    }

    @Override
    public List<E> findByCriteria(Criterion criterion) {
        Criteria criteria = getCurrentSession().createCriteria(entityClass);
        criteria.add(criterion);
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
     public List<E> findByCriteriaByOrder(Criterion criterion, String[] orderBy, Integer count) {
        Criteria criteria = getCurrentSession().createCriteria(entityClass);
        criteria.add(criterion);
        for(int i = 0; i< orderBy.length; i++){
            criteria.addOrder(Order.desc(orderBy[i]));
        }
        if(count != null){
            criteria.setMaxResults(count);
        }
        return criteria.list();
    }

    @Override
    public List<E> findByCriteriaWithAliasByOrder(Criterion criterion, String[] orderBy, Integer count, String aliasTable, Criterion aliasCriterion) {
        Criteria criteria = getCurrentSession().createCriteria(entityClass);
        criteria.add(criterion);
        criteria.createAlias(aliasTable, aliasTable, CriteriaSpecification.LEFT_JOIN).add(aliasCriterion);

        for(int i = 0; i< orderBy.length; i++){
            criteria.addOrder(Order.desc(orderBy[i]));
        }
        if(count != null){
            criteria.setMaxResults(count);
        }
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
}
