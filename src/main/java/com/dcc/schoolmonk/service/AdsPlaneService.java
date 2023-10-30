package com.dcc.schoolmonk.service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.dcc.schoolmonk.common.ApiResponse;
import com.dcc.schoolmonk.dao.AdsOrderDao;
import com.dcc.schoolmonk.dao.AdsOrderDetailsDao;
import com.dcc.schoolmonk.dao.AdsZoneMstFDao;
import com.dcc.schoolmonk.dao.AttachmentDao;
import com.dcc.schoolmonk.dao.RoleMstDao;
import com.dcc.schoolmonk.dao.UserDao;
import com.dcc.schoolmonk.dao.UserRoleMappingDao;
import com.dcc.schoolmonk.dto.AdsDto;
import com.dcc.schoolmonk.dto.SaveAdsOrderDto;
import com.dcc.schoolmonk.exception.ResourceNotFound;
import com.dcc.schoolmonk.utils.GlobalConstants;
import com.dcc.schoolmonk.vo.AdsOrderDetailsVo;
import com.dcc.schoolmonk.vo.AdsOrderVo;
import com.dcc.schoolmonk.vo.AdszoneMstVo;
import com.dcc.schoolmonk.vo.AttachmentVo;
import com.dcc.schoolmonk.vo.Transaction;
import com.dcc.schoolmonk.vo.UserRoleMappingVo;
import com.dcc.schoolmonk.vo.UserVo;

@Service
public class AdsPlaneService {
	@Autowired
	AdsZoneMstFDao adsZoneMstFDao;

	@Autowired
	AdsOrderDetailsDao adsOrderDetailsDao;

	@Autowired
	AdsOrderDao adsOrderDao;

	@Autowired
	UserDao userDao;

	@Autowired
	RoleMstDao roleMstDao;

	@Autowired
	UserRoleMappingDao userRoleMappingDao;

	@Autowired
	AttachmentDao attachmentDao;

	private static final Logger LOGGER = LogManager.getLogger(AdsPlaneService.class);

