package com.nandeep.ofooty.repository;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.nandeep.ofooty.modal.Team;

@Repository
public class TeamRepository implements ITeamRepository {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public void save(Team team) {
		sessionFactory.getCurrentSession().save(team);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Team> findAll() {
		List<Team> teams = (List<Team>) sessionFactory.getCurrentSession().createCriteria(Team.class).list();
		return teams;
	}

	@Override
	public Team findByTeamName(String teamName) {
		Team team = (Team) sessionFactory.getCurrentSession().createCriteria(Team.class)
				.add(Restrictions.eq("teamName", teamName)).uniqueResult();
		return team;
	}

	@Override
	public void delete(Team team) {
		sessionFactory.getCurrentSession().delete(team);
	}

	@Override
	public Team findById(int teamId) {
		Team team = (Team) sessionFactory.getCurrentSession().createCriteria(Team.class)
				.add(Restrictions.eq("teamId", teamId)).uniqueResult();
		return team;
	}
	
	@SuppressWarnings("unchecked")
	public List<Team> findByZip(String zip){
		List<Team> teams = (List<Team>) sessionFactory.getCurrentSession().createCriteria(Team.class)
				.add(Restrictions.eq("zip", zip)).list();
		return teams;
	}
	
	public Long getTableCount(){
		Long count = (Long) sessionFactory.getCurrentSession()
				.createCriteria(Team.class)
				.setProjection(Projections.rowCount()).uniqueResult();
		return count;
	}
	
	@SuppressWarnings("unchecked")
	public List<Team> findBySearchKey(String key, String val){
		List<Team> teams = (List<Team>) sessionFactory.getCurrentSession().createCriteria(Team.class)
				.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
				.add(Restrictions.eq(key, val)).list();
		return teams;
	}

}
