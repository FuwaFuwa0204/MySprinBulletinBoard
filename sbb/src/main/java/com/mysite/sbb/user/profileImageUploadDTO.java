package com.mysite.sbb.user;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class profileImageUploadDTO {
	
    private MultipartFile file;
}
