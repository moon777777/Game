package com.bbar.game.comment.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bbar.game.comment.domain.Comment;

import jakarta.transaction.Transactional;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
	
	public List<Comment> findByPostId(int postId);
	
	public int countByPostId(int postId);
	
	@Transactional
	public void deleteByPostId(int postId);

}
