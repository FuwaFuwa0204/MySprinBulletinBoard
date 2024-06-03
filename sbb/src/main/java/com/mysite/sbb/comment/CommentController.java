
package com.mysite.sbb.comment;

import java.security.Principal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.mysite.sbb.answer.AnswerService;
import com.mysite.sbb.question.Question;
import com.mysite.sbb.answer.Answer;
import com.mysite.sbb.question.QuestionService;
import com.mysite.sbb.user.SiteUser;
import com.mysite.sbb.user.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequestMapping("/comment")
@RequiredArgsConstructor
//JSON 데이터 사용하니까
@RestController
public class CommentController {
   
   private final QuestionService questionService;
   private final AnswerService answerService;
   private final UserService userService;
   private final CommentService commentService;
   
   /*
   @PreAuthorize("isAuthenticated()")
   @GetMapping("/create/question/{id}")
   //GetMapping에도 CommentForm 추가해줘야한다.
   public String createQuestionComment(CommentForm commentForm) {
      
      return "comment_form";
   }
   */
   /*
   @PreAuthorize("isAuthenticated()")
   @PostMapping("/create/question/{id}")
   public String createQuestionComment(Model model, @PathVariable("id") Integer id, @Valid CommentForm commentForm, BindingResult bindingResult, Principal principal) {
      
      Question question = this.questionService.getQuestion(id);
      SiteUser siteUser = this.userService.getUser(principal.getName());
      
      if(bindingResult.hasErrors()) {
         model.addAttribute("question",question);
         return "comment_form";
      }
      
      Comment comment = this.commentService.createQuestion(question,commentForm.getContent(),siteUser);

      return String.format("redirect:/question/detail/%s", comment.getQuestion().getId());
   }*/
   
   @PreAuthorize("isAuthenticated()")
   @PostMapping("/create/question")
   @ResponseBody
   public ResponseEntity<?> createQuestionComment(@RequestBody CommentDTO commentDTO, Principal principal) {
      
          SiteUser siteUser = this.userService.getUser(principal.getName());

         Comment saveComment = commentService.saveQuestion(commentDTO,siteUser);
         return ResponseEntity.ok(saveComment);
         
   }
   
   @PreAuthorize("isAuthenticated()")
   @PostMapping("/create/answer")
   @ResponseBody
   public ResponseEntity<?> creatAnswerComment(@RequestBody CommentDTO commentDTO, Principal principal) {
      
          SiteUser siteUser = this.userService.getUser(principal.getName());

         Comment saveComment = commentService.saveAnswer(commentDTO,siteUser);
         return ResponseEntity.ok(saveComment);
         
   }
   
   /*
   @PreAuthorize("isAuthenticated()")
   @GetMapping("/create/question/{id}/{answerId}")
   //GetMapping에도 CommentForm 추가해줘야한다.
   public String createAnswerComment(CommentForm commentForm) {
      
      return "comment_form";
   }
   
   
   @PreAuthorize("isAuthenticated()")
   @PostMapping("/create/question/{id}/{answerId}")
   public String createAnswerComment(Model model, @PathVariable("answerId") Integer answerId, @PathVariable("id") Integer id, @Valid CommentForm commentForm, BindingResult bindingResult, Principal principal) {
      
      Answer answer = this.answerService.getAnswer(answerId);
      SiteUser siteUser = this.userService.getUser(principal.getName());
      
      if(bindingResult.hasErrors()) {
         model.addAttribute("answer",answer);
         return "comment_form";
      }
      
      Comment comment = this.commentService.createAnswer(answer,commentForm.getContent(),siteUser);

      return String.format("redirect:/question/detail/%s#answer%s", comment.getAnswer().getQuestion().getId(), comment.getAnswer().getId());
   }
   
   @PreAuthorize("isAuthenticated()")
   @GetMapping("/modify/{id}")
   public String commentModify(CommentForm commentForm, @PathVariable("id") Integer id, Principal principal) {

      Comment comment = this.commentService.getComment(id);
      if(!comment.getAuthor().getUsername().equals(principal.getName())) {
         throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 권한이 없습니다.");
      }
      commentForm.setContent(comment.getContent());
      return "comment_form";
   }

   
   @PreAuthorize("isAuthenticated()")
   @PostMapping("/modify/{id}")
   public String commentModify(@Valid CommentForm commentForm, BindingResult bindingResult, @PathVariable("id") Integer id,Principal principal) {
      
      if(bindingResult.hasErrors()) {
         return "comment_form";
      }
      Comment comment = this.commentService.getComment(id);
      if(!comment.getAuthor().getUsername().equals(principal.getName())) {
         throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 권한이 없습니다.");
      }
      this.commentService.modify(comment, commentForm.getContent());
      commentForm.setContent(comment.getContent());
      return String.format("redirect:/question/detail/%s", comment.getQuestionId());
   }
   */
   
   @PreAuthorize("isAuthenticated()")
   @DeleteMapping("/delete")
   public ResponseEntity<?> commentDelete(@RequestBody CommentDTO commentDTO) {
      
      commentService.delete(commentDTO);
      
      return ResponseEntity.ok("삭제성공");
   }
   
   @PreAuthorize("isAuthenticated()")
   @PostMapping("/modify")
   public ResponseEntity<?> commentModify(@RequestBody CommentDTO commentDTO) {
      commentService.modify(commentDTO);
      //왜 값을 넣어줘야하지?
      return ResponseEntity.ok("수정성공");
   }
   
}