package com.bbar.game.comment;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bbar.game.comment.service.CommentService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/post")
public class CommentRestController {
	
	private CommentService commentService;
	
	public CommentRestController( CommentService commentService) {
		this.commentService = commentService;
	}
	
	@PostMapping("/comment/create")
	public Map<String,String> addComment(
			@RequestParam("postId") int postId
			, @RequestParam("contents") String contents
			, HttpSession session){
		int userId = (Integer)session.getAttribute("userId");

		Map<String, String> resultMap = new HashMap<>();
		
		if(commentService.addComment(userId, postId, contents)) {
			resultMap.put("result", "success");
		} else {
			resultMap.put("result", "fail");
		}
		return resultMap;
	}
	
	@PutMapping("/comment/update")
	public Map<String, String>updateComment(
			@RequestParam("id") int id
			, @RequestParam("contents") String contents
			, HttpSession session) {
		
		Map<String, String> resultMap = new HashMap<>();
		
		int userId = (Integer)session.getAttribute("userId");
		
		if(commentService.updatePost(id, contents, userId)) {
			resultMap.put("result", "success");
		} else {
			resultMap.put("result", "fail");
		}
		
		return resultMap;
		
	}
	
	@DeleteMapping("/comment/delete")
	public Map<String, String> deleteComment(
			@RequestParam("id") int id
			, HttpSession session) {
		
		Map<String, String> resultMap = new HashMap<>();
		int userId = (Integer)session.getAttribute("userId");
		
		if(commentService.deleteComment(id, userId)) {
			resultMap.put("result", "success");
		} else {
			resultMap.put("result", "fail");
		}
		
		return resultMap;
	}

}
