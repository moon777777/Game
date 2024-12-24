package com.bbar.game.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bbar.game.user.domain.User;

public interface UserRepository extends JpaRepository<User, Integer> {
	public int countByloginId(String loginId);

}
