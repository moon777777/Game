package com.bbar.game.replies.domain;

import java.time.LocalDateTime;

import org.hibernate.annotations.UpdateTimestamp;

import com.bbar.game.post.domain.Post;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Table(name="`replies`")
@Entity
public class Replies {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	@Column(name="userId")
	private int userId;
	@Column(name="postId")
	private int postId;
	@Column(name="commentId")
	private int commentId;
	private String contents;
	@Column(name="createdAt")
	private LocalDateTime createdAt;
	
	@UpdateTimestamp
	@Column(name="updatedAt")
	private LocalDateTime  updatedAt;
	

}
