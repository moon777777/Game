package com.bbar.game.post;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bbar.game.comment.DTO.CommentDTO;
import com.bbar.game.comment.service.CommentService;
import com.bbar.game.post.dto.BoardDTO;
import com.bbar.game.post.service.PostService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/post")
public class PostController {
	
	private PostService postService; 
	private CommentService commentService;
	
	public PostController(PostService postService, CommentService commentService) {
		this.postService = postService;
		this.commentService = commentService;
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
		
		LocalDate localDate = LocalDate.now();
		model.addAttribute("localDate", localDate);
		
		List<BoardDTO> postList = postService.getPostList(userId);
		
		model.addAttribute("postList", postList);
		return "post/list";
	}
	
	@GetMapping("/detail-view/{id}")
	public String deatilBoard(@PathVariable("id") int id
			, Model model
			, HttpSession session) {
		Integer userId = (Integer) session.getAttribute("userId");
		BoardDTO post = postService.getPost(id, userId);
		List<CommentDTO> commentList = commentService.getCommentList(id, userId);
		model.addAttribute("post", post);
		return "post/detail";
	}

}
