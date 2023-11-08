package com.dcc.schoolmonk.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dcc.schoolmonk.vo.BlogMstVo;

public interface BlogDao extends JpaRepository<BlogMstVo, Long>{

    @Query(nativeQuery = true, value = "CALL getAllBloglist(:whereClause, :orderByClause, :limitClause);")
    List<BlogMstVo> getAllBlog(@Param("whereClause") String whereClause, @Param("limitClause") String limitStr,@Param("orderByClause") String orderByStr);

    @Query(nativeQuery = true, value = "CALL getBlogcount(:whereClause);")
    int getAllBlogCount(String whereClause);
    
    @Modifying
    @Transactional    
    @Query(nativeQuery = true, value = "UPDATE Blog_MST SET status = ?2 WHERE id = ?1")
    int updateStatus(long id, boolean b);

    @Query(nativeQuery = true, value = "SELECT * FROM Blog_MST where slug = ?1")
    BlogMstVo findbySlug(String slug);

    @Query(nativeQuery = true, value = "SELECT COUNT(*) FROM schoolportalstage.Blog_MST WHERE status = true")
    int getBlogCount();
    
}
