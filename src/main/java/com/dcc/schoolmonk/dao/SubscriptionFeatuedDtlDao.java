package com.dcc.schoolmonk.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.dcc.schoolmonk.vo.SubscriptionFeaturesDtlVo;

public interface SubscriptionFeatuedDtlDao extends CrudRepository<SubscriptionFeaturesDtlVo, Long>{

	
	@Query(nativeQuery = true, value = "select * from SUB_FEATURES_DTL where find_in_set(:id,Subscription_Id_Str) AND Feature_Status='ACTIVE'")
	List<SubscriptionFeaturesDtlVo> getSubscriptionFeaturedList(String id);

}
