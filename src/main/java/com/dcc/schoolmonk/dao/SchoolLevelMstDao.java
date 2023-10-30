package com.dcc.schoolmonk.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.dcc.schoolmonk.vo.SchoolLevelMstVo;

public interface SchoolLevelMstDao extends CrudRepository<SchoolLevelMstVo, Long> {
	List<SchoolLevelMstVo> findByOrderBySlNo();

	
	@Query(nativeQuery = true,value = "select school_level_name from SCHOOL_LEVEL_MST")
	List<String> getSchoolLevels();
}
