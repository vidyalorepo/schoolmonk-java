package com.dcc.schoolmonk.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.dcc.schoolmonk.vo.StudentMstVo;

public interface StudentDao extends JpaRepository<StudentMstVo, Long> {

	@Query(nativeQuery = true, value = "select a.id from STUDENT_MST a, USER_DETAILS b where Student_User_Id = b.userId and userId = :userId")
	Long getStudentIdByUserId(Long userId);

	@Query(nativeQuery = true, value = "SELECT * FROM STUDENT_MST where Parent_User_Id = :studentUserId")
	List<StudentMstVo> getApplicantByStudentUser(Long studentUserId);
	
	@Query(nativeQuery = true,value = "select * from STUDENT_MST where Parent_User_Id = ?1 ")
	List<StudentMstVo> getStudentByParentId(Long id);
}
