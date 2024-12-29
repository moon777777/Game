package com.bbar.game.mail;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class MailService {
	
    private final JavaMailSender javaMailSender;
    private static final String senderEmail = "moonyj6380@gmail.com";
    private int number;

    public MailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }
    
    private Map<String, Integer> emailCode = new HashMap<>();
    
    public void createNumber() {
    	Random random = new Random();
    	number = random.nextInt(900000) + 100000;
    }
    
    public MimeMessage createMail(String mail) {
    	createNumber();
    	MimeMessage message = javaMailSender.createMimeMessage();
    	
    	try {
    	    MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
    	    helper.setFrom(senderEmail);
    	    helper.setTo(mail); 	    
    	    helper.setSubject("BBAR 회원 가입 인증 코드");
    	    
    	    helper.setText(
    	    "<h3>요청하신 인증 번호입니다.</h3><h1>" 
    	    + number 
    	    + "</h1><h3>감사합니다.</h3>", true);
    	    
    	} catch (MessagingException e) {
    		e.printStackTrace();
    	}
    	
    	return message;
    	
    }
    // 메일을 실제로 전송
    public int sendMail(String mail) {
        MimeMessage message = createMail(mail);
        javaMailSender.send(message); // 결국 얘를 호출
        emailCode.put(mail, number);
        return number;
    }
    
    public boolean verifyCode(String email, int code) {
    	Integer getCode = emailCode.get(email);
    	return getCode != null && getCode == code;
    }

}
