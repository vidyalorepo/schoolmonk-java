package com.dcc.schoolmonk.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dcc.schoolmonk.vo.AdsOrderDetailsVo;

public interface AdsOrderDetailsDao extends JpaRepository<AdsOrderDetailsVo, Long> {
	
	@Query(nativeQuery = true, value = "CALL getAdsOrderList(:whereClause, :orderByClause, :limitClause);")
    List<AdsOrderDetailsVo> getOrderList(@Param("whereClause") String whereClause, @Param("limitClause") String limitStr,@Param("orderByClause") String orderByStr);

    @Query(nativeQuery = true, value = "CALL getcountAdsOrderList(:whereClause);")
    int getcountOrderList(@Param("whereClause") String whereClause);

    @Query(nativeQuery = true, value = "SELECT * FROM ADS_ORDER_DETAILS where zone_id=?1 and status=true")
    List<AdsOrderDetailsVo> findbyzoneIdandStatus(Long id);
}