	public ResponseEntity<ApiResponse> SaveZone(AdszoneMstVo adszoneMstVo) {

		try {

			Date currentDate = new Date();
			LocalDateTime localDateTime = currentDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

			localDateTime = localDateTime.plusMonths(1);
			DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
			// String formattedDate = myFormatObj.format(myFormatObj);
			AdszoneMstVo art = adsZoneMstFDao.save(adszoneMstVo);
			return new ResponseEntity<ApiResponse>(new ApiResponse(200, "success", " Saved Successful.", art),
					HttpStatus.CREATED);

		} catch (Exception e) {
			e.getStackTrace();
			return new ResponseEntity<ApiResponse>(new ApiResponse(500, "error", " Saved Failed.", adszoneMstVo),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	public ResponseEntity<ApiResponse> Saveorder(SaveAdsOrderDto saveAdsOrderDto) {
		UserVo userVo = new UserVo();
		AdsOrderDetailsVo adsOrderDetailsVo = new AdsOrderDetailsVo();
		AdszoneMstVo adszoneMstVo = new AdszoneMstVo();
		Transaction transaction = new Transaction();
		AdsOrderDetailsVo saveAdsOrderDetailsVo = null;
		try {
			userVo = userDao.findByEmail(saveAdsOrderDto.getEmail());
			if (userVo != null) {
				Date currentDate = new Date();
				LocalDateTime localDateTime = currentDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
				localDateTime = localDateTime.plusDays(Integer.valueOf(saveAdsOrderDto.getDuration()) * 30);
				DateTimeFormatter currentdate = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
				Date date = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());

				adszoneMstVo.setId(Long.parseLong(saveAdsOrderDto.getZone()));
				transaction.setId(1L);

				adsOrderDetailsVo.setStartDate(currentDate);
				adsOrderDetailsVo.setEndDate(date);
				adsOrderDetailsVo.setCustomerId(userVo);
				adsOrderDetailsVo.setZoneId(adszoneMstVo);
				adsOrderDetailsVo.setTransactionId(transaction);
				adsOrderDetailsVo.setIsAgree(saveAdsOrderDto.getIsAgree());
				adsOrderDetailsVo.setQty(saveAdsOrderDto.getQty());
				adsOrderDetailsVo.setDuration(saveAdsOrderDto.getDuration());

				saveAdsOrderDetailsVo = adsOrderDetailsDao.save(adsOrderDetailsVo);
			} else {
				UserVo saveUserVo = new UserVo();
				saveUserVo.setFirstName(saveAdsOrderDto.getFirstName());
				saveUserVo.setLastName(saveAdsOrderDto.getLastName());
				saveUserVo.setEmail(saveAdsOrderDto.getEmail());
				saveUserVo.setPhone(saveAdsOrderDto.getPhone());
				saveUserVo.setUserType("CUSTOMER_USER");
				saveUserVo.setCountryCode("+91");
				UserVo saveUser = userDao.save(saveUserVo);
				String roleId = roleMstDao.getRoleIdByName("CUSTOMER_USER");
				if (null != roleId) {
					UserRoleMappingVo userRoleMappingVo = new UserRoleMappingVo();
					userRoleMappingVo.setRoleId(Long.parseLong(roleId));
					userRoleMappingVo.setUserId(userVo.getUserId());
					userRoleMappingDao.save(userRoleMappingVo);
				}
				if (saveUser != null) {
					Date currentDate = new Date();
					LocalDateTime localDateTime = currentDate.toInstant().atZone(ZoneId.systemDefault())
							.toLocalDateTime();
					localDateTime = localDateTime.plusDays(30L);
					DateTimeFormatter currentdate = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
					Date date = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());

					adszoneMstVo.setId(Long.parseLong(saveAdsOrderDto.getZone()));
					transaction.setId(1L);

					adsOrderDetailsVo.setStartDate(currentDate);
					adsOrderDetailsVo.setEndDate(date);
					adsOrderDetailsVo.setCustomerId(saveUser);
					adsOrderDetailsVo.setZoneId(adszoneMstVo);
					adsOrderDetailsVo.setTransactionId(transaction);
					adsOrderDetailsVo.setIsAgree(saveAdsOrderDto.getIsAgree());
					adsOrderDetailsVo.setQty(saveAdsOrderDto.getQty());
					adsOrderDetailsVo.setDuration(saveAdsOrderDto.getDuration());

					saveAdsOrderDetailsVo = adsOrderDetailsDao.save(adsOrderDetailsVo);
				} else {
					return new ResponseEntity<ApiResponse>(
							new ApiResponse(200, "failed", "Customer Create Failed.", false), HttpStatus.BAD_REQUEST);
				}
			}
			return new ResponseEntity<ApiResponse>(
					new ApiResponse(200, "success", " Saved Successful.", saveAdsOrderDetailsVo), HttpStatus.CREATED);

		} catch (Exception e) {
			e.getStackTrace();
			return new ResponseEntity<ApiResponse>(new ApiResponse(500, "error", " Saved Failed.", saveAdsOrderDto),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public ResponseEntity<ApiResponse> getAllads(AdsOrderDetailsVo adsOrderDetailsVo) {
		ResponseEntity<ApiResponse> responseEntity = null;
		try {
			List<AdsOrderVo> adsOrderDetailsVos = adsOrderDao.getallAds();
			responseEntity = new ResponseEntity<ApiResponse>(
					new ApiResponse(200, "success", "Ads  fetch Successful.", adsOrderDetailsVos), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			responseEntity = new ResponseEntity<ApiResponse>(new ApiResponse(500, "error", "INTERNAL SERVER ERROR"),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return responseEntity;
	}

	public ResponseEntity<ApiResponse> fetchById(long id) {
		ResponseEntity<ApiResponse> responseEntity = null;
		AdsOrderDetailsVo ads = null;
		List<AttachmentVo> attachmentVos = null;
		try {
			ads = adsOrderDetailsDao.findById(id)
					.orElseThrow(() -> new ResourceNotFound(String.format("Order With Id %s Not Found", id)));
			attachmentVos = attachmentDao.findByAdsId(id);
			ads.setAttachmentVo(attachmentVos);
			responseEntity = new ResponseEntity<ApiResponse>(
					new ApiResponse(200, "success", "Ads Fetch Success", ads),
					HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			responseEntity = new ResponseEntity<ApiResponse>(new ApiResponse(500, "error", "INTERNAL SERVER ERROR"),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return responseEntity;
	}

	public ResponseEntity<ApiResponse> getallOrderDetails(AdsDto adsDto) {
		ResponseEntity<ApiResponse> responseEntity = null;
		int noOfRecords = 0;
		String whereClause = " where 1";
		String limitStr = "";
		String orderByStr = "";
		try {
			if (0 == adsDto.getPage() && 0 == adsDto.getSize()) {
				// do nothing
			} else {
				Integer size = (0 != adsDto.getSize()) ? adsDto.getSize()
						: GlobalConstants.DEFAULT_DATA_SIZE_PAGINATION;
				Integer page = (0 != adsDto.getPage() && adsDto.getPage() > 1)
						? (adsDto.getPage() - 1) * size
						: 0;
				limitStr = " limit " + size + " offset " + page;
				LOGGER.info("limitStr..." + limitStr);
			}
			if (null != adsDto.getSortBy()
					&& !adsDto.getSortBy().trim().isEmpty()) {
				orderByStr = " order by " + adsDto.getSortBy().trim() + " "
						+ adsDto.getSortDir();
			} else {
				orderByStr = " order by o.id  DESC";
			}
			if (null != adsDto.getCustomerFirstName()
					&& !adsDto.getCustomerFirstName().trim().isEmpty()) {
				LOGGER.info("adsDto.getCustomerFirstName() ------------ " + adsDto.getCustomerFirstName());
				whereClause += " and c.first_name like '%" + adsDto.getCustomerFirstName().trim() + "%'";
			}
			if (null != adsDto.getCustomerLastName()
					&& !adsDto.getCustomerLastName().trim().isEmpty()) {
				LOGGER.info("adsDto.getCustomerLastName() ------------ " + adsDto.getCustomerLastName());
				whereClause += " and c.last_name like '%" + adsDto.getCustomerLastName().trim() + "%'";
			}
			if (null != adsDto.getCustomerEmail()
					&& !adsDto.getCustomerEmail().trim().isEmpty()) {
				LOGGER.info("adsDto.getCustomerEmail() ------------ " + adsDto.getCustomerEmail());
				whereClause += " and c.email like '%" + adsDto.getCustomerEmail().trim() + "%'";
			}
			if (null != adsDto.getZone()
					&& !adsDto.getZone().trim().isEmpty()) {
				LOGGER.info("adsDto.getZone() ------------ " + adsDto.getZone());
				whereClause += " and z.zone_name like '%" + adsDto.getZone().trim() + "%'";
			}
			if (null != adsDto.getDate()) {
				LOGGER.info("adsDto.getDate() ------------ " + adsDto.getDate());
				whereClause += " and (o.start_date = " + adsDto.getDate() + " OR " + adsDto.getDate()
						+ " = o.end_date OR (" + adsDto.getDate() + ">= o.start_date AND " + adsDto.getDate()
						+ " <= o.end_date))";
			}

			LOGGER.info("whereClause ------------ " + whereClause);
			List<AdsOrderDetailsVo> adsOrderDetailsVo = adsOrderDetailsDao.getOrderList(whereClause, limitStr,
					orderByStr);
			noOfRecords = adsOrderDetailsDao.getcountOrderList(whereClause);
			responseEntity = new ResponseEntity<ApiResponse>(
					new ApiResponse(200, "success", "Order List Fetch Successful.", adsOrderDetailsVo, noOfRecords),
					HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			responseEntity = new ResponseEntity<ApiResponse>(new ApiResponse(500, "error", "INTERNAL SERVER ERROR"),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return responseEntity;
	}

	public ResponseEntity<ApiResponse> updateAdsStatus(Long adsId, Boolean status, String duration) {
		ResponseEntity<ApiResponse> responseEntity = null;
		AdsOrderDetailsVo adsOrderDetailsVo = null;
		try {
			adsOrderDetailsVo = adsOrderDetailsDao.findById(adsId).orElseThrow(
					() -> new ResourceNotFound(String.format("Advertisement With Id %s Not Found", adsId)));
			Date currentDate = new Date();
			LocalDateTime localDateTime = currentDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
			localDateTime = localDateTime.plusDays(Integer.valueOf(duration) * 30);
			Date date = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());

			adsOrderDetailsVo.setStartDate(currentDate);
			adsOrderDetailsVo.setEndDate(date);
			adsOrderDetailsVo.setStatus(status);

			adsOrderDetailsVo = adsOrderDetailsDao.save(adsOrderDetailsVo);
			responseEntity = new ResponseEntity<ApiResponse>(
					new ApiResponse(200, "success", "Advertisement Status has been updated.", adsOrderDetailsVo, 1),
					HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			responseEntity = new ResponseEntity<ApiResponse>(new ApiResponse(500, "error", "INTERNAL SERVER ERROR"),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return responseEntity;
	}

	public ResponseEntity<ApiResponse> FetchAdsAttachmentBYZoneId(List<String> zoneName) {
		ResponseEntity<ApiResponse> responseEntity = null;
		AdszoneMstVo adszoneMstVo = null;
		List<AttachmentVo> attachmentVoList = null;
		Map<String, List<AttachmentVo>> attachmentListwithkey = new HashMap<>();
		List<AdsOrderDetailsVo> adsOrderDetailsVoList = null;
		try {
			for (String zone : zoneName) {
				adszoneMstVo = adsZoneMstFDao.findByZoneName(zone);
				if (adszoneMstVo != null) {
					adsOrderDetailsVoList = adsOrderDetailsDao.findbyzoneIdandStatus(adszoneMstVo.getId());
					for (AdsOrderDetailsVo adsOrderDetailsVo : adsOrderDetailsVoList) {
                        attachmentVoList = attachmentDao.findByAdsId(adsOrderDetailsVo.getId());
						if (attachmentListwithkey.containsKey(zone.replaceAll("\\s+","").replace("-", ""))) {
							List<AttachmentVo> tempattachmentVoList = null;
							tempattachmentVoList=attachmentListwithkey.get(zone.replaceAll("\\s+","").replace("-", ""));
							tempattachmentVoList.addAll(attachmentVoList);
						}else{
                        attachmentListwithkey.put(zone.replaceAll("\\s+","").replace("-", ""), attachmentVoList);
						}
					}
				} else {
					responseEntity = new ResponseEntity<ApiResponse>(
							new ApiResponse(400, "error", "Zone not found with zone name", zone, 0),
							HttpStatus.BAD_REQUEST);
				}
               adszoneMstVo=null;
			   attachmentVoList=null;
			   adsOrderDetailsVoList=null;
			}
		 responseEntity = new ResponseEntity<ApiResponse>(new ApiResponse(200, "success", "Zone wise Attachment found", attachmentListwithkey, 0),
							HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			responseEntity = new ResponseEntity<ApiResponse>(new ApiResponse(500, "error", "INTERNAL SERVER ERROR"),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return responseEntity;
	}

}
