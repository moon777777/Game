package com.bbar.game.common;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.web.multipart.MultipartFile;

public class FileManager {
	
public static final String FILE_UPLOAD_PATH = "C:\\Moon777\\SpringProject\\upload\\zzz";
	
	public static String saveFile(int userId, MultipartFile file) {
		
		if(file == null) {
			return null;
		}
		
		
		String directoryName = "/" + userId + "_" + System.currentTimeMillis();
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
	
	public static boolean removeFile(String filePath) {
		
		if(filePath == null) {
			return false;
		}
		
		String fullFilePath = FILE_UPLOAD_PATH + filePath.replace("/images", "");
		
		Path path = Paths.get(fullFilePath);
		
		// 디렉토리 경로 /C:\\Moon777\\SpringProject\\upload\\
		Path directoryPath = path.getParent();
		
		try {
			Files.delete(path);
			Files.delete(directoryPath);
			
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			return false;
		}
	}
	
	// 게시글 이미지 저장
	public static String saveFiles(int userId, int postId, MultipartFile file) {
		
		if(file == null) {
			System.err.println("업로드된 파일이 null입니다.");
			return null;
		}
		
		String directoryName = "/" + userId + "_" +  "_" + System.currentTimeMillis();
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
