package com.mysite.sbb.question;

import lombok.Getter;

@Getter
public enum QuestionEnum {
	QNA(0),
	CALENDAR(1);

	private int status;

	QuestionEnum(int status) {
		this.status = status;
	}
}