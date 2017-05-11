package com.nandeep.ofooty.repository;

import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.nandeep.ofooty.modal.OAdmin;

@Repository
public class OAdminRepository implements IOAdminRepository {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public void save(OAdmin oadmin) {
		sessionFactory.getCurrentSession().save(oadmin);
	}

	@Override
	public void delete(OAdmin oadmin) {
		sessionFactory.getCurrentSession().delete(oadmin);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<OAdmin> findAll() {
		List<OAdmin> oadmins = (List<OAdmin>) sessionFactory.getCurrentSession().createCriteria(OAdmin.class).list();
		return oadmins;
	}

	@Override
	public OAdmin findById(int oadminId) {
		OAdmin oadmin = (OAdmin) sessionFactory.getCurrentSession()
				.createCriteria(OAdmin.class)
				.add(Restrictions.eq("actorId", oadminId)).uniqueResult();
		return oadmin;
	}

	public OAdmin findByMobile(String mobile){
		OAdmin oadmin = (OAdmin) sessionFactory.getCurrentSession()
				.createCriteria(OAdmin.class)
				.add(Restrictions.eq("mobile", mobile)).uniqueResult();
		return oadmin;
	}
	
}
