package com.bbar.game.user;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.bbar.game.user.domain.User;
import com.bbar.game.user.service.UserService;

import jakarta.servlet.http.HttpSession;

@RequestMapping("/user")
@Controller
public class UserController {
	
	private UserService userService;
	
	public UserController(UserService userService) {
		this.userService = userService;
	}
	
	@GetMapping("/join-view")
	public String inputJoin() {
		return "user/join";
	}
	
	@GetMapping("/login-view")
	public String inputLogin(HttpSession session) {
		return "user/login";
	}
	
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		
		session.removeAttribute("userId");
		session.removeAttribute("userNickname");
		
		return "redirect:/user/login-view";
	}
	
	@GetMapping("/edit-profile")
	public String editProfile(HttpSession session, Model model) {
		int userId = (Integer)session.getAttribute("userId");
		User profile = userService.getUser(userId);
		
		model.addAttribute("profile", profile);
		return "user/editProfile";
	}
	
	@GetMapping("/edit-password")
	public String editPassword() {
		return "user/editPassword";
	}
}
