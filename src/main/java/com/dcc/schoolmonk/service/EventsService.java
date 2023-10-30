package com.dcc.schoolmonk.service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.dcc.schoolmonk.common.ApiResponse;
import com.dcc.schoolmonk.dao.EventsDao;
import com.dcc.schoolmonk.dao.SchoolMstDao;
import com.dcc.schoolmonk.vo.EnhanceNoticeBoardVo;
import com.dcc.schoolmonk.vo.EventsVo;
import com.dcc.schoolmonk.vo.UserVo;

@Service
public class EventsService {
	private static final Logger LOGGER = Logger.getLogger(EventsService.class);

	@Autowired
	SchoolMstDao schoolMstDao;

	@Autowired
	EventsDao eventsDao;
	
	
	public ResponseEntity<ApiResponse> saveEvent(EventsVo vo, Long userId) {
		ApiResponse apiResponse = null;
		EventsVo dataSavedVo = new EventsVo();		
		try {
			if(null == vo.getId()){
				UUID uuid = UUID.randomUUID();
				LOGGER.info("Event uuid --------------- " + uuid.toString());
				vo.setEventId(uuid.toString());
				vo.setCreatedBy(userId);
				vo.setCreatedOn(new Date());
				vo.setUpdatedOn(new Date());
				dataSavedVo = vo;
			} else {
				EventsVo getData = eventsDao.findById(vo.getId()).get();
				getData.setId(vo.getId());
				getData.setCreatedBy(userId);
				getData.setUpdatedBy(userId);
				getData.setUpdatedOn(new Date());
				getData.setEventContent(vo.getEventContent());
				getData.setEventType(vo.getEventType());
				getData.setEventDate(vo.getEventDate());
				getData.setEventTime(vo.getEventTime());
				dataSavedVo = getData;
			}
			EventsVo dataSaved = eventsDao.save(dataSavedVo);			
			LOGGER.info("Event saved --------------- " + dataSaved);
			
			//We have to do something here for notification : TBD

			apiResponse = new ApiResponse(200, "success", "Event Saved Successfully", dataSaved); 
			return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			apiResponse = new ApiResponse(406, "error", "Event Save un-successful", null); 
			return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.NOT_ACCEPTABLE);
		}
	}
	
	public ResponseEntity<ApiResponse> getEventDetails(EventsVo vo) {
		LOGGER.info("getEventDetails ::eventType :: "+vo.getEventType());
		ApiResponse apiResponse = null;
				
		try {
			EventsVo dataSaved = eventsDao.findByEventId(vo.getEventId());			
			LOGGER.info("getEventDetails fetched --------------- " + dataSaved);

			apiResponse = new ApiResponse(200, "success", "Event fetched Successfully", dataSaved); 
			return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			apiResponse = new ApiResponse(406, "error", "Event fetch un-successful", null); 
			return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.NOT_ACCEPTABLE);
		}
	}
	
	public ResponseEntity<ApiResponse> getEventList(String eventType) {
		LOGGER.info("getEventList :: eventType :: "+eventType);
		ApiResponse apiResponse = null;
				
		try {
			List<EventsVo> data = eventsDao.findByEventType(eventType);			
			LOGGER.info("getEventList list --------------- " + data);

			apiResponse = new ApiResponse(200, "success", "Events fetched Successfully", data); 
			return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			apiResponse = new ApiResponse(406, "erroe", "Events Save un-successful", null); 
			return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.NOT_ACCEPTABLE);
		}
	}
	
	public ResponseEntity<ApiResponse> getAllEventsList(UserVo userVo) {
		LOGGER.info("getAllEventsList :: getAllEventsList :: ");
		ApiResponse apiResponse = null;
		if(null != userVo) {
			try {
				List<EventsVo> data = (List<EventsVo>) eventsDao.findAll();			
				LOGGER.info("Events list --------------- " + data);

				apiResponse = new ApiResponse(200, "success", "Events fetched Successfully", data); 
				return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
			} catch (Exception e) {
				e.printStackTrace();
				apiResponse = new ApiResponse(406, "erroe", "Events Save un-successful", null); 
				return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.NOT_ACCEPTABLE);
			}
		}else {
			apiResponse = new ApiResponse(401, "erroe", "Un-Authorized", null); 
			return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.UNAUTHORIZED);
		}
		
	}
	
	public ResponseEntity<ApiResponse> getEventDetailsById(String eventId) {
		LOGGER.info("getEventDetailsById :: noticeType :: "+eventId);
		ApiResponse apiResponse = null;
				
		try {
			EventsVo dataSaved = eventsDao.findByEventId(eventId);			
			LOGGER.info("getEventDetailsById fetched --------------- " + dataSaved);

			apiResponse = new ApiResponse(200, "success", "Event fetched Successfully", dataSaved); 
			return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			apiResponse = new ApiResponse(406, "error", "Notice fetch un-successful", null); 
			return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.NOT_ACCEPTABLE);
		}
	}
}
