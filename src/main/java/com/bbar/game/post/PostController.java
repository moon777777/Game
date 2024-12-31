package com.bbar.game.post;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bbar.game.post.domain.Post;
import com.bbar.game.post.service.PostService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/post")
public class PostController {
	
	private PostService postService;
	
	public PostController(PostService postService) {
		this.postService = postService;

	}
	
	@GetMapping("/profile")
	public String profile() {
		return "post/profile";
	}
	
	@GetMapping("/create-view")
	public String inputBoard() {
		return "post/input";
	}
	
	@GetMapping("/list-view")
	public String listBoard(Model model
			, HttpSession session) {
		
		int userId = (Integer)session.getAttribute("userId");
		
		List<Post> postList = postService.getPostList(userId);
		
		model.addAttribute("postList", postList);
		return "post/list";
	}

}
