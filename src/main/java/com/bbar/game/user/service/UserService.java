package com.bbar.game.user.service;

import org.springframework.stereotype.Service;

import com.bbar.game.user.domain.User;
import com.bbar.game.user.repository.UserRepository;

@Service
public class UserService {
	
	private UserRepository userRepository;
	
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	public boolean addUser(String loginId, String password, String nickname) {
		
		User user = User.builder()
		.loginId(loginId)
		.password(password)
		.nickname(nickname)
		.build();
		
		try {
			userRepository.save(user);
			return true;
		} catch(Exception e) {
			return false;
		}
	}
	

}
