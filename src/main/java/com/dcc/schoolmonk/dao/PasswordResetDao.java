package com.dcc.schoolmonk.dao;

import org.springframework.data.repository.CrudRepository;

import com.dcc.schoolmonk.vo.UserVo;

public interface PasswordResetDao extends CrudRepository<UserVo, Long> {

}
