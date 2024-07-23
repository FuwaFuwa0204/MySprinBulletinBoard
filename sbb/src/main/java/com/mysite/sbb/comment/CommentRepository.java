package com.mysite.sbb.comment;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mysite.sbb.question.Question;



public interface CommentRepository  extends JpaRepository<Comment,Integer> {
	
    @Query("select c "
        	+ "from Comment c "
        	+ "join SiteUser u on c.author=u "
        	+ "where u.username = :username ")
        List<Comment> findCommentList(@Param("username") String username, Pageable pageable);
    
    //grp 개수만큼 자식 개수가 나온다.
    Integer countByGrp(Integer grp);
    
    List<Comment> findByQuestionOrderByGrpAscSeqAsc(Question question);
    
    @Query("select c "
        	+ "from Comment c "
        	+ "join SiteUser u on c.author=u "
        	+ "where u.username = :username "
        	+ "and c.content like %:kw% ")
    Page<Comment> findAllByKeywordAndSiteUser(@Param("kw") String kw, @Param("username") String username, Pageable pageable);
    

}
