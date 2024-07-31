package com.mysite.sbb.comment;

import java.time.LocalDateTime;

import org.hibernate.annotations.ColumnDefault;

import com.mysite.sbb.question.Question;
import com.mysite.sbb.user.SiteUser;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
	
	@ManyToOne
	private Question question;
	
	/* 답변 부분 삭제
	//Could not write JSON: Infinite recursion 오류
	@ManyToOne(fetch = FetchType.EAGER)
	@JsonBackReference
	private Answer answer;
	*/
	
	@ManyToOne
	private SiteUser author;
	
	@Column(columnDefinition = "TEXT")
	private String content;

	private LocalDateTime createDate;
	
	private LocalDateTime modifyDate;	
	

    public Integer getQuestionId() {
        Integer result = null;
        if (this.question != null) {
            result = this.question.getId();
        } 
        return result;
        }
        /*
        else if (this.answer != null) {
            result = this.answer.getQuestion().getId();
        }
        */
        
    
	//계층형 게시판을 위한 상태값
	private Integer grp;
	
	private Integer seq;
	
	private Integer dep;
	
	private Integer parentId;
	
	private String parentName;
	
    @ColumnDefault("FALSE")
    @Column(nullable = false)
	private Boolean isDeleted;
	
	public void changeIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}


}
