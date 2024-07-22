package com.mysite.sbb.question;

//CRUD작업 내포
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mysite.sbb.comment.Comment;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;


//Question에 대한 리포지터리, 기본키가 Integer.
public interface QuestionRepository extends JpaRepository<Question,Integer> {
	
	Question findBySubject(String subject);
	Question findBySubjectAndContent(String subject, String content);
	List<Question> findBySubjectLike(String subject);
	Page<Question> findAll(Pageable pageable);
	Page<Question> findAll(Specification<Question> spec, Pageable pagable);
	

    @Query("select "
            + "distinct q "
            + "from Question q " 
            + "left outer join SiteUser u on q.author=u "
            + "where "
    		+ "   (q.category = :category) "
    		+ "   and ( "
            + "   q.subject like %:kw% "
            + "   or q.content like %:kw% "
            + "   or u.username like %:kw% "
    		+ "   )")
    Page<Question> findAllByKeyword(@Param("kw") String kw, @Param("category") int category, Pageable pageable);
 	
    @Query("select q "
    	+ "from Question q "
    	+ "join SiteUser u on q.author=u "
    	+ "where u.username = :username ")
    List<Question> findQuestionList(@Param("username") String username, Pageable pageable);
    
    @Query("select q "
        	+ "from Question q "
        	+ "join SiteUser u on q.author=u "
        	+ "where u.username = :username "
        	+ "and (q.content like %:kw%) or (q.subject like %:kw%)")
    Page<Question> findAllByKeywordAndSiteUser(@Param("kw") String kw, @Param("username") String username, Pageable pageable);
    
    


}
