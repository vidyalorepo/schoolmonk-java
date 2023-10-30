package com.dcc.schoolmonk.service;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.dcc.schoolmonk.common.ApiResponse;
import com.dcc.schoolmonk.dao.SubscriptionDao;
import com.dcc.schoolmonk.dao.SubscriptionFeatuedDtlDao;
import com.dcc.schoolmonk.dao.UserRoleMappingDao;
import com.dcc.schoolmonk.dao.UserSubscriptionLogDao;
import com.dcc.schoolmonk.exception.SchoolmonkAppApplicationException;
import com.dcc.schoolmonk.vo.SubcriptionMstVo;
import com.dcc.schoolmonk.vo.SubscriptionFeaturesDtlVo;
import com.dcc.schoolmonk.vo.UserSubcriptionLogVo;
import com.dcc.schoolmonk.vo.UserVo;

@Service
public class SubscriptionService {
	
	private static final Logger LOGGER = Logger.getLogger(SubscriptionService.class);
	
	@Autowired
	SubscriptionDao subscriptionDao;
	
	@Autowired
	UserRoleMappingDao userRoleMappingDao;
	
	@Autowired
	SubscriptionFeatuedDtlDao subscriptionFeatuedDtlDao;
	
	@Autowired
	UserSubscriptionLogDao userSubscriptionLogDao;

