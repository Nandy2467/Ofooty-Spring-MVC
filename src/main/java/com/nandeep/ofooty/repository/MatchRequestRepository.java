package com.nandeep.ofooty.repository;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.hibernate.type.IntegerType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.nandeep.ofooty.modal.MatchRequest;
import com.nandeep.ofooty.util.MatchRequestStatus;

@Repository
public class MatchRequestRepository  implements IMatchRequestRepository{

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public MatchRequest findById(int id) {
		MatchRequest mr = (MatchRequest) sessionFactory.getCurrentSession().createCriteria(MatchRequest.class)
				.add(Restrictions.eq("mrId",id)).uniqueResult();
		return mr;
	}

	@Override
	public void save(MatchRequest matchRequest) {
		sessionFactory.getCurrentSession().save(matchRequest);
	}
	
	@SuppressWarnings("unchecked")
	public List<MatchRequest> getActiveMatchRequest(int teamId){
	List<MatchRequest> mrList = (List<MatchRequest>)sessionFactory.getCurrentSession().createCriteria(MatchRequest.class)
				.add(Restrictions.eq("reqStatus", MatchRequestStatus.OPEN))
				.createCriteria("team")
				.add(Restrictions.sqlRestriction("{alias}.teamId=?", teamId, IntegerType.INSTANCE))
				.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
				.list();
		return mrList;
	}

}
