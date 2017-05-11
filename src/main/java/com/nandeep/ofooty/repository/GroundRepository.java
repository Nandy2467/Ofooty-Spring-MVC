package com.nandeep.ofooty.repository;

import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.nandeep.ofooty.modal.Ground;

@Repository
public class GroundRepository implements IGroundRepository {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public void save(Ground ground) {
		sessionFactory.getCurrentSession().save(ground);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Ground> findAll() {
		List<Ground> grounds = (List<Ground>) sessionFactory.getCurrentSession().createCriteria(Ground.class).list();	
		return grounds;
	}

	@Override
	public void delete(Ground ground) {
		sessionFactory.getCurrentSession().delete(ground);
	}

	@Override
	public void approve(Ground ground) {
		ground = findByGroundName(ground.getGroundName());
		ground.setEnabled(true);
	}

	@Override
	public void reject(Ground ground) {
		ground = findByGroundName(ground.getGroundName());
		ground.setEnabled(false);
	}

	@Override
	public Ground findByGroundName(String groundName) {
		Ground ground = (Ground) sessionFactory.getCurrentSession().createCriteria(Ground.class)
				.add(Restrictions.eq("groundName", groundName)).uniqueResult();
		return ground;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Ground> findAllFalseGrounds(){
		List<Ground> falseGrounds = (List<Ground>) sessionFactory.getCurrentSession()
				.createCriteria(Ground.class).add(Restrictions.eq("isEnabled", false)).list();
		return falseGrounds;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Ground> findAllTrueGrounds() {
		List<Ground> falseGrounds = (List<Ground>) sessionFactory.getCurrentSession()
				.createCriteria(Ground.class).add(Restrictions.eq("isEnabled", true)).list();
		return falseGrounds;
	}

	@SuppressWarnings("unchecked")
	public List<Ground> findGroundsByKeyValueSearch(String key, String value){
		List<Ground> grounds = (List<Ground>) sessionFactory.getCurrentSession()
				.createCriteria(Ground.class).add(Restrictions.eq(key, value))
				.add(Restrictions.eq("isEnabled", true)).list();
		return grounds;
	}
	
	public Long getTableCount(){
		Long count = (Long) sessionFactory.getCurrentSession()
				.createCriteria(Ground.class)
				.setProjection(Projections.rowCount()).uniqueResult();
		return count;
	}
	
	@SuppressWarnings("unchecked")
	public List<Ground> getGroundsNotEnabled(){
		List<Ground> grounds = (List<Ground>) sessionFactory.getCurrentSession()
				.createCriteria(Ground.class)
				.add(Restrictions.eq("isEnabled", false)).list();
		return grounds;
	}

	@Override
	public Ground findByID(int id) {
		Ground ground = (Ground) sessionFactory.getCurrentSession()
				.createCriteria(Ground.class)
				.add(Restrictions.eq("groundId", id)).uniqueResult();
		return ground;
	}
	
}
