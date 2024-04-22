package com.mysite.sbb.category;

import java.util.List;

import com.mysite.sbb.question.Question;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
public class Category {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
	@Column(unique = true)
	private String name;
	
	@OneToMany(mappedBy = "category")
	private List<Question> question;
	
}



