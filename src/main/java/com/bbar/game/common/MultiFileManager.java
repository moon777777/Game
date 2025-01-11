package com.bbar.game.common;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.web.multipart.MultipartFile;

public class MultiFileManager {
	
public static final String FILE_UPLOAD_PATH = "C:\\Moon777\\SpringProject\\upload\\multiZZZ";
	
	public static String saveFile(int userId, int postId, MultipartFile file) {
		
		if(file == null) {
			return null;
		}
		
		String directoryName = "/" + userId + "_" + postId + "_" + System.currentTimeMillis();
		String directoryPath = FILE_UPLOAD_PATH + directoryName;
		
		// 디렉토리 만들기
		File directory = new File(directoryPath);
		if(!directory.mkdir()) {
			return null;
		}
		
		// 파일 저장
		String filePath = directoryPath + "/" + file.getOriginalFilename();
		
		try {
			byte[] bytes = file.getBytes();
			Path path = Paths.get(filePath);			
			Files.write(path, bytes);
		} catch (IOException e) {
			
			e.printStackTrace();
			
			// 파일 저장 실패
			return null;
		}
		
		return "/images" + directoryName + "/" + file.getOriginalFilename();
	}
	
	

}
