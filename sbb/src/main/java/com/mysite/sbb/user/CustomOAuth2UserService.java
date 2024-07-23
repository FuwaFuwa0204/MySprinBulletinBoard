package com.mysite.sbb.user;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
//DefaultOAuth2UserService :  OAuth2UserService를 구현.
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;
    
    //OAuth2UserRequest에서 엑세스 토큰과 같은 정보 들어있음.
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest)throws OAuth2AuthenticationException {

        DefaultOAuth2UserService delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);
        
        //Google, naver, kakao 처럼 제공하는 서비스 이름
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        //서비스가 가지고 있는 유니크한 키 ex) 구글은 sub
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();
        //access Token
        String accessToken = userRequest.getAccessToken().getTokenValue();


        OAuthAttributes attributes = OAuthAttributes.of(registrationId,userNameAttributeName,oAuth2User.getAttributes());

        SiteUser oAuthAccount = saveOrUpdate(attributes);

        return new PrincipalDetails(oAuthAccount, attributes.getAttributes());
    }

    private SiteUser saveOrUpdate(OAuthAttributes attributes) {
        SiteUser oAuthAccount =
            userRepository.findByEmail(attributes.getEmail())
            //값이 있으면 Optional 반환, 없으면 빈 Optional 반환
                .map(entity -> entity.update(attributes.getUsername(),attributes.getPicture()))
                //없으면 이 구문 실행 => email로 찾아도 없는 사람이면 회원가입 시켜준다.
                .orElse(attributes.toEntity());

        return userRepository.save(oAuthAccount);
    }
}