package com.dcc.schoolmonk.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.dcc.schoolmonk.vo.ClassMstVo;

public interface ClassMstDao extends CrudRepository<ClassMstVo, Long> {

	List<ClassMstVo> findByOrderBySlNo();
	
	List<ClassMstVo> findByStartClassOrderBySlNo(boolean startClass);
	
	List<ClassMstVo> findByEndClassOrderBySlNo(boolean endClass);
	
	@Query(nativeQuery = true, value = "select class_stream from CLASS_MST where class_name = :className and class_stream is not null")
	List<String> getStreams(String className);
	
	@Query(nativeQuery = true, value = "select class_display from CLASS_MST where sl_no >= :slNoStart and sl_no <= :slNoEnd")
	List<String> getClassBetween(long slNoStart, long slNoEnd);
	
	/*@Query(nativeQuery = true, value = "select concat(class_display, '||', class_name, '||', (select class_stream from SCHOOL_BOARD_CLASS_DTL where school_id = :schoolId and board_name = :boardName)) data_str "
			+ "FROM CLASS_MST where sl_no >= (select start_class_sl_no from SCHOOL_BOARD_CLASS_DTL  where school_id = :schoolId and board_name = :boardName) "
			+ "and sl_no <= (select end_class_sl_no from SCHOOL_BOARD_CLASS_DTL  where school_id = :schoolId and board_name = :boardName)")*/
	@Query(nativeQuery = true, value = "select concat(Class_Range, '||', (select class_name from CLASS_MST where class_display = Class_Range), '||', class_stream ) data_str "
			+ "from SCHOOL_ADMISSION_DTL where school_id = :schoolId and board_name = :boardName and Publish_Status = 'Y' ")
	List<String> getClassListOfSchool(long schoolId, String boardName);
}
