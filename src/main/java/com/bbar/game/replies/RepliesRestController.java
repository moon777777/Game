package com.bbar.game.replies;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bbar.game.replies.service.RepliesService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/post")
public class RepliesRestController {
	
	private RepliesService repliesService;
	
	public RepliesRestController(RepliesService repliesService) {
		this.repliesService = repliesService;
	}
	
	@PostMapping("/replies/create")
	public Map<String,String> addComment(
			@RequestParam("postId") int postId
			, @RequestParam("commentId") int commentId
			, @RequestParam("contents") String contents
			, HttpSession session){
		int userId = (Integer)session.getAttribute("userId");

		Map<String, String> resultMap = new HashMap<>();
		
		if(repliesService.addReplies(userId, postId, commentId, contents)) {
			resultMap.put("result", "success");
		} else {
			resultMap.put("result", "fail");
		}
		return resultMap;
	}
	
	@DeleteMapping("/replies/delete")
	public Map<String, String> deletePost(
			@RequestParam("id") int id
			, HttpSession session) {
		
		Map<String, String> resultMap = new HashMap<>();
		int userId = (Integer)session.getAttribute("userId");
		
		if(repliesService.deleteReplies(id, userId)) {
			resultMap.put("result", "success");
		} else {
			resultMap.put("result", "fail");
		}
		
		return resultMap;
	}

}
