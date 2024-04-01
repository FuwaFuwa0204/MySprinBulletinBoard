package com.mysite.sbb.question;

import java.util.List;



import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;

import lombok.RequiredArgsConstructor;
import jakarta.validation.Valid;

import org.springframework.validation.BindingResult;

import com.mysite.sbb.answer.AnswerForm;

@RequiredArgsConstructor
@Controller
@RequestMapping("/question")
public class QuestionController {
	
	private final QuestionService questionService;
	
	@GetMapping("/list")
	public String list(Model model) {
		//List<Question> questionList = this.questionRepository.findAll();
		List<Question> questionList = this.questionService.getList();
		//name,value
		model.addAttribute("questionList",questionList);
		//이제 이 model 객체를 템플릿에서 활용한다.
		//파일명
		return "question_list";
	}
	
	@GetMapping(value= "/detail/{id}")
	public String detail(Model model, @PathVariable("id") Integer id, AnswerForm answerForm) {
		Question question = this.questionService.getQuestion(id);
		model.addAttribute("question",question);
		return "question_detail";
	}
	
	//버튼용
	@GetMapping("/create")
	public String questionCreate(QuestionForm questionForm) {
		return "question_form";
	}
	//질문등록용
	@PostMapping("/create")
	public String questionCreate(@Valid QuestionForm questionForm, BindingResult bindingResult) {
		
		if(bindingResult.hasErrors()) {
			return"question_form";
		}
	    this.questionService.create(questionForm.getSubject(),questionForm.getContent());
		return "redirect:/question/list";
	}

}
