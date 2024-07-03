package com.mysite.sbb.calendar;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CalendarDTO {
	
    private Integer id;

    private String calendarTitle;
    
    private String calendarStart;

    private String calendarEnd;
    

}
