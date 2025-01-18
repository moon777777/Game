package com.bbar.game.calendar.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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
import lombok.Setter;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name="`calendar`")
@Entity
public class Calendar {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private String title;
	private String contents;
	
	@Column(name="startDate")
	private LocalDate startDate;
	@Column(name="endDate")
	private LocalDate endDate;
	
	@CreationTimestamp
	@Column(name="createdAt")
	private LocalDateTime createdAt;
	@UpdateTimestamp
	@Column(name="updatedAt")
	private LocalDateTime  updatedAt;

}
