package com.mysite.sbb.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class updatePasswordForm {
	@Size(min=8, max=20)
	@NotBlank(message = "현재 비밀번호는 필수 항목입니다.")
	private String password1;
	
	@Size(min=8, max=20)
	@NotBlank(message = "변경할 비밀번호는 필수 항목입니다.")
	private String password2;
	
	@NotBlank(message = "이메일은 필수 항목입니다.")
	@Email
	private String email;

}
