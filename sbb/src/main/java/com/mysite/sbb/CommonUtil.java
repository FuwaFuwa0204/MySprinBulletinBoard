package com.mysite.sbb;

import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.springframework.stereotype.Component;

@Component
public class CommonUtil {
	public String markdown(String markdown) {
		Parser parser = Parser.builder().build();
		Node document = parser.parse(markdown);
		HtmlRenderer renderer = HtmlRenderer.builder().build();
		return renderer.render(document);
	}
	
	public String createTmpPassword() {
		
		char[] passwordSet = new char[] {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F',
                'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };
		
		String password = "";
		
		int index=0;
		for(int i= 0; i<10; i++) {
			//랜덤한 인덱스를 가져오기
			//Math.random()은 0과 1 사이의 수 중에 랜덤으로 불러온다. passwordSet의 숫자만큼 가져와야하니까 length*Math.random()
			index = (int)(passwordSet.length*Math.random());
			password += passwordSet[index];
		}
		
		return password;
	}

}
