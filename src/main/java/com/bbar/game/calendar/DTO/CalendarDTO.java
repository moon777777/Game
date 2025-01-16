package com.bbar.game.calendar.DTO;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CalendarDTO {
	
	private int scheduleId;
	private String title;
	private String contents;
	
	private LocalDateTime startDate;
	private LocalDateTime endDate;

}
