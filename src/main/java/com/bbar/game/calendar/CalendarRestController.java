package com.bbar.game.calendar;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bbar.game.calendar.service.CalendarService;

@RestController
@RequestMapping("/post")
public class CalendarRestController {
	
	private CalendarService calendarService;
	
	public CalendarRestController(CalendarService calendarService) {
		this.calendarService = calendarService;
	}
	
	@PostMapping("/schedule")
	public Map<String, String> addSchedule(
			@RequestParam("title") String title
			, @RequestParam("contents") String contents
			, @RequestParam("startDate") String startDate
			, @RequestParam("endDate") String endDate){
		
		Map<String, String> resultMap = new HashMap<>();
		
		if(calendarService.addSchedule(title, contents, startDate, endDate)) {
			resultMap.put("result", "success");
		} else {
			resultMap.put("result", "fail");
		}
		return resultMap;
	}

}
