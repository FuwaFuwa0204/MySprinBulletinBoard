package com.mysite.sbb.answer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.mysite.sbb.question.Question;

public interface AnswerRepository extends JpaRepository<Answer,Integer> {
	Page<Answer> findByQuestion(Question qusetion,Pageable pageable);

}
