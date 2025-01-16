package com.bbar.game.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.bbar.game.common.FileManager;
import com.bbar.game.interceptor.PermissionInterceptor;

@Configuration
public class WebMyConfig implements WebMvcConfigurer {
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/images/**")
		.addResourceLocations("file:///" + FileManager.FILE_UPLOAD_PATH + "/");
	}
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		
		registry.addInterceptor(new PermissionInterceptor())
		.addPathPatterns("/**")
		.excludePathPatterns("/user/logout", "/static/**", "/images/**", "/post/list-view", "/post/calendar-view"
				,"/post/calendar-data", "/post/detail-view/{id}", "/post/video-list", "/post/detail-video/{id}"
				, "/user/edit-profile");
	}
	
	
}
