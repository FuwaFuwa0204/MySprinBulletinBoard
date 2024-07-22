package com.mysite.sbb.user;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<SiteUser,Long> {
   
   //꼭 엔티티에 있는 아이템을 가지고 함수 이름을 만들어야한다.
   Optional<SiteUser> findByUsername(String username);
   Optional<SiteUser> findByEmail(String email);
   
   //검색을 할때는 like를 쓰자. u.username=:username은 처음에 ""이 들어올텐데 ""인 username이 없어서 아무런 리스트도 들어오지 않는다. 
    @Query("select u "
           + "from SiteUser u "
           + "where u.username like %:username% ")
   Page<SiteUser> findAllByUsername(Pageable pageable, @Param("username") String username);
    
}
