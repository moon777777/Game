package com.bbar.game.calendar.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.bbar.game.calendar.DTO.CalendarDTO;
import com.bbar.game.calendar.domain.Calendar;
import com.bbar.game.calendar.repository.CalendarRepository;

@Service
public class CalendarService {
	private CalendarRepository calendarRepository;
	
	public CalendarService(CalendarRepository calendarRepository) {
		this.calendarRepository = calendarRepository;
	}
	
	
	public boolean addSchedule(String title, String conetents, String startDate, String endDate) {
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate startDay = LocalDate.parse(startDate, formatter);
		LocalDate endDay = LocalDate.parse(endDate, formatter);
		
		Calendar calendar = Calendar.builder()
		.title(title)
		.contents(conetents)
		.startDate(startDay)
		.endDate(endDay)
		.build();
		
		try {
			calendarRepository.save(calendar);
			return true;
		} catch(Exception e) {
			return false;
		}
	}
	
	public List<CalendarDTO> getSchedule() {
		List<Calendar> calendarList = calendarRepository.findAll();
		
		List<CalendarDTO> scheduleList = new ArrayList<>();
		
		
		for(Calendar calendar:calendarList) {
			
			CalendarDTO schedule = CalendarDTO.builder()
			.scheduleId(calendar.getId())
			.title(calendar.getTitle())
			.contents(calendar.getContents())
			.startDate(calendar.getStartDate())
			.endDate(calendar.getEndDate())
			.build();

			scheduleList.add(schedule);
		}
		
		return scheduleList;
	
	}
}
