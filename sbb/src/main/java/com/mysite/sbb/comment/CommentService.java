package com.mysite.sbb.comment;

import java.time.LocalDateTime;

import java.util.ArrayList;
import java.util.List;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.mysite.sbb.DataNotFoundException;
import com.mysite.sbb.question.Question;
import com.mysite.sbb.question.QuestionRepository;
import com.mysite.sbb.user.SiteUser;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CommentService {
   
   private final CommentRepository commentRepository;
   private final QuestionRepository questionRepository;
   
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
   */
   
   //일반 댓글 생성
   public Comment saveQuestion(CommentDTO commentDTO, SiteUser siteUser) {
         
         Question question = this.questionRepository.findById(commentDTO.getQuestion().getId()).orElseThrow();
         
         question.setCommentParentGrp(question.getCommentParentGrp()+1);
         
         Comment build = Comment.builder()
               .content(commentDTO.getContent())
               .createDate(LocalDateTime.now())
               .author(siteUser)
               .question(commentDTO.getQuestion())
               .parentId(null)
               .grp(question.getCommentParentGrp())
               .seq(0)
               .dep(0)
               .isDeleted(false)
               .parentName(null)
               .build();
         
          Comment save = commentRepository.save(build);

          
          return save;
      }
   /*
   public Comment saveAnswer(CommentDTO commentDTO, SiteUser siteUser) {
      
      Answer answer = this.answerRepository.findById(commentDTO.getAnswer().getId()).orElseThrow();
      
      Question question = this.questionRepository.findById(commentDTO.getQuestion().getId()).orElseThrow();
      
      answer.setCommentParentGrp(answer.getCommentParentGrp()+1);
      
      Comment build = Comment.builder()
            .content(commentDTO.getContent())
            .createDate(LocalDateTime.now())
            .author(siteUser)
            .answer(commentDTO.getAnswer())
            .question(commentDTO.getQuestion())
            .parentId(null)
            //question이 아니라 answer..
            .grp(answer.getCommentParentGrp())
            .seq(0)
            .dep(0)
            .isDeleted(false)
            .build();
      
       Comment save = commentRepository.save(build);
       return save;
   }
   */
   
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
      
      comment.changeIsDeleted(true);
      
      //상태 변하고 나면 꼭 save해주기
      this.commentRepository.save(comment);

   }
   
   public List<Comment> findCommentList(int num, String username) {
      Pageable pageable = PageRequest.of(0, num);
      return this.commentRepository.findCommentList(username, pageable);

}
   
   public Page<Comment> pagingComment(int page){
      Pageable pageable = PageRequest.of(page,10);
      return this.commentRepository.findAll(pageable);
   }
   

   //대댓글 생성
   public Comment addReply(CommentDTO commentDTO,SiteUser siteUser) {

      
      //Comment origin = this.commentRepository.findById(commentDTO.getId()).orElseThrow();
      
      Integer parentId = commentDTO.getParentId();
      
      Comment parent = this.commentRepository.findById(parentId).orElseThrow();
      
      Question question = commentDTO.getQuestion();
      
      Integer parentGrp = parent.getGrp(); //그룹은 그대로
      
      Integer parentSeq = parent.getSeq(); 
      
      Integer parentDep = parent.getDep(); //dep는 부모보다 +1

      Integer seq = this.commentRepository.countByGrp(parentGrp);
      
      Integer dep = parentDep+1;
      
      String parentName = commentDTO.getParentName();
      
      if(dep>2) {
    	  dep = 2;
      }
      
        
         Comment build = Comment.builder()
                  .content(commentDTO.getContent())
                  .createDate(LocalDateTime.now())
                  .author(siteUser)
                  .question(question)
                  .parentId(parent.getId())
                  .grp(parentGrp)
                  .seq(seq)
                  .dep(dep)
                  .isDeleted(false)
                  .parentName(parentName)
                  .build();
         
         Comment result = this.commentRepository.save(build);
         
         return result;
      
   }
   /*
   
   public Comment addReplyAnswer(CommentDTO commentDTO,SiteUser siteUser) {

      
      //Comment origin = this.commentRepository.findById(commentDTO.getId()).orElseThrow();
      
      Integer parentId = commentDTO.getParentId();
      
      Comment parent = this.commentRepository.findById(parentId).orElseThrow();
      
      Answer answer = commentDTO.getAnswer();
      
      Question question = commentDTO.getQuestion();
      
      Integer parentGrp = parent.getGrp(); //그룹은 그대로
      
      Integer parentSeq = parent.getSeq(); 
      
      Integer parentDep = parent.getDep(); //dep는 부모보다 +1

      Integer seq = this.commentRepository.countByGrp(parentGrp);
      
      Integer dep = parentDep+1;
         
         Comment build = Comment.builder()
                  .content(commentDTO.getContent())
                  .createDate(LocalDateTime.now())
                  .author(siteUser)
                  .question(question)
                  .parentId(parent.getId())
                  .grp(parentGrp)
                  .seq(seq)
                  .dep(dep)
                  .isDeleted(false)
                  .build();
         
         Comment result = this.commentRepository.save(build);
         
         return result;


      
   }
   */
   
   public Page<Comment> getListByUser(int Page, String user, String kw){
      List<Sort.Order> sorts = new ArrayList<>();
      sorts.add(Sort.Order.desc("createDate"));
      Pageable pageable = PageRequest.of(Page, 10, Sort.by(sorts));
      return this.commentRepository.findAllByKeywordAndSiteUser(kw, user, pageable);
   }
   



}
