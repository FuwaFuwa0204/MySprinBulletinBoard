package com.mysite.sbb.comment;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.mysite.sbb.answer.Answer;
import com.mysite.sbb.question.Question;
import com.mysite.sbb.user.SiteUser;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentDTO {
   
   private Integer id;
   
   private String content;
   
   private LocalDateTime createDate;
   
   @JsonInclude(JsonInclude.Include.NON_NULL)
   private Question question;
   
   @JsonInclude(JsonInclude.Include.NON_NULL)
   private Answer answer;
   
   
   @JsonInclude(JsonInclude.Include.NON_NULL)
   private SiteUser siteUser;
   
   

}
