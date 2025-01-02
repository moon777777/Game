package com.bbar.game.user.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bbar.game.common.FileManager;
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
		.salt(salt)
		.nickname(nickname)
		.build();
		
		try {
			userRepository.save(user);
			return true;
		} catch(Exception e) {
			return false;
		}
	}
	
	public boolean changePassword(String loginId, String newPassword) {
		String salt = MD5HashingEncoder.createSalt();
		String endcodingPassword = MD5HashingEncoder.encode(newPassword, salt);
		
		User user = userRepository.findByLoginId(loginId);
		
		user.setPassword(endcodingPassword);
	    user.setSalt(salt);
		
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
	
	public User getUser(String loginId, String password) {
		User user = userRepository.findByLoginId(loginId);
		String salt = user.getSalt();
		String endcodingPassword = MD5HashingEncoder.encode(password, salt);
		
		return userRepository.findByLoginIdAndPassword(loginId, endcodingPassword);
	}
	
	public boolean updateNickname(int id, String nickname) {
		
		Optional<User> optionalUser = userRepository.findById(id);
		
		if(optionalUser.isPresent()) {
			User user = optionalUser.get();
			
			user = user.toBuilder()
			.nickname(nickname)
			.build();
			
			try {
				userRepository.save(user);
				return true;
			} catch(Exception e) {
				return false;
			}
			
		} else {
			return false;
		}
	}
	
	public boolean updateFile(int id, MultipartFile newFile) {
		Optional<User> optionalUser = userRepository.findById(id);
		
		User user = optionalUser.get();
		
		String oldFilePath = user.getImagePath();
		
		if (oldFilePath != null) {
	        try {
	            FileManager.removeFile(oldFilePath);
	        } catch (Exception e) {
	            return false;
	        }
	    }
		String imagePath = FileManager.saveFile(id, newFile);
		
		user = user.toBuilder()
		.imagePath(imagePath)
		.build();
		
		try {
			userRepository.save(user);
			return true;
		} catch(Exception e) {
			return false;
		}
	}
	
		public User getUser(int id) {
		
		Optional<User> optionalUser = userRepository.findById(id);
		
		return optionalUser.orElse(null);
	}
		

}
