package com.bbar.game.common;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class MD5HashingEncoder {
	
	public static String encode(String message, String salt) {
		
		String result = "";
		try {
			MessageDigest messageDigest =  MessageDigest.getInstance("md5");
			
			String saltMessage = salt + message;
			
			byte[] bytes = saltMessage.getBytes();
			
			messageDigest.update(bytes);
			
			byte[] digest = messageDigest.digest();
			
			for(int i = 0; i < digest.length; i++) {
				
				 result += Integer.toHexString(digest[i] & 0xff);//논리연산 비트연산
			}
			
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}	
		return result;
	}
	
	 public static String createSalt() {
		 SecureRandom random = new SecureRandom();
		 
		 byte[] salt = new byte[16];
		 random.nextBytes(salt);
		 StringBuilder stringBuild = new StringBuilder();
		 
		 for (byte a : salt) {
			    stringBuild.append(String.format("%02x", a));
			}
		 return stringBuild.toString();
	 }
}
