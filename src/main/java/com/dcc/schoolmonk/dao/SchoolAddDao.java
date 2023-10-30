package com.dcc.schoolmonk.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.dcc.schoolmonk.vo.SchoolAddVo;

public interface SchoolAddDao extends CrudRepository<SchoolAddVo, Long>{


    @Query(nativeQuery = true, value = "SELECT * FROM JOIN_SCHOOL where School_Name=?1")
    List<SchoolAddVo> findByschoolName(String schoolName);

   
}
