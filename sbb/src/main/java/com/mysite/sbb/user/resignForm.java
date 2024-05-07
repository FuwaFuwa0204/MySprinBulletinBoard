package com.mysite.sbb.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class resignForm {
	@Size(min=8, max=20)
	@NotBlank(message = "비밀번호는 필수 항목입니다.")
	private String password;
	
}
