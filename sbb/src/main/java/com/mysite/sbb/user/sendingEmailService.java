package com.mysite.sbb.user;

import java.io.UnsupportedEncodingException;


import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import jakarta.mail.Message.RecipientType;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class sendingEmailService {
	
	private final JavaMailSender javaMailSender;
	private String code;
	
	public MimeMessage createMessage(String to) throws UnsupportedEncodingException, MessagingException{

		MimeMessage message = javaMailSender.createMimeMessage();
		
		//Recipient 수신자 종류 : TO, CC, BCC
		message.addRecipients(RecipientType.TO, to);
		message.setSubject("FUWAFUWA FANMADE T1 CALENDAR 임시비밀번호 발급");
		
        String msg = "";
        msg += "<div style='margin:100px;'>";	        
        msg += "<h3 style='color:black;'>FUWAFWUA FANMADE T1 CALENDAR에서 새로운 임시비밀번호를 발급하였습니다.</h3>";
        msg += "<h3 style='color:black;'>아래 임시비밀번호를 사용하여 로그인하시고 비밀번호를 변경하세요.</h3>";
        msg +=
            "<div align='center' style='border:1px solid black; font-family:verdana';>";
        msg += "<div style='font-size:130%'>";
        msg += "<strong>";
        msg += code + "</strong><div><br/> ";
        msg += "</div>";
        message.setText(msg, "utf-8", "html");
        message.setFrom(new InternetAddress("soyeonmail24@gmail.com", "FUWAFUWA_FANMADE_T1_CALENDAR"));
			MimeMessage message = javaMailSender.createMimeMessage();
			
			//Recipient 수신자 종류 : TO, CC, BCC
			message.addRecipients(RecipientType.TO, to);
			message.setSubject("FUWAFUWA FANMADE T1 CALENDAR 임시비밀번호 발급");
			
	        String msg = "";
	        msg += "<div style='margin:100px;'>";	        
	        msg += "<h3 style='color:black;'>FUWAFWUA FANMADE T1 CALENDAR에서 새로운 임시비밀번호를 발급하였습니다.</h3>";
	        msg += "<h3 style='color:black;'>아래 임시비밀번호를 사용하여 로그인하시고 비밀번호를 변경하세요.</h3>";
	        msg +=
	            "<div align='center' style='border:1px solid black; font-family:verdana';>";
	        msg += "<div style='font-size:130%'>";
	        msg += "<strong>";
	        msg += code + "</strong><div><br/> ";
	        msg += "</div>";
	        message.setText(msg, "utf-8", "html");
	        message.setFrom(new InternetAddress("soyeonmail24@gmail.com", "FUWAFUWA_FANMADE_T1_CALENDAR"));
        return message;
	}
	
	public void sendMessage(String to, String code) {
		this.code=code;
		MimeMessage message;
		try {
			message = createMessage(to);
			
		}catch(UnsupportedEncodingException | MessagingException e) {
            e.printStackTrace();
            throw new EmailException("이메일 생성 에러");
        }
		try {
			javaMailSender.send(message);
		}catch (MailException e) {
            e.printStackTrace();
            throw new EmailException("이메일 전송 에러");
        }
	}
		
		
	

}
