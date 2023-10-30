package com.dcc.schoolmonk.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dcc.schoolmonk.vo.SchoolStudentEligibilityDtlVo;

public interface SchoolStudentEligibilityDtlDao extends JpaRepository<SchoolStudentEligibilityDtlVo, Long> {
	
	@Query(nativeQuery = true, value = "SELECT id FROM SCHOOL_STUDENT_ELIGIBILITY_DTL where School_Id = :schoolId and Academic_Year = :academicYear and board_name = :boardName ")
	List<Long> noOfEligibilityData(@Param("schoolId") long schoolId, @Param("academicYear") String academicYear, @Param("boardName") String boardName);
	
	@Transactional
	@Modifying
	@Query(nativeQuery = true, value = "delete FROM SCHOOL_STUDENT_ELIGIBILITY_DTL where id in :idList ")
	void deleteEligibilityData(@Param("idList") List<Long> idList);
	
	//@Query(nativeQuery = true, value = "SELECT id FROM SCHOOL_STUDENT_ELIGIBILITY_DTL where School_Id = :schoolId and Academic_Year = :academicYear and board_name = :boardName ")
//	SchoolStudentEligibilityDtlVo findBySchoolMstVoIdAndAcademicYearAndBoardNameAndClassRange(Long id, String academicYear, String boardName, String classRange);
//	
//	SchoolStudentEligibilityDtlVo findBySchoolMstVoIdAndAcademicYearAndBoardNameAndClassRangeAndClassStream(Long id, String academicYear, String boardName, String classRange, String classStream);
	
	@Query(nativeQuery = true, value = "CALL checkIfEligible(:whereClause);")
	SchoolStudentEligibilityDtlVo checkIfEligible(@Param("whereClause") String whereClause);
}
