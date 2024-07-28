package com.mysite.sbb.user;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;

@Service
public class S3Uploader {
	
    private final AmazonS3 amazonS3;
    private final String bucketName;
    
    public S3Uploader(AmazonS3 amazonS3, @Value("${cloud.aws.s3.bucketName}") String bucketName) {
    	this.amazonS3=amazonS3;
    	this.bucketName=bucketName;
    }
    
    //dirName : 파일 업로드할 때의 경로를 설정하는 파라미터.
    public String upload(MultipartFile multipartFile, String dirName) throws IOException {

        String uuid = UUID.randomUUID().toString();
        String uniqueFileName = uuid + "_" + multipartFile.getOriginalFilename().replaceAll("\\s", "_");
        String fileName = dirName+"/"+uniqueFileName;
        
        File uploadFile = convert(multipartFile);
        
        //파일 이름과 자바 파일을 put
        String uploadImageUrl = putS3(uploadFile, fileName);
        //디스크 절약을 위해 임시로 생성된 파일 삭제
        removeNewFile(uploadFile);
        
        return uploadImageUrl;

        
    }
    
    //MultipartFile -> java로 변환.
    public File convert(MultipartFile file) throws IOException {
    	
    	String originalFileName = file.getOriginalFilename();
    	String uuid = UUID.randomUUID().toString();
    	String uniqueFileName = uuid + "_" + originalFileName.replaceAll("\\s", "_");
    	
    	//새 파일 생성.
    	File convertFile = new File(uniqueFileName);
    	
    	//파일 작성.
    	if(convertFile.createNewFile()) {
    		//try-with-resources
    		//자동으로 관리해야하는 자원을 () 안에 넣는다. 파일 스트림 생성시 파일 핸들을 닫아줘야하기 때문에 사용한다.
    		try(FileOutputStream fos = new FileOutputStream(convertFile)){
    			//파일 내용을 바이트 배열로 가져와 write 메소드를 이용해 convertFile에 기록.
    			fos.write(file.getBytes());
    			
    		}catch(IOException e) {
    			throw e;
    		}
    		return convertFile;
    	}
    	throw new IllegalArgumentException(String.format("파일 변환에 실패했습니다. %s", originalFileName));
    }
    
    private String putS3(File uploadFile, String fileName) {
    	
    	amazonS3.putObject(new PutObjectRequest(bucketName,fileName,uploadFile).withCannedAcl(CannedAccessControlList.PublicRead));
    	return amazonS3.getUrl(bucketName, fileName).toString();
    }
    
    private void removeNewFile(File targetFile) {
    	targetFile.delete();
    }
    
    public void deleteFile(String fileName) {
    	try {
    		String decodedFileName = URLDecoder.decode(fileName, "UTF-8");
    		amazonS3.deleteObject(bucketName,decodedFileName);
    	} catch(UnsupportedEncodingException e) {
    		
    	}
    }
    
    public String updateFile(MultipartFile newFile, String oldFileName, String dirName)throws IOException {
        // 기존 파일 삭제
        deleteFile(oldFileName);
        // 새 파일 업로드
        return upload(newFile, dirName);
    }

}
