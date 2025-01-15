package com.bbar.game.calendar.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bbar.game.calendar.domain.Calendar;

public interface CalendarRepository extends JpaRepository<Calendar, Integer> {

}
