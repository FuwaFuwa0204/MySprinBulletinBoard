package com.mysite.sbb.question;

//CRUD작업 내포
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

//Question에 대한 리포지터리, 기본키가 Integer.
public interface QuestionRepository extends JpaRepository<Question,Integer> {
	
	Question findBySubject(String subject);
	Question findBySubjectAndContent(String subject, String content);
	List<Question> findBySubjectLike(String subject);
	


}
