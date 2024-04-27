package com.mysite.sbb.answer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.mysite.sbb.question.Question;

public interface AnswerRepository extends JpaRepository<Answer,Integer> {
	Page<Answer> findByQuestion(Question qusetion,Pageable pageable);
	
    @Query("select a "
        	+ "from Answer a "
        	+ "join SiteUser u on a.author=u "
        	+ "where u.username = :username ")
        List<Answer> findAnswerList(@Param("username") String username, Pageable pageable);

}
