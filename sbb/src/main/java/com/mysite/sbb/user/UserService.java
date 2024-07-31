package com.mysite.sbb.user;

import java.io.IOException;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
   private final profileImageRepository profileImageRepository;
   private final PasswordEncoder passwordEncoder;
   private final CommonUtil commonUtil;
   private final sendingEmailService sendingEmailService;
   private final S3Uploader S3Uploader;
   
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
   
   public Page<SiteUser> getUserList(int Page, String kw){
      Pageable pageable = PageRequest.of(Page, 10);
      return this.userRepository.findAllByUsername(pageable,kw);
   }
   
   @Transactional
   public void modifyPassword(String email) throws EmailException{
      
      String password = commonUtil.createTmpPassword();
      SiteUser user = userRepository.findByEmail(email).orElseThrow(() -> new DataNotFoundException("해당 이메일의 유저가 없습니다."));
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
   public void resign(String email, String password) {
      SiteUser siteUser = this.userRepository.findByEmail(email).orElseThrow(() -> new DataNotFoundException("이메일이 존재하지 않습니다."));
      
      if(passwordEncoder.matches(password, siteUser.getPassword())) {
         this.userRepository.delete(siteUser);
         SecurityContextHolder.clearContext();
      } else {
         throw new DataNotFoundException("비밀번호가 일치하지 않습니다.");
      }
   }
   
   public void userManageResign(String username) {
      
      SiteUser user = this.userRepository.findByUsername(username).orElseThrow();
      this.userRepository.delete(user);
   }
   
   public String findByEmail(String email) {
      SiteUser user = this.userRepository.findByEmail(email).orElseThrow(()-> new DataNotFoundException("이메일이 존재하지 않습니다."));
   
      
      return user.getUsername();
            
   }
   
   //aws s3 이미지 관련 메소드
   
   
   public void upload(profileImageUploadDTO profileImageUploadDTO, String username) throws IOException {
	   
	   SiteUser user = this.userRepository.findByUsername(username).orElseThrow();
	   
	  
	   profileImage image = this.profileImageRepository.findBySiteUser(user);
	   
	   if(image != null) {
		   String url = this.S3Uploader.updateFile(profileImageUploadDTO.getFile(), image.getUrl(), username);
		   image.updateUrl(url);
           this.profileImageRepository.save(image);

		   
	   }else { 
	   String url = this.S3Uploader.upload(profileImageUploadDTO.getFile(),"profileimages");
	
	   image = profileImage.builder()
               .siteUser(user)
               .url(url)
               .build();
	   
	   this.profileImageRepository.save(image);
	   
	   }

	   
	   this.profileImageRepository.save(image);
   }
   
   public profileImageResponseDTO getProfileImage(String username) {
	   SiteUser user = this.userRepository.findByUsername(username).orElseThrow();
	   profileImage image = this.profileImageRepository.findBySiteUser(user);
	   
	   if(image == null) {
		   //aws s3 경로.
           return profileImageResponseDTO.builder()
                   .url("https://fuwafuwacal.s3.ap-northeast-2.amazonaws.com/pngwing.com+(2).png")
                   .build();
	   }else {
           return profileImageResponseDTO.builder()
                   .url(image.getUrl())
                   .build();
	   }
   }
   
   public profileImage findProfileImageByUser(String username) {
	   SiteUser user = this.userRepository.findByUsername(username).orElseThrow();
	   profileImage image = this.profileImageRepository.findBySiteUser(user);
	   return image;
   }
   
   public void deleteImage(SiteUser user) {
	   String fileName = getProfileImage(user.getUsername()).getUrl().substring(1);
	   this.S3Uploader.deleteFile(fileName);
	   profileImage image = this.profileImageRepository.findBySiteUser(user);
	   this.profileImageRepository.delete(image);
   }
   

}
