package com.mysite.sbb.calendar;

import java.security.Principal;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RequestMapping("/calendar")
@RequiredArgsConstructor
@RestController
public class CalendarController {
	
	private final CalendarService calendarService;
	
	   @PreAuthorize("isAuthenticated()")
	   @PostMapping("/create")
	   public ResponseEntity<?> createSchedule(@RequestBody CalendarDTO calendarDTO) {
	        
	         Calendar saveCalendar = this.calendarService.createSchedule(calendarDTO);
	         
	         
	         return ResponseEntity.ok(saveCalendar);
	         
	   }
	   
	   @PreAuthorize("isAuthenticated()")
	   @PostMapping("/delete")
	   public ResponseEntity<?> deleteSchedule(@RequestBody CalendarDTO calendarDTO, Principal principal) {
	      
	         this.calendarService.deleteSchedule(calendarDTO);
	         
	         
	         return ResponseEntity.ok("삭제 완료");
	         
	   }
	   
	   @GetMapping("/getList")
	   public List<Map<String,Object>> getList(){
		   return calendarService.getList();
	   }
	   
	   

}
