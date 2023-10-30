package com.dcc.schoolmonk.dao;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.dcc.schoolmonk.vo.AdsOrderDetailsVo;
import com.dcc.schoolmonk.vo.AdsOrderVo;


public interface AdsOrderDao extends JpaRepository<AdsOrderVo, Long> {
   	@Query(nativeQuery = true, value="SELECT a.id as adId, a.zone_name as zoneName,a.page as page,a.no_of_ad as noOfAd,a.width_px as widthPx,a.height_px as heightPx,a.price as price,COALESCE((a.no_of_ad - COALESCE(adCount,0)), 0) as isavailable,a.description,a.is_Active as active FROM ADS_ZONE_MST a left JOIN (  select zone_id, SUM(qty) as adCount from ADS_ORDER_DETAILS group by zone_id ) o ON a.id=o.zone_id")
	List<AdsOrderVo> getallAds();
}