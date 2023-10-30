package com.dcc.schoolmonk.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dcc.schoolmonk.vo.SchoolTimingDtlVo;

public interface SchoolTimingDtlDao extends JpaRepository<SchoolTimingDtlVo, Long> {
	
	@Query(nativeQuery = true, value = "SELECT id FROM SCHOOL_TIMING_DTL where School_Id = :schoolId and Academic_Year = :academicYear and board_name = :boardName ")
	List<Long> noOfTimingData(@Param("schoolId") long schoolId, @Param("academicYear") String academicYear, @Param("boardName") String boardName);
	
	@Transactional
	@Modifying
	@Query(nativeQuery = true, value = "delete FROM SCHOOL_TIMING_DTL where id in :idList ")
	void deleteTimingData(@Param("idList") List<Long> idList);
}
