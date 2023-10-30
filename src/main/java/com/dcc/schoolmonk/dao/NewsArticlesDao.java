package com.dcc.schoolmonk.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.dcc.schoolmonk.vo.NewsArticlesVo;


public interface NewsArticlesDao extends JpaRepository<NewsArticlesVo, Long>{

    @Query(nativeQuery = true, value = "CALL getnewsarticallist(:whereClause, :orderByClause, :limitClause);")
    List<NewsArticlesVo> getNewsArticallist(@Param("whereClause") String whereClause, @Param("limitClause") String limitStr,@Param("orderByClause") String orderByStr);

    @Query(nativeQuery = true, value = "CALL getNewsArticalcount(:whereClause);")
    int getcountNewsArtical(@Param("whereClause") String whereClause);

    @Modifying
    @Transactional    
    @Query(nativeQuery = true, value = "UPDATE NEWS_ARTICLES SET status = ?2 WHERE id = ?1")
    int updateStatus(long id, boolean b);

    @Query(nativeQuery = true, value ="SELECT * FROM NEWS_ARTICLES where slug=?1")
	NewsArticlesVo findByslug(String slug);
	

}
