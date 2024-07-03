package com.mysite.sbb.calendar;


import org.springframework.data.jpa.repository.JpaRepository;


public interface CalendarRepository extends JpaRepository<Calendar,Integer> {
	
}
