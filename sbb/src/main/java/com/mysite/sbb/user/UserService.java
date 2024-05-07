package com.mysite.sbb.user;

import java.util.Optional;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import com.mysite.sbb.CommonUtil;
import com.mysite.sbb.DataNotFoundException;

import jakarta.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class UserService {
	
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final CommonUtil commonUtil;
	private final sendingEmailService sendingEmailService;
	
	public SiteUser create(String username, String email, String password) {
		SiteUser user = new SiteUser();
		user.setUsername(username);
		user.setEmail(email);
		//BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		user.setPassword(passwordEncoder.encode(password));
		this.userRepository.save(user);
		return user;
	
	}
	
	public SiteUser getUser(String username) {
		Optional<SiteUser> siteUser = this.userRepository.findByUsername(username);
		
		if(siteUser.isPresent()) {
			return siteUser.get();
		} else {
			throw new DataNotFoundException("siteuser not found");
		}
	}
	
	@Transactional
	public void modifyPassword(String email) throws EmailException{
		
		String password = commonUtil.createTmpPassword();
		SiteUser user = userRepository.findByEmail(email).orElseThrow();
	    //orElseThrow(() -> new DataNotFoundException("해당 이메일의 유저가 없습니다."));
		user.setPassword(passwordEncoder.encode(password));
		userRepository.save(user);
		sendingEmailService.sendMessage(email,password);
	}
	
	
	public void updatePassword(String password1, String password2, String email) {
		SiteUser siteUser = this.userRepository.findByEmail(email).orElseThrow(() -> new DataNotFoundException("이메일이 존재하지 않습니다."));
		siteUser.setPassword(passwordEncoder.encode(password2));
		this.userRepository.save(siteUser);
	}
	
	//회원탈퇴
	@Transactional
	public Boolean resign(String email, String password) {
		SiteUser siteUser = this.userRepository.findByEmail(email).orElseThrow(() -> new DataNotFoundException("이메일이 존재하지 않습니다."));
		
		if(passwordEncoder.matches(password, siteUser.getPassword())) {
			this.userRepository.delete(siteUser);
			SecurityContextHolder.clearContext();
			return true;
		} else {
			return false;
		}
	}
	

}
