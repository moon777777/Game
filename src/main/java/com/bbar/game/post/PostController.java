package com.bbar.game.post;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/post")
public class PostController {
	
	@GetMapping("/profile")
	public String profile() {
		return "post/profile";
	}
	
	@GetMapping("/create-view")
	public String inputBoard() {
		return "post/input";
	}
	
	@GetMapping("/list-view")
	public String listBoard() {
		return "post/list";
	}

}
