package com.bbar.game.calendar.service;

import org.springframework.stereotype.Service;

import com.bbar.game.calendar.repository.CalendarRepository;

@Service
public class CalendarService {
	private CalendarRepository calendarRepository;
	
	public void CalendarService(CalendarRepository calendarRepository) {
		this.calendarRepository = calendarRepository;
	}
	
	
}
