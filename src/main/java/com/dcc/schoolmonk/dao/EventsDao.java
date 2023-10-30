package com.dcc.schoolmonk.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.dcc.schoolmonk.vo.EventsVo;

public interface EventsDao extends CrudRepository<EventsVo, Long>{

	EventsVo findByEventId(String eventId);

	@Query(nativeQuery = true,value = "select * from EVENTS_DETAILS where event_type = ?1")
	List<EventsVo> findByEventType(String eventType);

}
