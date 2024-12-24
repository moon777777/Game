package com.bbar.game.user.service;

import org.springframework.stereotype.Service;

import com.bbar.game.common.MD5HashingEncoder;
import com.bbar.game.user.domain.User;
import com.bbar.game.user.repository.UserRepository;

@Service
public class UserService {
	
	private UserRepository userRepository;
	
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	public boolean addUser(String loginId, String password, String nickname) {
		
		String salt = MD5HashingEncoder.createSalt();
		String endcodingPassword = MD5HashingEncoder.encode(password, salt);
		
		User user = User.builder()
		.loginId(loginId)
		.password(endcodingPassword)
		.nickname(nickname)
		.build();
		
		try {
			userRepository.save(user);
			return true;
		} catch(Exception e) {
			return false;
		}
	}
	
	public boolean isDuplicateLoginId(String loginId) {
		int count = userRepository.countByloginId(loginId);
		
		return count >= 1;
	}
	
	public boolean isDuplicateNickname(String nickname) {
		int count = userRepository.countBynickname(nickname);
		
		return count >= 1;
	}
	

}
