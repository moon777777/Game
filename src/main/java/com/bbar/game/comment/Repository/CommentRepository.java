package com.bbar.game.comment.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bbar.game.comment.domain.Comment;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
	
	public List<Comment> findByPostId(int postId);

}
