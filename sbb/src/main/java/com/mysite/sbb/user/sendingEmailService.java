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
			message.setSubject("sbb 임시 비밀번호 메일이 도착했습니다.");
			
	        String msg = "";
	        msg += "<div style='margin:100px;'>";
	        msg +=
	            "<div align='center' style='border:1px solid black; font-family:verdana';>";
	        msg += "<h3 style='color:blue;'>sbb 게시판 임시 비밀번호입니다.</h3>";
	        msg += "<div style='font-size:130%'>";
	        msg += "CODE : <strong>";
	        msg += code + "</strong><div><br/> ";
	        msg += "</div>";
	        message.setText(msg, "utf-8", "html");
	        message.setFrom(new InternetAddress("발신자 이메일 주소", "sbb_Admin"));

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
