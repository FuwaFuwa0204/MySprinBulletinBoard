package com.mysite.sbb.calendar;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CalendarService {
	
	private final CalendarRepository calendarRepository;
	
	public Calendar createSchedule(CalendarDTO calendarDTO) {
		
		Calendar build = Calendar.builder()
				.calendarTitle(calendarDTO.getCalendarTitle())
				.calendarStart(calendarDTO.getCalendarStart())
				.calendarEnd(calendarDTO.getCalendarEnd())
				.build();
		
		Calendar save = this.calendarRepository.save(build);
		
		return save;
		
	}
	
	public void deleteSchedule(CalendarDTO calendarDTO) {
		
		Calendar calendar = this.calendarRepository.findById(calendarDTO.getId()).orElseThrow();
		
		this.calendarRepository.delete(calendar);
		
	}
	

	public List<Map<String, Object>> getList(){
		
		List<Calendar> calendarList = this.calendarRepository.findAll();

		List<Map<String,Object>> eventList = new ArrayList<Map<String,Object>>();
		
		for(int i=0; i<calendarList.size(); i++) {
		Map<String,Object> event = new HashMap<String,Object>();
		event.put("id", calendarList.get(i).getId());
		event.put("title",calendarList.get(i).getCalendarTitle());
		event.put("start",calendarList.get(i).getCalendarStart());
		event.put("end",calendarList.get(i).getCalendarEnd());
		eventList.add(event);
		}
		
		return eventList;
		
		
	}
	


}
