package com.mysite.sbb.question;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface questionImageRepository extends JpaRepository<questionImage, Integer> {
	
	questionImage findByQuestion(Question question);
	List<questionImage> deleteAllByQuestion(Question question);
	List<questionImage> findAllByQuestion(Question question);
	
	
	

}
