package com.bbar.game.mail;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mail")
public class MailRestController {
	
	private final MailService mailService;

    public MailRestController(MailService mailService) {
        this.mailService = mailService;
    }
    
    @PostMapping("/send")
    public Map<String, String> sendMail(@RequestParam("email") String email) {
    	 Map<String, String> resultMap = new HashMap<>();
    	 
    	 try {
    		 mailService.sendMail(email);
    		 resultMap.put("result", "success");
    	 } catch(Exception e) {
    		 resultMap.put("result", "fail");
    	 }
    	 return resultMap;
        
    }

}
