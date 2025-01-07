package com.bbar.game.post.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import jakarta.transaction.Transactional;

@SpringBootTest
class PostServiceTest {
	
	@Autowired
	private PostService postService;
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Test
	@Transactional
	public void joinTest() {
		
		// given - 준비
		int userId = 2;
		String title = "testTtile";
		String contents = "testContents";
		
		// when - 실행
		boolean result = postService.addPost(1, "title", "contents");
		// then - 검증
//		Post post = postService.getPost(1, 2);
		
		logger.error("회원 가입 테스트 결과 : " + result);
		assertNotNull(null);
		assertEquals(result, true);
//		assert
	}
}
