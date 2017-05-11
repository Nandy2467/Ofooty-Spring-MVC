package com.nandeep.ofooty.repository;

import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.nandeep.ofooty.modal.Player;

@Repository
public class PlayerRepository implements IPlayerRepository {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void save(Player player) {
		sessionFactory.getCurrentSession().save(player);
	}


	@Override
	public void delete(Player player) {
		sessionFactory.getCurrentSession().delete(player);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Player> findAll() {
		List<Player> players = (List<Player>) sessionFactory.getCurrentSession().createCriteria(Player.class).list();
		return players;
	}

	@Override
	public Player findById(int playerId) {
		Player player = (Player) sessionFactory.getCurrentSession()
				.createCriteria(Player.class)
				.add(Restrictions.eq("actorId", playerId)).uniqueResult();
		return player;
	}

	public Player findByMobile(String mobile){
		Player player = (Player) sessionFactory.getCurrentSession()
				.createCriteria(Player.class)
				.add(Restrictions.eq("mobile", mobile)).uniqueResult();
		return player;
	}
	
	public Long getTableCount(){
		Long count = (Long) sessionFactory.getCurrentSession()
				.createCriteria(Player.class)
				.setProjection(Projections.rowCount()).uniqueResult();
		return count;
	}
	
}
