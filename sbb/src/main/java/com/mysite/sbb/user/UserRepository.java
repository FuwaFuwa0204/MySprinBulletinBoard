package com.mysite.sbb.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<SiteUser,Long> {
	
	//꼭 엔티티에 있는 아이템을 가지고 함수 이름을 만들어야한다.
	Optional<SiteUser> findByUsername(String username);
	Optional<SiteUser> findByEmail(String email);
    
}
