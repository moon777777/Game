package com.bbar.game.videoPost.domain;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
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

@Builder(toBuilder=true)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Table(name="`vpost`")
@Entity
public class VideoPost {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	@Column(name="userId")
	private int userId;
	private String title;
	@Column(name="youtubeUrl")
	private String youtubeUrl;
	@CreationTimestamp
	@Column(name="createdAt")
	private LocalDateTime createdAt;
	private String thumbnail;
	
	@UpdateTimestamp
	@Column(name="updatedAt")
	private LocalDateTime  updatedAt;
	
	@Column(name="viewCount")
	private int viewCount;

}
