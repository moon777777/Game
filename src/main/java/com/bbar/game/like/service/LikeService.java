package com.bbar.game.like.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.bbar.game.like.domain.Like;
import com.bbar.game.like.repository.LikeRepository;

@Service
public class LikeService {
	
	private LikeRepository likeRepository;
	
	public LikeService(LikeRepository likeRepository) {
		this.likeRepository = likeRepository;
	}
	
	public boolean addLike(String targetType, int targetId, int userId) {
		
		Like like = Like.builder()
        .targetType(targetType)
        .targetId(targetId)
        .userId(userId)
        .build();
		
		try {
	        likeRepository.save(like);
	        return true;
	    } catch(Exception e) {
	        return false;
	    }
	}
	
	public boolean deleteLike(int targetId, String targetType, int userId) {
		Optional<Like> optionalLike = likeRepository.findByTargetIdAndTargetTypeAndUserId(targetId, targetType, userId);
		
		if(optionalLike.isPresent()) {
			Like like = optionalLike.get();
			likeRepository.delete(like);
			return true;
		} else {
			return false;
		}
	}
	
	public boolean isLike(int targetId, String targetType, int userId) {
		int count = likeRepository.countByTargetIdAndTargetTypeAndUserId(targetId, targetType, userId);
		
		if(count > 0) {
			return true;
		} else {
			return false;
		}
	}
	
	public int getLikeCount(String targetType, int targetId) {
		return likeRepository.countByTargetTypeAndTargetId(targetType, targetId);
	}
	
	public void deleteLikeByTargetId(String targetType, int postId) {
		likeRepository.deleteByTargetTypeAndTargetId(targetType, postId);
	}

}
