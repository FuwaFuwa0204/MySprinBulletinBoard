package com.mysite.sbb;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {
	@GetMapping("/hello") //hello url과 밑의 hello 메서드 매핑  
	@ResponseBody
	public String hello(){
		return "날 위한 점프 투 스프링";
	}
	

}