	public ResponseEntity<ApiResponse> getSubscriptionList(UserVo userVo) {
		LOGGER.info("SubscriptionService :: Entering getSubscriptionList method");
		ResponseEntity<ApiResponse> responseEntity = null;
		ApiResponse apiResponse = null; 
		List<SubcriptionMstVo> listOfEntries = new ArrayList<>();
		if(null != userVo) {
			try {
				listOfEntries = (List<SubcriptionMstVo>) subscriptionDao.findAll();
				if (listOfEntries != null) {
					apiResponse = new ApiResponse(200, "data_found", "data_found", listOfEntries, listOfEntries.size()); // Added
					return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
				} else {
					apiResponse = new ApiResponse(200, "data_not_found", "data_not_found", null, 0); // Added
					return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.NOT_FOUND);
				}
			} catch (SchoolmonkAppApplicationException e) {
				e.getStackTrace();
			}
		}else {
			apiResponse = new ApiResponse(401, "Un-Authorized", "Token Not Found", null, 0); // Added
			return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.UNAUTHORIZED);
		}
		

		LOGGER.info("SubscriptionService :: Exiting getSubscriptionList method");
		return responseEntity;
	}

	public ResponseEntity<ApiResponse> saveSubscription(UserVo userVo, SubcriptionMstVo subcriptionMstVo) {
		LOGGER.info("SubscriptionService :: Entering saveSubscription method");
//		ResponseEntity<ApiResponse> responseEntity = null;
		ApiResponse apiResponse = null; 
		SubcriptionMstVo entry = new SubcriptionMstVo();
		if(null != userVo) {
			try {
				entry =  subscriptionDao.save(subcriptionMstVo);
				if (entry != null) {
					userRoleMappingDao.upgradeSubscription(userVo.getUserId(), entry.getId());
					apiResponse = new ApiResponse(200, "data_saved", "Subscription Added Successfully", entry, 1); // Added
					return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
				} else {
					apiResponse = new ApiResponse(200, "data_not_saved", "data_not_saved", null, 0); // Added
					return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.NOT_FOUND);
				}
			} catch (SchoolmonkAppApplicationException e) {
				e.getStackTrace();
				apiResponse = new ApiResponse(500, "data_not_saved", "Internal Server Err", null, 0); // Added
				return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}else {
			apiResponse = new ApiResponse(401, "Un-Authorized", "Token Not Found", null, 0); // Added
			return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.UNAUTHORIZED);
		}
	}

	public ResponseEntity<ApiResponse> upgradeSubscription(UserVo userVo, SubcriptionMstVo subcriptionMstVo) {
		LOGGER.info("SubscriptionService :: Entering upgradeSubscription method");
//		ResponseEntity<ApiResponse> responseEntity = null;
		ApiResponse apiResponse = null; 
		if(null != userVo) {
			try {
				userRoleMappingDao.upgradeSubscription(subcriptionMstVo.getUserId(), subcriptionMstVo.getId());
				saveSubscriptionLog(subcriptionMstVo.getId(), userVo);
				apiResponse = new ApiResponse(200, "data_saved", "subscription_upgraded_sucessfully", "", 1); // Added
				return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
			} catch (SchoolmonkAppApplicationException e) {
				e.getStackTrace();
				apiResponse = new ApiResponse(500, "data_not_saved", "Internal Server Err", null, 0); // Added
				return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}else {
			apiResponse = new ApiResponse(401, "Un-Authorized", "Token Not Found", null, 0); // Added
			return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.UNAUTHORIZED);
		}
	}
	private void saveSubscriptionLog(Long subscriptionId, UserVo user) {
		// TODO Auto-generated method stub
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			String currentDateString = simpleDateFormat.format(new Date());
		    LocalDateTime dateTime = LocalDateTime.parse(currentDateString, formatter);
		    dateTime = dateTime.plusYears(1);
		    String oneYearsAfterString = dateTime.format(formatter);
		    
		    LocalDateTime remindDate = dateTime.minusDays(10);
		    String remindDateString = remindDate.format(formatter);
		    
		    System.out.println("oneYearsAfterString-->> "+oneYearsAfterString);
		    System.out.println("Reminder date-->> " +remindDateString); 
		    
			UserSubcriptionLogVo userSubcriptionLogVo = new UserSubcriptionLogVo();
			
			SubcriptionMstVo subcriptionMstVo = new SubcriptionMstVo();
			subcriptionMstVo.setId(subscriptionId);
			userSubcriptionLogVo.setSubcriptionMstVo(subcriptionMstVo);
			
			userSubcriptionLogVo.setUserVo(user);
			userSubcriptionLogVo.setSubscriptionStatus("ACTIVE");
			userSubcriptionLogVo.setSubscriptionEmiOpt("NO");
			userSubcriptionLogVo.setSubscriptionStartDate(currentDateString);
			userSubcriptionLogVo.setSubscriptionExpiryDate(oneYearsAfterString);
			userSubcriptionLogVo.setSubscriptionReminderDate(remindDateString);
			userSubcriptionLogVo.setSubscriptionValidity("365");
			userSubcriptionLogVo.setSubscriptionValidityType("Yearly");
			userSubcriptionLogVo.setSubscriptionEmiVo(null);
			userSubcriptionLogVo.setSubscriberId(user.getSubscriberId());
			
			UUID uuid = UUID.randomUUID();
			userSubcriptionLogVo.setSubscriptionReceiptNo(uuid.toString());
			
			UUID uuid1 = UUID.randomUUID();
			userSubcriptionLogVo.setSubscriptionRefNo(uuid1.toString());
			try {
				userSubscriptionLogDao.save(userSubcriptionLogVo);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		
	}

	public ResponseEntity<ApiResponse> getSubscriptionFeaturedList(UserVo userVo) {
		ApiResponse apiResponse = null; 
		if(null != userVo) {
			try {
				List<SubcriptionMstVo> listOfEntries = new ArrayList<>();
				List<SubcriptionMstVo> listOfResultEntries = new ArrayList<>();
				listOfEntries = (List<SubcriptionMstVo>) subscriptionDao.findAll();
				if (listOfEntries.size() > 0) {
					for (SubcriptionMstVo subcriptionMstVo : listOfEntries) {
						List<SubscriptionFeaturesDtlVo> featuredList = subscriptionFeatuedDtlDao.getSubscriptionFeaturedList(subcriptionMstVo.getId().toString());
						subcriptionMstVo.setSubscriptionFeaturesDtlVo(featuredList);
						listOfResultEntries.add(subcriptionMstVo);
					}
				}
				apiResponse = new ApiResponse(200, "data_fetched", "featured_fetched_sucessfully", listOfResultEntries, listOfResultEntries.size()); // Added
				return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
			} catch (SchoolmonkAppApplicationException e) {
				e.getStackTrace();
				apiResponse = new ApiResponse(500, "data_not_saved", "Internal Server Err", null, 0); // Added
				return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}else {
			apiResponse = new ApiResponse(401, "Un-Authorized", "Token Not Found", null, 0); // Added
			return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.UNAUTHORIZED);
		}
	}

	public ResponseEntity<ApiResponse> getUserSubscriptionLog(UserVo userVo, long userId) {
		ApiResponse apiResponse = null; 
		if(null != userVo) {
			try {
				List<UserSubcriptionLogVo> listOfEntries = new ArrayList<>();
				
				List<UserSubcriptionLogVo> listOfResultEntries = new ArrayList<>();
				listOfEntries = userSubscriptionLogDao.findByUserId(userId);
				if (listOfEntries.size() > 0) {
					for (UserSubcriptionLogVo userSubcriptionLogVo : listOfEntries) {
						List<SubscriptionFeaturesDtlVo> featuredList = subscriptionFeatuedDtlDao.getSubscriptionFeaturedList(userSubcriptionLogVo.getSubcriptionMstVo().getId().toString());
						userSubcriptionLogVo.setSubscriptionFeaturesDtlVo(featuredList);
						listOfResultEntries.add(userSubcriptionLogVo);
					}
				}
				apiResponse = new ApiResponse(200, "data_fetched", "userSubs_fetched_sucessfully", listOfResultEntries, listOfResultEntries.size()); // Added
				return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
			} catch (SchoolmonkAppApplicationException e) {
				e.getStackTrace();
				apiResponse = new ApiResponse(500, "data_not_saved", "Internal Server Err", null, 0); // Added
				return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}else {
			apiResponse = new ApiResponse(401, "Un-Authorized", "Token Not Found", null, 0); // Added
			return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.UNAUTHORIZED);
		}
	}

	public ResponseEntity<ApiResponse> getAllFeaturedList(UserVo userVo) {
		ApiResponse apiResponse = null; 
		if(null != userVo) {
			try {
				List<SubscriptionFeaturesDtlVo> listOfEntries = new ArrayList<>();
				
				listOfEntries = (List<SubscriptionFeaturesDtlVo>) subscriptionFeatuedDtlDao.findAll();
				if (listOfEntries.size() > 0) {
					
					apiResponse = new ApiResponse(200, "data_fetched", "Features_fetched_sucessfully", listOfEntries, listOfEntries.size()); // Added
					return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
				}else {
					apiResponse = new ApiResponse(404, "data_not_fetched", "Features_not_fetched_sucessfully", null); // Added
					return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
				}
				
			} catch (SchoolmonkAppApplicationException e) {
				e.getStackTrace();
				apiResponse = new ApiResponse(500, "data_not_saved", "Internal Server Err", null, 0); // Added
				return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}else {
			apiResponse = new ApiResponse(401, "Un-Authorized", "Token Not Found", null, 0); // Added
			return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.UNAUTHORIZED);
		}
	}

	public ResponseEntity<ApiResponse> getSubscriptionNotification(long userId, String currentDate) {
		ApiResponse apiResponse = null; 
		if(0 != userId) {
			try {
				List<SubscriptionFeaturesDtlVo> listOfEntries = new ArrayList<>();
				
				listOfEntries = (List<SubscriptionFeaturesDtlVo>) subscriptionFeatuedDtlDao.findAll();
				if (listOfEntries.size() > 0) {
					
					apiResponse = new ApiResponse(200, "data_fetched", "Features_fetched_sucessfully", listOfEntries, listOfEntries.size()); // Added
					return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
				}else {
					apiResponse = new ApiResponse(404, "data_not_fetched", "Features_not_fetched_sucessfully", null); // Added
					return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
				}
				
			} catch (SchoolmonkAppApplicationException e) {
				e.getStackTrace();
				apiResponse = new ApiResponse(500, "data_not_saved", "Internal Server Err", null, 0); // Added
				return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}else {
			apiResponse = new ApiResponse(401, "Un-Authorized", "Token Not Found", null, 0); // Added
			return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.UNAUTHORIZED);
		}
	}

}
