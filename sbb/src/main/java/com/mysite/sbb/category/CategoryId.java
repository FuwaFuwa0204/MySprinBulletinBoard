package com.mysite.sbb.category;

import lombok.Getter;

@Getter
public enum CategoryId {

	QNA(0),
	FREE(1);

	private int status;

	CategoryId(int status) {
		this.status = status;
	}
}