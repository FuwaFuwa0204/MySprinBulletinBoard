package com.mysite.sbb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
//2개 이상의 프로퍼티 사용
@PropertySource(value = {"classpath:application-prod.properties","classpath:application.properties"}, ignoreResourceNotFound=true)
public class SbbApplication {

	public static void main(String[] args) {
		SpringApplication.run(SbbApplication.class, args);
	}

}
