package com.mysite.sbb.comment;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.mysite.sbb.DataNotFoundException;
import com.mysite.sbb.answer.Answer;
import com.mysite.sbb.question.Question;
import com.mysite.sbb.question.QuestionService;
import com.mysite.sbb.user.SiteUser;
import com.mysite.sbb.user.UserRepository;
import com.mysite.sbb.user.UserService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CommentService {
   
   private final CommentRepository commentRepository;
   private final UserService userService;
   private final QuestionService questionService;
   
   /*
   public Comment createQuestion(Question question, String content, SiteUser author) {
      Comment comment = new Comment();
      comment.setContent(content);
      comment.setCreateDate(LocalDateTime.now());
      comment.setQuestion(question);
      comment.setAuthor(author);

      this.commentRepository.save(comment);
      
      return comment;
   }
   
   public Comment createAnswer(Answer answer, String content, SiteUser author) {
      Comment comment = new Comment();
      comment.setContent(content);
      comment.setCreateDate(LocalDateTime.now());
      comment.setAnswer(answer);
      comment.setAuthor(author);
      this.commentRepository.save(comment);
      
      return comment;
   }
   */
   
   public Comment saveQuestion(CommentDTO commentDTO, SiteUser siteUser) {
      
      Question question = commentDTO.getQuestion();
      
      Comment build = Comment.builder()
            .content(commentDTO.getContent())
            .createDate(LocalDateTime.now())
            .author(siteUser)
            .answer(null)
            .question(question)
            .build();
      
       Comment save = commentRepository.save(build);
       return save;
   }
   
   public Comment saveAnswer(CommentDTO commentDTO, SiteUser siteUser) {
      
      Answer answer = commentDTO.getAnswer();
      Question question = commentDTO.getQuestion();
      
      Comment build = Comment.builder()
            .content(commentDTO.getContent())
            .createDate(LocalDateTime.now())
            .author(siteUser)
            .answer(answer)
            .question(question)
            .build();
      
       Comment save = commentRepository.save(build);
       return save;
   }
   
   public Comment getComment(Integer id) {
      Optional<Comment> comment = this.commentRepository.findById(id);
      if(comment.isPresent()) {
         return comment.get();
      } else {
         throw new DataNotFoundException("comment not found");
      }
   }
   
   /*
   public void modify (Comment comment, String content) {
      comment.setContent(content);
      comment.setModifyDate(LocalDateTime.now());
      this.commentRepository.save(comment);
   }
   
   public void delete(Comment comment) {

         this.commentRepository.delete(comment);

      
   }
   */
   public void modify (CommentDTO commentDTO) {
      
      //프론트에서 받아온 commentDTO로 comment찾고 set해주기
      Comment comment = this.commentRepository.findById(commentDTO.getId()).orElseThrow();
      
      comment.setContent(commentDTO.getContent());
      comment.setModifyDate(LocalDateTime.now());
      
      this.commentRepository.save(comment);
   }
   
   public void delete(CommentDTO commentDTO) {
      
      Comment comment = this.commentRepository.findById(commentDTO.getId()).orElseThrow();

      this.commentRepository.delete(comment);

      
   }
   
   public List<Comment> findCommentList(int num, String username) {
      Pageable pageable = PageRequest.of(0, num);
      return this.commentRepository.findCommentList(username, pageable);

}

}
