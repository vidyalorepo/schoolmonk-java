package com.dcc.schoolmonk.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.dcc.schoolmonk.vo.UserSubcriptionLogVo;

public interface UserSubscriptionLogDao extends CrudRepository<UserSubcriptionLogVo, Long>{
	
	
	@Query(nativeQuery = true, value = "SELECT * FROM SchoolMonk.USER_SUBSCRIPTION_LOG where user_id =:userId")
	List<UserSubcriptionLogVo> findByUserId(long userId);

}
