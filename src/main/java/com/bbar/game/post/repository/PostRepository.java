package com.bbar.game.post.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bbar.game.post.domain.Post;

import jakarta.transaction.Transactional;

public interface PostRepository extends JpaRepository<Post, Integer> {
	
	public List<Post> findAllByOrderByIdDesc();
	
	@Modifying
	@Transactional
//	@Query("update Post p set p.viewCount = p.viewCount + 1 where p.id = :id") JPQL
	@Query(value = "UPDATE post SET viewCount = viewCount + 1 WHERE id = :id", nativeQuery = true)
	public int updateView(@Param("id") int id);
	// 검증

}
