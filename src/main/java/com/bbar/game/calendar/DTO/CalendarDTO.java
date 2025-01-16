package com.bbar.game.calendar.DTO;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CalendarDTO {
	
	private int scheduleId;
	private String title;
	private String contents;
	
	private LocalDate startDate;
	private LocalDate endDate;

}
