package com.bbar.game.user;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;

@RequestMapping("/user")
@Controller
public class UserController {
	
	@GetMapping("/join-view")
	public String inputJoin() {
		return "user/join";
	}
	
	@GetMapping("/login-view")
	public String inputLogin(HttpSession session) {
		
		session.removeAttribute("userId");
		session.removeAttribute("userNickname");
		
		return "user/login";
	}
	
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		
		session.removeAttribute("userId");
		session.removeAttribute("userNickname");
		
		return "redirect:/user/login-view";
	}
	
	@GetMapping("/edit-password")
	public String editPassword() {
		return "user/password";
	}
	
	@GetMapping("/edit-profile")
	public String editProfile() {
		return "user/editProfile";
	}
}
