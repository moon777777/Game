package com.bbar.game.post;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bbar.game.post.service.PostService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/post")
public class PostRestController {
	
	private PostService postService;
	
	public PostRestController(PostService postService) {
		this.postService = postService;
	}
	
	@PostMapping("/create")
	public Map<String, String> addPost(
			@RequestParam("title") String title
			, @RequestParam("contents") String contents
			, @RequestParam(value="youtubeUrl", required=false) String youtubeUrl
			, @RequestParam(value="imageFiles", required=false) List<MultipartFile> files
			, HttpSession session){
		
		int userId = (Integer)session.getAttribute("userId");
		
		Map<String, String> resultMap = new HashMap<>();
		
		if(postService.addPost(userId, title, contents, youtubeUrl ,files)) {
			resultMap.put("result", "success");
		} else {
			resultMap.put("result", "fail");
		}
		return resultMap;
	}
	
	@PutMapping("/update")
	public Map<String, String>updatePost(
			@RequestParam("id") int id
			, @RequestParam("title") String title
			, @RequestParam("contents") String contents
			, @RequestParam(value="youtubeUrl", required=false) String youtubeUrl
			, @RequestParam(value="imageFiles", required=false) List<MultipartFile> files
			, HttpSession session) {
		
		Map<String, String> resultMap = new HashMap<>();
		int userId = (Integer)session.getAttribute("userId");
		
		if(postService.updatePost(id, title, contents, userId, youtubeUrl ,files)) {
			resultMap.put("result", "success");
		} else {
			resultMap.put("result", "fail");
		}
		
		return resultMap;
		
	}
	
	@DeleteMapping("/delete")
	public Map<String, String> deletePost(
			@RequestParam("id") int id
			, HttpSession session) {
		
		Map<String, String> resultMap = new HashMap<>();
		int userId = (Integer)session.getAttribute("userId");
		
		if(postService.deletePost(id, userId)) {
			resultMap.put("result", "success");
		} else {
			resultMap.put("result", "fail");
		}
		
		return resultMap;
	}

}
