package com.bbar.game.videoPost.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bbar.game.videoPost.domain.VideoPost;

import jakarta.transaction.Transactional;

public interface VideoPostRepository extends JpaRepository<VideoPost, Integer> {
	
	@Modifying
	@Transactional
//	@Query("update Post p set p.viewCount = p.viewCount + 1 where p.id = :id") JPQL
	@Query(value = "UPDATE vpost SET viewCount = viewCount + 1 WHERE id = :id", nativeQuery = true)
	public int updateView(@Param("id") int id);
	// 검증

}
