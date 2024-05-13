package com.mysite.sbb.user;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class profileImageService {
	
    private final profileImageRepository profileImageRepository;
    private final UserRepository userRepository;

    @Value("${file.path}")
    private String uploadFolder;

    public void upload(profileImageUploadDTO imageUploadDTO, String email) {
        SiteUser siteUser = userRepository.findByEmail(email).orElseThrow();
        MultipartFile file = imageUploadDTO.getFile();

        UUID uuid = UUID.randomUUID();
        String imageFileName = uuid + "_" + file.getOriginalFilename();
        File destinationFile = new File(uploadFolder + imageFileName);

        try {
            file.transferTo(destinationFile);

            profileImage image = profileImageRepository.findBySiteUser(siteUser);
            if (image != null) {
                image.updateUrl("/profileImages/" + imageFileName);
            } else {
                image = profileImage.builder()
                        .siteUser(siteUser)
                        .url("/profileImages/" + imageFileName)
                        .build();
            }
            
            profileImageRepository.save(image);
            
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public profileImageResponseDTO findImage(String email) {
        SiteUser siteUser = userRepository.findByEmail(email).orElseThrow();
        profileImage image = profileImageRepository.findBySiteUser(siteUser);

        String defaultImageUrl = "/profileImages/user.png";

        if (image == null) {
            return profileImageResponseDTO.builder()
                    .url(defaultImageUrl)
                    .build();
        } else {
            return profileImageResponseDTO.builder()
                    .url(image.getUrl())
                    .build();
        }
    }

}
