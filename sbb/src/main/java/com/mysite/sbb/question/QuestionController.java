package com.mysite.sbb.question;


import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;

import lombok.RequiredArgsConstructor;
import jakarta.validation.Valid;

import org.springframework.validation.BindingResult;


import com.mysite.sbb.user.SiteUser;
import com.mysite.sbb.user.UserService;
import com.mysite.sbb.user.profileImageUploadDTO;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.data.domain.Page;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import com.mysite.sbb.calendar.CalendarDTO;
import com.mysite.sbb.comment.Comment;
import com.mysite.sbb.comment.CommentDTO;


@RequiredArgsConstructor
@Controller
@RequestMapping("/question")
public class QuestionController {
	
	private final QuestionService questionService;
	private final UserService userService;
	
	@GetMapping("/list/{type}")
	public String list(Model model,@PathVariable String type, @RequestParam(value="page", defaultValue="0") int page, @RequestParam(value="kw", defaultValue="") String kw) {
		//name,value
		int category = switch(type) {
		case "qna" -> QuestionEnum.QNA.getStatus();
		case "calendar" -> QuestionEnum.CALENDAR.getStatus();
		default -> throw new RuntimeException("올바르지 않은 접근입니다.");
		};
		
		model.addAttribute("category",category);
		//이제 이 model 객체를 템플릿에서 활용한다.
		Page<Question> paging = this.questionService.getList(page, category, kw);
		model.addAttribute("paging",paging);
		model.addAttribute("kw",kw);
		model.addAttribute("calendarDTO", new CalendarDTO());
        
		if(category == 0) {
			return "question_list";
		} else {
			
			return "calendar";
		}
		
	}
	//댓글 페이징 부분
	@GetMapping(value= "/detail/{id}")
	public String detail(Model model, @PathVariable("id") Integer id) {
		//id로 조회한 question을 넣어준다.
		//detail/{id}로 들어갈때마다 getQuestion을 한다. -> 조회수 증가.
		Question question = this.questionService.getQuestion(id);
		//Page<Answer> answerPaging = this.answerService.getList(question,answerPage);
		questionImageResponseDTO image = this.questionService.findImage(question);
		//answer is null 조건 없었을 때 => List<Comment> questionComment = this.questionService.findByQuestionOrderByGrpDescSeqAsc(question);
		List<Comment> result = this.questionService.findByQuestionOrderByGrpAscSeqAsc(question);
		
		model.addAttribute("question",question);
		//model.addAttribute("answerPaging",answerPaging);
		model.addAttribute("image", image);
		//commentDto 생성해서 create
		model.addAttribute("commentDTO",new CommentDTO());
		model.addAttribute("questionComment",result);
		
		return "question_detail";
	}
	
	@PreAuthorize("isAuthenticated()")
	//버튼용
	@GetMapping("/create/{type}")
	public String questionCreate(Model model,QuestionForm questionForm,@PathVariable String type) {
		switch (type) {
		case "qna" -> model.addAttribute("category", "질문 작성");
		case "free" -> model.addAttribute("category", "자유게시판 작성");
		default -> throw new RuntimeException("올바르지 않은 접근입니다.");
	}
		return "question_form";
	}
	
	
	@PreAuthorize("isAuthenticated()")
	//질문등록용
	@PostMapping("/create/{type}")
	public String questionCreate(@Valid QuestionForm questionForm, BindingResult bindingResult, Principal principal,@PathVariable String type) throws IOException {
		
		SiteUser siteUser = this.userService.getUser(principal.getName());
		
		if(bindingResult.hasErrors()) {
			return"question_form";
		}
		
		int category = switch (type) {
		case "qna" -> QuestionEnum.QNA.getStatus();
		case "free" -> QuestionEnum.CALENDAR.getStatus();
		default -> throw new RuntimeException("올바르지 않은 접근입니다.");
	};  
	    this.questionService.create(questionForm.getSubject(),questionForm.getContent(), siteUser, category, questionForm.getFiles());
		return "redirect:/question/list/%s".formatted(type);
	}
	
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/modify/{id}")
	public String questionModify(@Valid QuestionForm questionForm, BindingResult bindingResult, Principal principal, @PathVariable("id") Integer id) throws IOException {
		
		if(bindingResult.hasErrors()) {
			return "question_form";
		}
		Question question = this.questionService.getQuestion(id);
		
		if(!question.getAuthor().getUsername().equals(principal.getName())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 권한이 없습니다.");
		}
		this.questionService.modify(question, questionForm.getSubject(), questionForm.getContent(), questionForm.getFiles());
		return String.format("redirect:/question/detail/%s",id);
	}

	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/modify/{id}")
	public String questionModify(QuestionForm questionForm, Principal principal, @PathVariable("id") Integer id,Model model) {

		Question question = this.questionService.getQuestion(id);
		
		if(!question.getAuthor().getUsername().equals(principal.getName())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 권한이 없습니다.");
		}
		
		questionForm.setSubject(question.getSubject());
		questionForm.setContent(question.getContent());
		return "question_form";
	}
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/delete/{id}")
	public String questionDelete(Principal principal, @PathVariable("id") Integer id) {
		Question question = this.questionService.getQuestion(id);
		/*
		if(!question.getAuthor().getUsername().equals(principal.getName())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"삭제 권한이 없습니다.");
		}
		*/
		this.questionService.delete(question);
		return "redirect:/";
	}
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/vote/{id}")
	public String questionVote(Principal principal, @PathVariable("id") Integer id) {
		Question question = this.questionService.getQuestion(id);
		SiteUser siteUser = this.userService.getUser(principal.getName());
		this.questionService.vote(question, siteUser);
		return String.format("redirect:/question/detail/%s",id);
	}
	


}
