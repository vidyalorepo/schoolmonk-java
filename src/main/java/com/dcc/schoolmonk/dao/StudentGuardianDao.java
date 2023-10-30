package com.dcc.schoolmonk.dao;

import org.springframework.data.repository.CrudRepository;

import com.dcc.schoolmonk.vo.StudentGuardianVo;

public interface StudentGuardianDao extends CrudRepository<StudentGuardianVo, Long>{
    
    
}
