package com.dcc.schoolmonk.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.dcc.schoolmonk.vo.SchoolAcademicDtlVo;

public interface SchoolAcademicDtlDao extends JpaRepository<SchoolAcademicDtlVo, Long> {

    @Query(nativeQuery = true, value = "SELECT * FROM SCHOOL_ACADEMIC_DTL where School_Id =?1")
    List<SchoolAcademicDtlVo> findbySchoolid(Long id);

    @Query(nativeQuery = true, value = "SELECT * FROM SCHOOL_ACADEMIC_DTL where School_Id =?1 order by Academic_Year desc limit 3")
    List<SchoolAcademicDtlVo> findwithLimit(long id);
	
}
