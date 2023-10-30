package com.dcc.schoolmonk.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dcc.schoolmonk.vo.SchoolLevelDtlVo;

public interface SchoolLevelDtlDao extends JpaRepository<SchoolLevelDtlVo, Long> {
	
	@Query(nativeQuery = true, value = "SELECT min(from_class_no), max(to_class_no) FROM SchoolMonk.SCHOOL_LEVEL_DTL where School_Id = :schoolId ")
	String getClassRange(Long schoolId);
	
	
	List<SchoolLevelDtlVo> findBySchoolMstVoId(Long schoolID);
	
	
	@Query(nativeQuery = true, value = "SELECT count(school_level_name) FROM SCHOOL_LEVEL_DTL where school_level_name like %:levelName%")
	Long getSchoolLevelCount(@Param("levelName") String levelName);
}
