package com.bbar.game.post.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bbar.game.post.domain.Post;

public interface PostRepository extends JpaRepository<Post, Integer> {
	
	public List<Post> findAllByOrderByIdDesc();

}
