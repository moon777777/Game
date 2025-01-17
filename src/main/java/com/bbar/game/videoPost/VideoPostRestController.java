package com.bbar.game.videoPost;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bbar.game.videoPost.service.VideoPostService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/post")
public class VideoPostRestController {
	
	private VideoPostService videoPostService;
	
	public VideoPostRestController(VideoPostService videoPostService) {
		this.videoPostService = videoPostService; 
	}
	
	@PostMapping("/video/create")
	public Map<String, String> addPost(
			HttpSession session
			, @RequestParam("title") String title
			, @RequestParam("youtubeUrl") String youtubeUrl
			,  @RequestParam("thumbnailUrl") String thumbnailUrl
			){
		
		int userId = (Integer)session.getAttribute("userId");
		
		Map<String, String> resultMap = new HashMap<>();
		
		if(videoPostService.addVideoPost(userId, title, youtubeUrl, thumbnailUrl)) {
			resultMap.put("result", "success");
		} else {
			resultMap.put("result", "fail");
		}
		return resultMap;
	}
	
	@PutMapping("/video/update")
	public Map<String, String>updatePost(
			@RequestParam("id") int id
			, @RequestParam("title") String title
			, @RequestParam("youtubeUrl") String youtubeUrl
			, HttpSession session) {
		
		Map<String, String> resultMap = new HashMap<>();
		int userId = (Integer)session.getAttribute("userId");
		
		if(videoPostService.updateVideoPost(id, title, userId, youtubeUrl)) {
			resultMap.put("result", "success");
		} else {
			resultMap.put("result", "fail");
		}
		
		return resultMap;
		
	}
	
	@DeleteMapping("/video/delete/{id}")
	public Map<String, String> deletePost(
			@PathVariable("id") int id
			, HttpSession session) {
		
		Map<String, String> resultMap = new HashMap<>();
		int userId = (Integer)session.getAttribute("userId");
		
		if(videoPostService.deleteVideoPost(id, userId)) {
			resultMap.put("result", "success");
		} else {
			resultMap.put("result", "fail");
		}
		
		return resultMap;
	}

}
