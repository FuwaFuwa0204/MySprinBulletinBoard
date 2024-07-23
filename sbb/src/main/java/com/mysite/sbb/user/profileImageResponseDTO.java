package com.mysite.sbb.user;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class profileImageResponseDTO {
	
    private String url;

    @Builder
    public profileImageResponseDTO(String url) {
        this.url = url;
    }

}
