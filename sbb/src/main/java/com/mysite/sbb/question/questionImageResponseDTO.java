package com.mysite.sbb.question;


import java.util.List;
import java.util.stream.Collectors;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class questionImageResponseDTO {
	
	private List<String> urls;
	
	@Builder
	public questionImageResponseDTO(Question question) {
			this.urls = question.getQuestionImage()
					.stream()
					.map(questionImage::getUrl)
					.collect(Collectors.toList());
	}
	


}
