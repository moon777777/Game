package com.bbar.game.replies.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bbar.game.replies.domain.Replies;

public interface RepliesRepository extends JpaRepository<Replies, Integer> {
	
	List<Replies> findByCommentId(int commentId);

}
