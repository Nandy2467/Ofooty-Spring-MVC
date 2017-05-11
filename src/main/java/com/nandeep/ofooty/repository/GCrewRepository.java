package com.nandeep.ofooty.repository;

import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.nandeep.ofooty.modal.GCrew;

@Repository
public class GCrewRepository implements IGCrewRespository{
	
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void save(GCrew gcrew) {
		sessionFactory.getCurrentSession().save(gcrew);
	}

	@Override
	public void delete(GCrew gcrew) {
		sessionFactory.getCurrentSession().delete(gcrew);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<GCrew> findAll() {
		List<GCrew> gcrews = (List<GCrew>) sessionFactory.getCurrentSession().createCriteria(GCrew.class).list();
		return gcrews;
	}

	@Override
	public GCrew findById(int gcrewId) {
		GCrew gcrew = (GCrew) sessionFactory.getCurrentSession().createCriteria(GCrew.class)
				.add(Restrictions.eq("actorId", gcrewId)).uniqueResult();
		return gcrew;
	}
	
	public GCrew findByMobile(String mobile){
		GCrew gcrew = (GCrew) sessionFactory.getCurrentSession()
				.createCriteria(GCrew.class)
				.add(Restrictions.eq("mobile", mobile)).uniqueResult();
		return gcrew;
	}
	
}
