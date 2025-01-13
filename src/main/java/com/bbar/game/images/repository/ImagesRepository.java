package com.bbar.game.images.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bbar.game.images.domain.Images;

import jakarta.transaction.Transactional;

public interface ImagesRepository extends JpaRepository<Images, Integer> {
	List<Images> findByPostId(int postId);
	
	@Transactional
	public void deleteByPostId(int postId);
}
