package com.bbar.game.user;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bbar.game.common.FileManager;
import com.bbar.game.user.domain.User;
import com.bbar.game.user.service.UserService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/user")
public class UserRestController {
	
	private UserService userService;
	
	public UserRestController(UserService userService) {
		this.userService = userService;
	}
	
	@PostMapping("/join")
	public Map<String, String> join(
			@RequestParam("loginId") String loginId
			, @RequestParam("password") String password
			, @RequestParam("nickname") String nickname){
		
		Map<String, String> resultMap = new HashMap<>();

		if(userService.addUser(loginId, password, nickname)) {
			resultMap.put("result", "success");
		} else {
			resultMap.put("result", "fail");
		}
		return resultMap;
	}
	
	@GetMapping("/duplicate-id")
	@ResponseBody
	public Map<String, Boolean> isDuplicateLoginId(@RequestParam("loginId") String loginId) {
		
		boolean isDuplicate = userService.isDuplicateLoginId(loginId);
		
		Map<String, Boolean> resultMap = new HashMap<>();
		
		if(isDuplicate) {
			resultMap.put("isDuplicate", true);
		} else {
			resultMap.put("isDuplicate", false);
		}
				
		return resultMap;
	}
	
	@GetMapping("/duplicate-name")
	@ResponseBody
	public Map<String, Boolean> isDuplicateNickname(@RequestParam("nickname") String nickname) {
		
		boolean isDuplicate = userService.isDuplicateNickname(nickname);
		
		Map<String, Boolean> resultMap = new HashMap<>();
		
		if(isDuplicate) {
			resultMap.put("isDuplicate", true);
		} else {
			resultMap.put("isDuplicate", false);
		}
				
		return resultMap;
	}
	
	@PostMapping("/login")
	public Map<String, String> login(
			@RequestParam("loginId") String loginId
			, @RequestParam("password") String password
			, HttpSession session) {
		
		User user = userService.getUser(loginId, password);
		
		Map<String, String> resultMap = new HashMap<>();
		
		if(user != null) {
			session.setAttribute("userId", user.getId());
			session.setAttribute("userNickname", user.getNickname());
			session.setAttribute("userProfile", user.getImagePath());
			
			resultMap.put("result", "success");
		} else {
			resultMap.put("result", "fail");
		}
		return resultMap;
	}
	
	@PutMapping("/nickname/edit")
	public Map<String, String> updateNickname(
			@RequestParam("nickname") String nickname
			, HttpSession session) {
		
		int userId = (Integer)session.getAttribute("userId");
		
		Map<String, String> resultMap = new HashMap<>();
		
		if(userService.updateNickname(userId, nickname)) {
			session.setAttribute("userNickname", nickname);
			resultMap.put("result", "success");
		} else {
			resultMap.put("result", "fail");
		}
		return resultMap;
	}
	
	@PutMapping("/profile/edit")
	public Map<String, String> updateProfile(
			@RequestParam("imageFile") MultipartFile file
			, HttpSession session) {
		
		int userId = (Integer)session.getAttribute("userId");
		
		Map<String, String> resultMap = new HashMap<>();
		String filePath = FileManager.saveFile(userId, file);
		
		if(userService.updateFile(userId, file)) {
			session.setAttribute("userProfile", filePath); 
			resultMap.put("result", "success");
		} else {
			resultMap.put("result", "fail");
		}
		return resultMap;
	}

	
	@PostMapping("/password/change")
	public Map<String, String> chagnePassword(
			@RequestParam("loginId") String loginId
			, @RequestParam("password") String password){
		
		Map<String, String> resultMap = new HashMap<>();
		if(userService.changePassword(loginId, password)) {
			resultMap.put("result", "success");
		} else {
			resultMap.put("result", "fail");
		}
		return resultMap;
	}
	
	

}
