package com.bbar.game.post.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.bbar.game.post.dto.BoardDTO;

import jakarta.transaction.Transactional;

@SpringBootTest
class PostServiceTest {
	
	@Autowired
	private PostService postService;
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Test
	@Transactional
	public void addTest() {
		
		// given - 준비
		
		int userId = 1;
		int postId = 13;
		String title = "얌";
		String contents = "옹";
		
		// when - 실행
		boolean result = postService.addPost(userId, title, contents);
		// then - 검증
		
		BoardDTO post = postService.getPost(postId, userId);
		
		logger.error("회원 가입 테스트 결과 : " + result);
		assertNotNull(post);
		assertEquals(result, true);
//		assert
	}
}
