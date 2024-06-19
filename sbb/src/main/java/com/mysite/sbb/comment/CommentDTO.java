package com.mysite.sbb.comment;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.mysite.sbb.question.Question;
import com.mysite.sbb.user.SiteUser;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentDTO {
   
   private Integer id;
   
   private String content;
   
   private LocalDateTime createDate;
   
   @JsonInclude(JsonInclude.Include.NON_NULL)
   public Question question;
   
   /* 답변 부분 삭제
   @JsonInclude(JsonInclude.Include.NON_NULL)
   public Answer answer;
   */
   
   @JsonInclude(JsonInclude.Include.NON_NULL)
   private SiteUser siteUser;
   
   //reply
	private Integer grp;
	
	private Integer seq;
	
	private Integer dep;
	
	private Integer parentId;
	
	private Boolean isDeleted;
   
   

}
