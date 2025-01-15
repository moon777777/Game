package com.bbar.game.post;

import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bbar.game.post.dto.BoardDTO;
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
	
	@GetMapping("/update/{postId}")
	public String updateBoard(@PathVariable("postId") int postId
			, Model model
			, HttpSession session) {
		
		Integer userId = (Integer) session.getAttribute("userId");
		BoardDTO post = postService.getPost(postId, userId);
		model.addAttribute("post", post);
		return "post/update";
	}
	
	@GetMapping("/detail-view/{id}")
	public String deatailBoard(@PathVariable("id") int id
			, Model model
			, HttpSession session) {
		Integer userId = (Integer) session.getAttribute("userId");
		BoardDTO post = postService.getPost(id, userId);
		model.addAttribute("post", post);
		return "post/detail";
	}
	
	@GetMapping("/list-view")
	public String paging(
			@PageableDefault(page = 1) Pageable pageable
			, Model model) {
		
		Page<BoardDTO> postPage = postService.paging(pageable);

		LocalDate localDate = LocalDate.now();
		model.addAttribute("localDate", localDate);
		
		int blockLimit = 3;
		int startPage = ((pageable.getPageNumber() - 1) / blockLimit) * blockLimit + 1;
		int endPage = Math.min((startPage + blockLimit - 1), postPage.getTotalPages());
		
		model.addAttribute("postPage", postPage);
		model.addAttribute("startPage", startPage);
    	model.addAttribute("endPage", endPage);
		return "post/list";
		
	}
	
	@GetMapping("/video/url-view")
	public String inputYoutube() {
		return "post/url";
	}
	
	@GetMapping("/calendar-view")
	public String calendar() {
		return "post/calendar";
	}
	
	
	

}
