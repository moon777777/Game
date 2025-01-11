package com.bbar.game.images.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bbar.game.images.domain.Images;

public interface ImagesRepository extends JpaRepository<Images, Integer> {
	
}
