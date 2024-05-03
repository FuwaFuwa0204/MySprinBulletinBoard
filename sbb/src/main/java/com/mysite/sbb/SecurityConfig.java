package com.mysite.sbb;

import org.springframework.context.annotation.Bean;



import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.mysite.sbb.user.CustomOAuth2UserService;

import lombok.RequiredArgsConstructor;

import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@RequiredArgsConstructor
//스프링의 환경설정 파일
@Configuration
//모든 요청 url이 스프링 시큐리티의 제어를 받도록
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
	
	   private final CustomOAuth2UserService customOAuth2UserService;
	@Bean
	//이 클래스로 모든 요청 url에 필터 작용을 시켜 url별로 특별 설정을 할 수 있게 된다.
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
		http.authorizeHttpRequests((authorizeHttpRequests)->authorizeHttpRequests.requestMatchers(new AntPathRequestMatcher("/**"))
				.permitAll())
		.csrf((csrf)->csrf.ignoringRequestMatchers(new AntPathRequestMatcher("/h2-console/**")))
		.headers((headers)->headers.addHeaderWriter(new XFrameOptionsHeaderWriter(XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN)))
		.formLogin((formLogin)->formLogin.loginPage("/user/login").defaultSuccessUrl("/"))
		//userInfoEndpodint로 소셜로그인 성공시 OAuth2UserService로 실행
		.oauth2Login((oauth2Login) -> oauth2Login.defaultSuccessUrl("/").userInfoEndpoint(userInfoEndpoint -> userInfoEndpoint.userService(customOAuth2UserService)))
		.logout((logout)->logout.logoutRequestMatcher(new AntPathRequestMatcher("/user/logout")).logoutSuccessUrl("/").invalidateHttpSession(true));
		
		return http.build();
	}
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{
		return authenticationConfiguration.getAuthenticationManager();
	}

}
