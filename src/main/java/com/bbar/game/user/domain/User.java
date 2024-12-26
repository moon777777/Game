package com.bbar.game.user.domain;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

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
@Table(name="`user`")
@Getter
@Entity
public class User {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	@Column(name="loginId")
	private String loginId;
	private String password;
	private String salt;
	private String nickname;
	@Column(name="imagePath")
	private String imagePath;
	@CreationTimestamp
	@Column(name="createdAt")
	private LocalDateTime createdAt;
	@CreationTimestamp
	@Column(name="updatedAt")
	private LocalDateTime updatedAt;

}
