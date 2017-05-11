package com.nandeep.ofooty.repository;

import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.nandeep.ofooty.modal.JoinTeamRequest;

@Repository
public class JoinTeamRequestRepository implements IJoinTeamRequestRepository{

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public void save(JoinTeamRequest joinTeamRequest) {
		sessionFactory.getCurrentSession().save(joinTeamRequest);
	}

	@Override
	public void delete(JoinTeamRequest joinTeamRequest) {
		sessionFactory.getCurrentSession().delete(joinTeamRequest);
	}

	@Override
	public JoinTeamRequest findByRequestId(int requestId) {
		JoinTeamRequest jtRequest = (JoinTeamRequest) sessionFactory.getCurrentSession()
				.createCriteria(JoinTeamRequest.class)
				.add(Restrictions.eq("requestId", requestId)).uniqueResult();
		return jtRequest;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<JoinTeamRequest> findAll() {
		List<JoinTeamRequest> jtRquestList = (List<JoinTeamRequest>)sessionFactory.getCurrentSession()
				.createCriteria(JoinTeamRequest.class).list();
		return jtRquestList;
	}
	
}
