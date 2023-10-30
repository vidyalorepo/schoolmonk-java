package com.dcc.schoolmonk.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.dcc.schoolmonk.vo.SchoolCityMstVo;

public interface SchoolCityMstDao extends JpaRepository <SchoolCityMstVo , Long> {
	
    @Query(nativeQuery = true, value = "SELECT * FROM SCHOOL_CITY_MST where is_active=true")
    List<SchoolCityMstVo> findAllLocation();	
}
