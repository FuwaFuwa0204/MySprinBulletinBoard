package com.mysite.sbb.user;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class PrincipalDetails implements UserDetails, OAuth2User {
    private SiteUser siteUser;
    private Map<String, Object> attributes;

    // 일반 유저 로그인 시 생성자
    public PrincipalDetails(SiteUser siteUser) {
        this.siteUser = siteUser;
    }

    // 소셜ㄹ 로그인 시 사용하는 생성자
    public PrincipalDetails(SiteUser siteUser, Map<String, Object> attributes) {
        this.siteUser = siteUser;
        this.attributes = attributes;
    }

    // 해당 유저의 권한을 리턴하는 곳
    @Override
    public List<GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        if ("admin".equals(siteUser.getUsername())) {
            authorities
                .add(new SimpleGrantedAuthority(UserRole.ADMIN.getValue()));
        } else {
            authorities
                .add(new SimpleGrantedAuthority(UserRole.USER.getValue()));
        }
        return authorities;
    }

 // UserDetails override하는 메소드들
    @Override
    public String getPassword() {
        return siteUser.getPassword();
    }

    @Override
    public String getUsername() {
        return siteUser.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    // OAuth2User override 메소드들
    //구글이 주는 사용자 정보를 Map타입으로,,
    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }


    @Override
    public String getName() {
        return siteUser.getUsername();
    }
}