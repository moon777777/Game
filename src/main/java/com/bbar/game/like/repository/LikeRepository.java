package com.bbar.game.like.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bbar.game.like.domain.Like;

import jakarta.transaction.Transactional;

public interface LikeRepository extends JpaRepository<Like, Integer> {
	
	public int countByTargetTypeAndTargetId(String targetType, int targetId);
	
	public int countByTargetIdAndTargetTypeAndUserId(int targetId, String targetType, int userId);
	
	public Optional<Like> findByTargetIdAndTargetTypeAndUserId(int targetId, String targetType, int userId);
	
	@Transactional
	public void deleteByTargetTypeAndTargetId(String targetType, int targetID);

}
