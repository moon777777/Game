package com.bbar.game.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bbar.game.user.domain.User;

public interface UserRepository extends JpaRepository<User, Integer> {
	public User findByLoginId(String loginId);
	
	public int countByloginId(String loginId);
	
	public int countBynickname(String nickname);
	
	public User findByLoginIdAndPassword(String loginId, String password);
	
	public User findByimagePath(String imagePath);
	

}
