package com.nandeep.ofooty.repository;

import java.util.List;

import com.nandeep.ofooty.modal.Player;

public interface IPlayerRepository {
	
	public void save(Player player);
	public void delete(Player player);
	public List<Player> findAll();
	public Player findById(int playerId);
}
