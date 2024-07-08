package com.mysite.sbb.user;

import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.server.ResponseStatusException;

import com.mysite.sbb.DataNotFoundException;
import com.mysite.sbb.comment.CommentService;
import com.mysite.sbb.question.QuestionService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.security.Principal;
import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;

@RequiredArgsConstructor
@Controller
@RequestMapping("/user")
public class UserController {
	
	private final UserService userService;
	private final QuestionService questionService;
	private final CommentService commentService;
	private final PasswordEncoder passwordEncoder;
	private final profileImageService profileImageService;
	
	@GetMapping("/signup")
	public String signup(UserCreateForm userCreateForm) {
		return "signup_form";
	}
	
	@PostMapping("/signup")
	public String signup(@Valid UserCreateForm userCreateForm, BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			return "signup_form";
		}
		if(!userCreateForm.getPassword1().equals(userCreateForm.getPassword2())) {
			bindingResult.rejectValue("password2", "passwordInCoreect","2개의 비밀번호가 일치하지 않습니다.");
			return "signup_form";
		}
		try {
			userService.create(userCreateForm.getUsername(), userCreateForm.getEmail(), userCreateForm.getPassword1());
		}catch(DataIntegrityViolationException e) {
			e.printStackTrace();
			bindingResult.reject("signupFailed","이미 등록된 사용자입니다.");
			return "signup_form";
		}catch(Exception e) {
			e.printStackTrace();
			bindingResult.reject("signupFailed",e.getMessage());
			return "signup_form";
		}
		
		
		return "redirect:/";
	}
	
	@GetMapping("/login")
	public String login() {
		return "login_form";
	}
	
	@GetMapping("/findpassword")
	public String sendTmpPassword(tmpPasswordForm tmpPasswordForm) {
		return "findpasswordForm";
	}
	
	@PostMapping("/findpassword")
	public String sendTmpPassword(@Valid tmpPasswordForm tmpPasswordForm, BindingResult bindingResult){
		if(bindingResult.hasErrors()) {
			return "findpasswordForm";
		}
        try {
        	userService.modifyPassword(tmpPasswordForm.getEmail());
        }catch(DataNotFoundException e) {
        	e.printStackTrace();
        	bindingResult.reject("emailNotFound", e.getMessage());
        	return "findpasswordForm";
        } catch(EmailException e) {
            bindingResult.reject("sendEmailFail", e.getMessage());
            return "findpasswordForm";
        }
        return "redirect:/";
	}
	
	
	@PreAuthorize("isAuthenticated()")	
	@GetMapping("/profile")
	public String profile(Model model, Principal principal) {
		String user = principal.getName(); //현재 로그인한 사람
		String userEmail = userService.getUser(user).getEmail();
		profileImageResponseDTO image = profileImageService.findImage(userEmail);
		
		model.addAttribute("username",user);
		model.addAttribute("userEmail",userEmail);
		model.addAttribute("userQuestion",questionService.findQuestionList(5, user));
		model.addAttribute("userComment",commentService.findCommentList(5, user));
		model.addAttribute("image",image);
		return "profile";
	}
	
	//이 권한 가진 사람만
	@Secured("ROLE_ADMIN")	
	@GetMapping("/userManage")

	public String userManage(Model model, Principal principal) {
		
		List<SiteUser> userList = this.userService.getUserList();
		
		model.addAttribute("userList",userList);
		
		return "userManage";
	}
	
	@Secured("ROLE_ADMIN")	
	@GetMapping("/userManage/resign/{username}")
	@ResponseBody
	public ResponseEntity<?> userManageResign(@PathVariable String username) {
		
		this.userService.userManageResign(username);
		
		return ResponseEntity.ok("삭제성공");
	}
	
	@PreAuthorize("isAuthenticated()")	
	@GetMapping("/updatepassword")
	public String updatePassword(updatePasswordForm updatePasswordForm) {
		return "updatepassword";
	}
	
	@PreAuthorize("isAuthenticated()")	
	@PostMapping("/updatepassword")
	public String updatePassword(@Valid updatePasswordForm updatePasswordForm, BindingResult bindingResult, Principal principal) {
		if(bindingResult.hasErrors()) {
			return "updatepassword";
		}
		
		SiteUser user = this.userService.getUser(principal.getName());
		String password1 = updatePasswordForm.getPassword1();
		String password2 = updatePasswordForm.getPassword2();
		String email = updatePasswordForm.getEmail();
		
		if(!user.getUsername().equals(principal.getName())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "변경 권한이 없습니다.");
		}
		
		if(!email.equals(user.getEmail())) {
			bindingResult.rejectValue("email","이메일이 같지 않습니다.");
		}

		if(!passwordEncoder.matches(password1,user.getPassword())) {
				bindingResult.rejectValue("password1","비밀번호가 맞지않습니다.");
			}
			
		this.userService.updatePassword(password1,password2,email);
		return "redirect:/user/profile";
	}
	
	@PreAuthorize("isAuthenticated()")	
	@GetMapping("/resign")
	public String resign(resignForm resignForm) {
		return "resign";
	}
	
	@PreAuthorize("isAuthenticated()")	
	@PostMapping("/resign")
	public String resign(@Valid resignForm resignForm, BindingResult bindingResult, Principal principal) {
		
		SiteUser user = this.userService.getUser(principal.getName());
		String email = user.getEmail();
		//입력으로 받아들이는 건 폼 클래스에서 가져오기
		String password = resignForm.getPassword();
		
		if(this.userService.resign(email,password)==true) {
			
			return "redirect:/user/logout";
			
		}else {
			return "resign";
		}
		
	}
	
	
	@PostMapping("/profileImage")
	public String imageUpload(@ModelAttribute profileImageUploadDTO profileImageUploadDTO, Principal principal){
		
		String email = this.userService.getUser(principal.getName()).getEmail();
		
		profileImageService.upload(profileImageUploadDTO, email);
		return "redirect:/user/profile";
	}
	
	
	

}
