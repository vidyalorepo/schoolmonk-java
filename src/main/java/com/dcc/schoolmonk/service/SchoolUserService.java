package com.dcc.schoolmonk.service;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.dcc.schoolmonk.SchoolmonkApp.ExtPropertyReader;
import com.dcc.schoolmonk.common.ApiResponse;
import com.dcc.schoolmonk.common.CommmonFunction;
import com.dcc.schoolmonk.common.GlobalConstants;
import com.dcc.schoolmonk.common.RomanNumber;
import com.dcc.schoolmonk.common.UniqueIDGenerator;
import com.dcc.schoolmonk.dao.AdmissionStatusMstDao;
import com.dcc.schoolmonk.dao.AttachmentDao;
import com.dcc.schoolmonk.dao.ClassMstDao;
import com.dcc.schoolmonk.dao.DistrictMstDao;
import com.dcc.schoolmonk.dao.RoleMstDao;
import com.dcc.schoolmonk.dao.SchoolAcademicDtlDao;
import com.dcc.schoolmonk.dao.SchoolAdmissionDtlDao;
import com.dcc.schoolmonk.dao.SchoolInfraDtlDao;
import com.dcc.schoolmonk.dao.SchoolLevelDtlDao;
import com.dcc.schoolmonk.dao.SchoolMstBulkDao;
import com.dcc.schoolmonk.dao.SchoolMstDao;
import com.dcc.schoolmonk.dao.SchoolStreamDtlDao;
import com.dcc.schoolmonk.dao.SchoolStreamSubjectDtlDao;
import com.dcc.schoolmonk.dao.SchoolStudentEligibilityDtlDao;
import com.dcc.schoolmonk.dao.SchoolStudentMappingDao;
import com.dcc.schoolmonk.dao.SchoolTimingDtlDao;
import com.dcc.schoolmonk.dao.StateMstDao;
import com.dcc.schoolmonk.dao.UserDao;
import com.dcc.schoolmonk.dao.UserRoleMappingDao;
import com.dcc.schoolmonk.dao.UserSubscriptionLogDao;
import com.dcc.schoolmonk.exception.ResourceNotFound;
import com.dcc.schoolmonk.vo.AdmissionStatusMstVo;
import com.dcc.schoolmonk.vo.AttachmentVo;
import com.dcc.schoolmonk.vo.CodeValueVo;
import com.dcc.schoolmonk.vo.CodeValuesVo;
import com.dcc.schoolmonk.vo.SchoolAcademicDtlVo;
import com.dcc.schoolmonk.vo.SchoolAdmissionDtlVo;
import com.dcc.schoolmonk.vo.SchoolClassDtlVo;
import com.dcc.schoolmonk.vo.SchoolInfraDtlVo;
import com.dcc.schoolmonk.vo.SchoolLevelDtlVo;
import com.dcc.schoolmonk.vo.SchoolMstVo;
import com.dcc.schoolmonk.vo.SchoolSearchInputVo;
import com.dcc.schoolmonk.vo.SchoolStreamDtlVo;
import com.dcc.schoolmonk.vo.SchoolStreamSubjectDtlVo;
import com.dcc.schoolmonk.vo.SchoolStudentMappingVo;
import com.dcc.schoolmonk.vo.SchoolTimingDtlVo;
import com.dcc.schoolmonk.vo.SubcriptionMstVo;
import com.dcc.schoolmonk.vo.SystemAdminDashboardVo;
import com.dcc.schoolmonk.vo.UserRoleMappingVo;
import com.dcc.schoolmonk.vo.UserSearchInputVo;
import com.dcc.schoolmonk.vo.UserSubcriptionLogVo;
import com.dcc.schoolmonk.vo.UserVo;

@Service
public class SchoolUserService {

	private static final Logger LOGGER = Logger.getLogger(SchoolUserService.class);

	@Autowired
	SchoolMstDao schoolMstDao;

	@Autowired
	SchoolMstBulkDao schoolMstBulkDao;

	@Autowired
	MailService mailService;

	@Autowired
	UserRoleMappingDao userRoleMappingDao;

	@Autowired
	RoleMstDao roleMstDao;

	@Autowired
	UserDao userDao;

	@Autowired
	AttachmentDao attachmentDao;

	@Autowired
	SchoolAdmissionDtlDao schoolAdmissionDtlDao;

	@Autowired
	SchoolInfraDtlDao schoolInfraDtlDao;

	@Autowired
	SchoolAcademicDtlDao schoolAcademicDtlDao;

	@Autowired
	SchoolTimingDtlDao schoolTimingDtlDao;

	@Autowired
	SchoolStudentEligibilityDtlDao schoolStudentEligibilityDtlDao;

	@Autowired
	ClassMstDao classMstDao;

	@Autowired
	SchoolLevelDtlDao schoolLevelDtlDao;

	@Autowired
	SchoolStudentMappingDao schoolStudentMappingDao;

	@Autowired
	DistrictMstDao districtMstDao;

	@Autowired
	StateMstDao stateMstDao;

	@Autowired
	SchoolStreamDtlDao schoolStreamDtlDao;

	@Autowired
	SchoolStreamSubjectDtlDao schoolStreamSubjectDtlDao;

	@Autowired
	AdmissionStatusMstDao admissionStatusMstDao;
	
	@Autowired
	UserSubscriptionLogDao userSubscriptionLogDao;
	
	public List<SchoolMstVo> getAllEntries() {
		LOGGER.info("SchoolUserService:: getAllEntries:: " + "Entering getAllEntries method:: userKey:: ");

		List<SchoolMstVo> entry = schoolMstDao.findAllByOrderByUpdatedOnDesc();
		for (SchoolMstVo vo : entry) {
			vo = getSchoolInfo(vo);
		}

		return entry;
	}

//	@Transactional
	public ResponseEntity<ApiResponse> register(SchoolMstVo vo, Long createdByUserId) {
		ApiResponse apiResponse = null;
		UserVo user = new UserVo();
		vo.setCreatedBy(createdByUserId);
		try {
			UUID uuid = UUID.randomUUID();
			// Save in User table
			user.setFirstName(vo.getContactPersonFirstName());
			user.setLastName(vo.getContactPersonLastName());
			user.setEmail(vo.getContactEmail());
			user.setPhoneCountryCode(vo.getPhoneCountryCode());
			user.setPhone(vo.getContactPhone());
			String otp = String.valueOf(UniqueIDGenerator.OTP(6));
			LOGGER.info("otp ------------- " + otp);
			user.setOtp(otp);
			user.setUserType("SCHOOL_USER");
			user.setToken(uuid.toString());
			// set username and dadid for JWT token, these two must be same, otherwise not
			// working
			/*
			 * String id = vo.getContactEmail().split("@")[0] + otp;
			 * user.setDadId(id);
			 * user.setUsername(id);
			 */
			String subscriberId = generateSubscriberId();
			user.setSubscriberId(subscriberId);
			UserVo userSaved = userDao.save(user);
			LOGGER.info("Contact person details saved --------------- " + userSaved);

			// save in schoolMst
			vo.setContactUser(userSaved);

			// set school id :-->>
			/*UniqueIDGenerator uid = new UniqueIDGenerator();
			String genUniqueId = uid.generateSchoolID();
			vo.setSchoolId(genUniqueId);*/
			/*Long totalCount = schoolMstDao.getTotalCount();
			if (totalCount > 0) {
				vo.setSchoolId(genUniqueId + totalCount);
			} else {
				vo.setSchoolId(genUniqueId + "1");
			}*/
			vo.setSchoolId(uuid.toString());
			vo.setCreationMode("Registration");
			//also add slug
			vo.setSchoolNameSlug(CommmonFunction.slugNameGeneration(vo.getSchoolName()));
			SchoolMstVo schoolSaved = schoolMstDao.save(vo);
			LOGGER.info("SchoolMstVo saved --------------- " + schoolSaved);

			// update schoolId in user table also
			userDao.updateSchoolIdOfUser(schoolSaved.getId(), userSaved.getUserId());
			LOGGER.info("School id updated for schooluser ------------------ " + userSaved.getUserId());

			// send mail with OTP , this will send to Mobile no later , after mobile otp
			// integration
			StringBuilder mailBody = new StringBuilder();

			String subject = ExtPropertyReader.registerInitSubject;
			String mainBody = ExtPropertyReader.registerInitBody;
			mailBody.append(mainBody.replace("<otp>", otp));
			// Send Mail
			mailService.sendMail(vo.getContactEmail(), subject, mailBody.toString(), null);

			mailBody = new StringBuilder();
			subject = ExtPropertyReader.updatePwdSubject;
			mainBody = ExtPropertyReader.updatePwdBody;
			mailBody.append(mainBody.replace("<uid>", uuid.toString()));
			// Send Mail
			mailService.sendMail(vo.getContactEmail(), subject, mailBody.toString(), null);

			// get roleId for SChool_USER in DB
			String roleId = roleMstDao.getRoleIdByName("SCHOOL_USER");
			if (null != roleId) {
				// save a data in UserRoleMappingVo
				UserRoleMappingVo userRoleMappingVo = new UserRoleMappingVo();
				userRoleMappingVo.setRoleId(Long.parseLong(roleId));
				userRoleMappingVo.setUserId(user.getUserId());
				userRoleMappingVo.setSchoolId(schoolSaved.getId());
				
				//Added for Subscription
				if(vo.getSubscriptionId() != null) {
					SubcriptionMstVo subcriptionMstVo = new SubcriptionMstVo();
					subcriptionMstVo.setId(vo.getSubscriptionId());
					userRoleMappingVo.setSubcriptionMstVo(subcriptionMstVo);
				}else {
					userRoleMappingVo.setSubcriptionMstVo(null);
				}
				
				userRoleMappingDao.save(userRoleMappingVo);
				saveSubscriptionLog(vo.getSubscriptionId(), user);
				LOGGER.info("DATA SAVED in UserRoleMappingVo : " + userRoleMappingVo);
			}

			apiResponse = new ApiResponse(200, "registration initialized", "registration initialized", user); // Added
			return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
		} catch (DataIntegrityViolationException div) {
			LOGGER.error("DataIntegrityViolationException ---------- " + div.getMessage());
			user.setOtp(null);
			apiResponse = new ApiResponse(406, "duplicate_entry", "User already exist with this email or phone number.",null); // Added
			return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.NOT_ACCEPTABLE);
		} catch (Exception e) {
			e.printStackTrace();
			apiResponse = new ApiResponse(500, "registration not initialized", "registration init un-successful", null); // Added
			return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	private String generateSubscriberId() {
	// TODO Auto-generated method stub
		int schoolUserLen = userDao.getSchoolUserLen();
		String result = "SUB-";
		if (schoolUserLen == 0) {
			result = "000001"; 
		}else if (schoolUserLen != 0 && schoolUserLen >= 9) {
			result = "00000" + schoolUserLen + 1;
		}
		else if (schoolUserLen >= 10) {
			result = "0000" + schoolUserLen + 1;
		}else if (schoolUserLen >= 100) {
			result = "000" + schoolUserLen + 1;
		}else if (schoolUserLen >= 1000) {
			result = "00" + schoolUserLen + 1;
		}else if (schoolUserLen >= 10000) {
			result = "0" + schoolUserLen + 1;
		}else {
			result += schoolUserLen + 1;
		}
		
	return result;
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

	public ResponseEntity<ApiResponse> getByToken(String token) {
		ApiResponse apiResponse = null;
		try {
			UserVo userSaved = userDao.findByToken(token);
			LOGGER.info("Contact person details found --------------- " + userSaved);

			if (null != userSaved) {
				SchoolMstVo schoolSaved = schoolMstDao.findByContactUserUserId(userSaved.getUserId());
				LOGGER.info("SchoolMstVo found --------------- " + schoolSaved);

				apiResponse = new ApiResponse(200, "school_found", "school_found", schoolSaved); // Added
				return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
			} else {
				apiResponse = new ApiResponse(200, "school_not_found", "school_not_found", null); // Added
				return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.NOT_FOUND);
			}

		} catch (Exception e) {
			e.printStackTrace();
			apiResponse = new ApiResponse(406, "school_not_found", "school_not_found", null); // Added
			return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.NOT_ACCEPTABLE);
		}
	}

	public SchoolMstVo getEntryById(long id) throws Exception {

		LOGGER.info("SchoolMstService:: getEntryById:: " + "Entering getEntry method:: ID:: " + id + " SchoolName "+ schoolMstDao.findSchoolNameById(id));
		
		SchoolMstVo entry = schoolMstDao.findById(id).get();
		List<SchoolAcademicDtlVo> schoolAcademicDtlVo=schoolAcademicDtlDao.findwithLimit(id);
		if (entry == null) {
			throw new Exception("Entry with id " + id + " not found");
		}
		entry.setSchoolAcademicDtlVo(schoolAcademicDtlVo);
		entry = getSchoolInfo(entry);
		if (null != entry.getProfileStep()) {
			Set<String> setSteps = Stream.of(entry.getProfileStep().trim().split("\\s*,\\s*"))
					.collect(Collectors.toSet());
			entry.setProfileStepSet(setSteps);
		}

		return entry;
	}
	
	//add by mouli 
	
	public ResponseEntity<ApiResponse> getEntryBySchoolId(SchoolAdmissionDtlVo vo, Long schoolId) throws Exception{
		LOGGER.info("SchoolMstService:: getEntryById:: " + "Entering getEntry method:: ID:: " + schoolId + " SchoolName "+ schoolMstDao.findSchoolNameById(schoolId));
	
		ApiResponse apiResponse = null;
		String whereClause = "";
		String limitStr = "";
		String orderByStr = "";
		if (null == vo.getPage() && null == vo.getSize()) {
			// do nothing
		} else {
			Integer size = (null != vo.getSize()) ? vo.getSize() : GlobalConstants.DEFAULT_DATA_SIZE_PAGINATION;
			Integer page = (null != vo.getPage() && vo.getPage() > 1) ? (vo.getPage() - 1) * size : 0;
			limitStr = " limit " + size + " offset " + page;
			LOGGER.info("limitStr..." + limitStr);
		}
		if (null != vo.getOrderByColName() && !vo.getOrderByColName().trim().isEmpty() &&
				null != vo.getOrderBy() && !vo.getOrderBy().trim().isEmpty()) {
			orderByStr = " order by " + vo.getOrderByColName().trim() + " " + vo.getOrderBy();
		} else {
			orderByStr = " order by ID ASC";
		}
		LOGGER.info("orderByStr ------------ " + orderByStr);
		
		if(null != schoolId ) {
			LOGGER.info(schoolId);
			whereClause += "and School_Id=" +schoolId;
		}
			
//		if (null != vo.getAcademicYear() && !vo.getAcademicYear().trim().isEmpty()) {
//			LOGGER.info("vo.getAcademicYear() ------------ " + vo.getAcademicYear());
//			whereClause += " and Academic_Year like '%" + vo.getAcademicYear().trim() + "%'";
//		}
//		
		if (null != vo.getBoard() && !vo.getBoard().trim().isEmpty()) {
			LOGGER.info("vo.getBoard() ------------ " + vo.getBoard());
			whereClause += " and Board like '%" + vo.getBoard().trim() + "%'";
		}
		if (null != vo.getClassRange() && !vo.getClassRange().trim().isEmpty()) {
			LOGGER.info("vo.getClassRange() ------------ " + vo.getClassRange());
			whereClause += " and Class_Range like '%" + vo.getClassRange().trim() + "%'";
		}
		if (null != vo.getAdmissionStartDate() && !vo.getAdmissionStartDate().toString().trim().isEmpty()) {
			LOGGER.info("vo.getAdmissionStartDate() ------------ " + vo.getAdmissionStartDate());
			whereClause += " and Admission_Start_Date '%" + vo.getAdmissionStartDate().toString().trim() + "%'";
		}
		if (null != vo.getAdmissionEndDate() && !vo.getAdmissionEndDate().toString().trim().isEmpty()) {
			LOGGER.info("vo.getAdmissionEndDate() ------------ " + vo.getAdmissionEndDate());
			whereClause += " and Admission_End_Date '%" + vo.getAdmissionEndDate().toString().trim() + "%'";
		}
		
		List<SchoolAdmissionDtlVo> entry = schoolAdmissionDtlDao.findAdmissionNoticeById(whereClause, limitStr, orderByStr);
		if (entry == null) {
			throw new Exception("Entry with id " + schoolId + " not found");
		}
//		entry = getSchoolInfo(entry);
//		if (null != entry.getProfileStep()) {
//			Set<String> setSteps = Stream.of(entry.getProfileStep().trim().split("\\s*,\\s*"))
//					.collect(Collectors.toSet());
//			entry.setProfileStepSet(setSteps);
//		}
		System.out.println("entry"+entry);
		int totalNo = schoolAdmissionDtlDao.getTotalCountByInput(whereClause, "SCHOOL_ADMISSION_DTL").intValue();
		LOGGER.info("SchoolUserService:: getEntryById:: Exiting getEntryById method:: ");
		apiResponse = new ApiResponse(200, "success", "Admission List fetched Successfully", entry, totalNo);
		LOGGER.info("NearMisssReportService::findById::Exiting...");
		return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
		
//		return entry;
	}

	public SchoolMstVo getEntryByUserId(long id) throws Exception {
		LOGGER.info("SchoolMstService:: getEntryByUserId:: " + "Entering getEntryByUserId method:: ID:: " + id);

		SchoolMstVo entry = null;

		entry = schoolMstDao.getSchoolByUserId(id);

		if (entry == null) {
			throw new Exception("Entry with id " + id + " not found");
		}
		return entry;
	}

	public ResponseEntity<ApiResponse> editEntryById(SchoolMstVo schoolMstVo) throws Exception {
		LOGGER.info("SchoolMstService:: editEntryById:: " + "Entering editEntry method:: ");

		SchoolMstVo entry = null;
		ResponseEntity<ApiResponse> responseEntity = null;
		ApiResponse apiResponse = null;

		// when login using contact person credential, then fetch school details by
		// school contact person id
		if (schoolMstVo.getSchoolUserId() != null) {
			entry = schoolMstDao.getSchoolByUserId(schoolMstVo.getSchoolUserId());
			schoolMstVo.setId(entry.getId());
		} else {
			entry = (schoolMstDao.findById(schoolMstVo.getId()).get());
		}

		if (entry == null) {
			throw new Exception("editEntry with id " + schoolMstVo.getId() + " not found");
		} else {
			if (null != schoolMstVo.getSchoolLevelDtlVo() && schoolMstVo.getSchoolLevelDtlVo().size() > 0) {

				// Same school level cant repeat
				Map<String, Long> counting = schoolMstVo.getSchoolLevelDtlVo().stream().collect(
						Collectors.groupingBy(SchoolLevelDtlVo::getSchoolLevelName, Collectors.counting()));
				for (Map.Entry<String, Long> val : counting.entrySet()) {
					LOGGER.info("Key = " + val.getKey() + ", Value = " + val.getValue());
					if (val.getValue() > 1) {
						apiResponse = new ApiResponse(406, "Success", "School Level must be unique.", entry);
						return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.NOT_ACCEPTABLE);
					}
				}

				/*
				 * //For higher secondary, stream is mandatory
				 * SchoolLevelDtlVo streamPresence = schoolMstVo.getSchoolLevelDtlVo().stream()
				 * .filter(level -> level.getToClassNo().equals(12))
				 * .findAny()
				 * .orElse(null);
				 * LOGGER.info("streamPresence = " + streamPresence);
				 * if(null != streamPresence && (null == streamPresence.getStream() ||
				 * streamPresence.getStream().trim().isEmpty())){
				 * apiResponse = new ApiResponse(406, "Success",
				 * "Stream is mandatory for Higher Secondary level.", entry);
				 * return new ResponseEntity<ApiResponse>(apiResponse,
				 * HttpStatus.NOT_ACCEPTABLE);
				 * }
				 */
			}

			entry = schoolMstDao.save(schoolMstVo);
			if (entry != null) {
				updateProfileSteps(entry.getId(), schoolMstVo.getProfileStepSet());
				apiResponse = new ApiResponse(200, "Success", "School details updated successfully", entry);
				responseEntity = new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
			} else {
				apiResponse = new ApiResponse(401, "ERROR", "");
				responseEntity = new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.BAD_REQUEST);
			}
		}

		LOGGER.info("SchoolMstService:: editEntryById:: " + "Exiting editEntry method:: ");

		return responseEntity;
	}

	public List<SchoolMstVo> getSchoolBySearch(String schoolName, String board, String medium, Integer page,
			Integer size, String sort) {
		LOGGER.info("SchoolMstService:: getSchoolBySearch:: " + "Entering getSchoolBySearch method:: ");
		List<SchoolMstVo> entry = null;
		// List<SchoolMstVo> entry = schoolMstDao.getSchoolBySearch(schoolName);
		String whereClause = "";
		String limitStr = " limit 20 offset 0";
		String orderByStr = " order by School_Name ASC";
		LOGGER.info("orderByStr ------------ " + orderByStr);
		/*
		 * if(null != schoolName && !schoolName.trim().isEmpty() &&
		 * (null == board || board.isEmpty()) &&
		 * (null == medium || medium.isEmpty())){
		 * LOGGER.info("schoolName ------------ "+schoolName);
		 * whereClause +=
		 * " and (School_Name like '%"+schoolName.trim()+"%' or School_Address like '%"
		 * +schoolName.trim()+"%' "
		 * + " or Address_Line_Two like '%"+schoolName.trim()+"%' or Land_Mark like '%"
		 * +schoolName.trim()+"%' "
		 * +
		 * " or City like '%"+schoolName.trim()+"%' or Postal_Code like '%"+schoolName.
		 * trim()+"%' )";
		 * LOGGER.info("whereClause -------- "+whereClause);
		 * entry = schoolMstDao.searchByinput(whereClause, limitStr, orderByStr);
		 * } else {
		 */
		if (null != schoolName && !schoolName.trim().isEmpty()) {
			whereClause += " and (School_Name like '%" + schoolName.trim() + "%' or School_Address like '%"
					+ schoolName.trim() + "%' "
					+ " or Address_Line_Two like '%" + schoolName.trim() + "%' or Land_Mark like '%" + schoolName.trim()
					+ "%' "
					+ " or City like '%" + schoolName.trim() + "%' or Postal_Code like '%" + schoolName.trim() + "%' )";
		}

		if (null != board && !board.trim().isEmpty()) {
			// whereClause += " and board_name = '"+board+"'";
			whereClause += " and School_Board like '%" + board.trim() + "%' "; // Board
		}

		if (null != medium && !medium.trim().isEmpty()) {
			// whereClause += " and school_medium = '"+medium+"'";
			whereClause += " and School_Medium like '%" + medium.trim() + "%' "; // Medium
		}
		LOGGER.info("whereClause ------------- " + whereClause);
		entry = schoolMstDao.commonSearchSchoolByinput(whereClause + orderByStr + limitStr);
		// }

		for (SchoolMstVo vo : entry) {
			vo = getSchoolInfo(vo);
		}

		LOGGER.info("SchoolMstService:: getSchoolBySearch:: " + "Exiting getSchoolBySearch method:: ");
		return entry;
	}

	public List<SchoolMstVo> getAdmissionOpenSchool() {
		LOGGER.info("SchoolMstService:: Entering getAdmissionOpenSchool method:: ");

		List<SchoolMstVo> entry = schoolMstDao.getAdmissionOpenSchool();
		for (SchoolMstVo vo : entry) {
			vo = getSchoolInfo(vo);
		}

		LOGGER.info("SchoolMstService:: Exiting getAdmissionOpenSchool method:: ");
		return entry;
	}

	public List<SchoolMstVo> getFeaturedSchool() {
		LOGGER.info("SchoolMstService:: Entering getFeaturedSchool method:: ");

		List<SchoolMstVo> entry = schoolMstDao.getFeaturedSchool();
		for (SchoolMstVo vo : entry) {
			vo = getSchoolInfo(vo);
		}

		LOGGER.info("SchoolMstService:: Exiting getFeaturedSchool method:: ");
		return entry;
	}

	// Search from admin login
	public ResponseEntity<ApiResponse> getSchoolByInput(SchoolMstVo vo) {
		LOGGER.info("SchoolMstService:: getSchoolByInput:: Entering getSchoolByInput method:: ");
		ApiResponse apiResponse = null;
		String whereClause = "";
		String limitStr = "";
		String orderByStr = "";
		if (null == vo.getPage() && null == vo.getSize()) {
			// do nothing
		} else {
			Integer size = (null != vo.getSize()) ? vo.getSize() : GlobalConstants.DEFAULT_DATA_SIZE_PAGINATION;
			Integer page = (null != vo.getPage() && vo.getPage() > 1) ? (vo.getPage() - 1) * size : 0;
			limitStr = " limit " + size + " offset " + page;
			LOGGER.info("limitStr..." + limitStr);
		}
		if (null != vo.getOrderByColName() && !vo.getOrderByColName().trim().isEmpty() &&
				null != vo.getOrderBy() && !vo.getOrderBy().trim().isEmpty()) {
			orderByStr = " order by " + vo.getOrderByColName().trim() + " " + vo.getOrderBy();
		} else {
			orderByStr = " order by ID ASC";
		}
		LOGGER.info("orderByStr ------------ " + orderByStr);

		if (null != vo.getSchoolName() && !vo.getSchoolName().trim().isEmpty()) {
			LOGGER.info("vo.getSchoolName() ------------ " + vo.getSchoolName());
			whereClause += " and School_Name like '%" + vo.getSchoolName().trim() + "%'";
		}
		if (null != vo.getContactPhone() && !vo.getContactPhone().trim().isEmpty()) {
			LOGGER.info("vo.getContactPhone() ------------ " + vo.getContactPhone());
			whereClause += " and Contact_Phone like '%" + vo.getContactPhone().trim() + "%'";
		}
		if (null != vo.getSchoolStatus() && !vo.getSchoolStatus().trim().isEmpty()) {
			LOGGER.info("vo.getSchoolStatus() ------------ " + vo.getSchoolStatus());
			whereClause += " and School_Status = '" + vo.getSchoolStatus().trim() + "'";
		}
		if (null != vo.getContactEmail() && !vo.getContactEmail().trim().isEmpty()) {
			LOGGER.info("vo.getContactEmail() ------------ " + vo.getContactEmail());
			whereClause += " and Contact_Email like '%" + vo.getContactEmail().trim() + "%'";
		}
		if (null != vo.getContactPersonFirstName() && !vo.getContactPersonFirstName().trim().isEmpty()) {
			LOGGER.info("vo.getContactPersonFirstName() ------------ " + vo.getContactPersonFirstName());
			whereClause += " and school_user_id in (select userId from USER_DETAILS where first_name like '%"
					+ vo.getContactPersonFirstName().trim() + "%' ) ";
		}
		if (null != vo.getSchoolAddress() && !vo.getSchoolAddress().trim().isEmpty()) {
			LOGGER.info("vo.getSchoolAddress() ------------ " + vo.getSchoolAddress());
			whereClause += " and (School_Address like '%" + vo.getSchoolAddress().trim() + "%' OR "
					+ "Address_Line_Two like '%" + vo.getSchoolAddress().trim() + "%') ";
		}
		List<SchoolMstVo> entry = schoolMstDao.searchByinput(whereClause, limitStr, orderByStr);
		for (SchoolMstVo voNew : entry) {
			voNew = getSchoolInfo(voNew);
		}
		int totalNo = schoolMstDao.getTotalCountByInput(whereClause, "SCHOOL_MST").intValue();
		LOGGER.info("SchoolMstService:: getSchoolByInput:: Exiting getSchoolByInput method:: ");
		apiResponse = new ApiResponse(200, "success", "success", entry, totalNo);
		LOGGER.info("NearMisssReportService::searchByInput::Exiting...");
		return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
	}

	public Page<SchoolMstVo> findPaginated(int page, int size) {
		return schoolMstDao.findAll(PageRequest.of(page, size));
	}

	public SchoolMstVo saveSchoolAdmissionDtl(SchoolMstVo schoolMstVo) throws Exception {
		LOGGER.info("SchoolMstService:: saveSchoolAdmissionDtl:: Entering :: ");

		try {
			List<SchoolAdmissionDtlVo> saved = schoolAdmissionDtlDao.saveAll(schoolMstVo.getSchoolAdmissionDtlVo());
			LOGGER.info("SchoolAdmissionDtl updated : " + saved);
		} catch (Exception e) {
			e.printStackTrace();
		}
		LOGGER.info("SchoolMstService:: saveSchoolAdmissionDtl:: Exiting :: ");

		return schoolMstVo;
	}

	public ResponseEntity<ApiResponse> saveSchoolInfraDtl(SchoolMstVo schoolMstVo) {
		LOGGER.info("SchoolMstService:: saveSchoolInfraDtl:: Entering :: ");
		ApiResponse apiResponse = null;
		try {
			List<SchoolInfraDtlVo> saved = schoolInfraDtlDao.saveAll(schoolMstVo.getSchoolInfraDtlVo());
			LOGGER.info("saveSchoolInfraDtl updated : " + saved);
			LOGGER.info("SchoolMstService:: saveSchoolInfraDtl:: Exiting :: ");

			updateProfileSteps(schoolMstVo.getId(), schoolMstVo.getProfileStepSet());
			apiResponse = new ApiResponse(200, "success", "saved successfully", saved, saved.size());
			return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
		} catch (DataIntegrityViolationException dive) {
			LOGGER.error(dive.getMessage());
			apiResponse = new ApiResponse(406, "Data already exists", null, 0);
			return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.NOT_ACCEPTABLE);
		} catch (Exception e) {
			e.printStackTrace();
			apiResponse = new ApiResponse(400, "error", null, 0);
			return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.BAD_REQUEST);
		}
	}

	/*
	 * public SchoolMstVo saveSchoolStudentEligibilityDtl(SchoolMstVo schoolMstVo)
	 * throws Exception {
	 * LOGGER.
	 * info("SchoolMstService:: saveSchoolStudentEligibilityDtl:: Entering :: ");
	 * 
	 * try {
	 * List<SchoolStudentEligibilityDtlVo> saved =
	 * schoolStudentEligibilityDtlDao.saveAll(schoolMstVo.
	 * getSchoolStudentEligibilityDtlVo());
	 * LOGGER.info("saveSchoolStudentEligibilityDtl updated : "+saved);
	 * } catch (Exception e) {
	 * e.printStackTrace();
	 * }
	 * LOGGER.
	 * info("SchoolMstService:: saveSchoolStudentEligibilityDtl:: Exiting :: ");
	 * 
	 * return schoolMstVo;
	 * }
	 */

	public ResponseEntity<ApiResponse> saveSchoolAcademicDtl(SchoolMstVo schoolMstVo) throws Exception {
		LOGGER.info("SchoolMstService:: saveSchoolAcademicDtl:: Entering :: ");
        ResponseEntity<ApiResponse> response=null;
		try {
			// Same Academic Details cant repeat
			List<SchoolAcademicDtlVo> AcademicDtlList=new ArrayList<>(schoolMstVo.getSchoolAcademicDtlVo());
            List<SchoolAcademicDtlVo> DBademicDtlList=schoolAcademicDtlDao.findbySchoolid(schoolMstVo.getId());
			AcademicDtlList.addAll(DBademicDtlList);
			LOGGER.info("saveSchoolAcademicDtl List all : " + DBademicDtlList);
			Map<String, Long> counting = AcademicDtlList.stream().collect(
					Collectors.groupingBy(SchoolAcademicDtlVo::getAcademicYear, Collectors.counting()));
			for (Map.Entry<String, Long> val : counting.entrySet()) {
				LOGGER.info("Key = " + val.getKey() + ", Value = " + val.getValue());
				if (val.getValue() > 1) {
				 
					return new ResponseEntity<ApiResponse>(new ApiResponse(406, "failed", "Academic Year must be unique.", null),HttpStatus.NOT_ACCEPTABLE);
				}
			}

			List<SchoolAcademicDtlVo> saved = schoolAcademicDtlDao.saveAll(schoolMstVo.getSchoolAcademicDtlVo());
			updateProfileSteps(schoolMstVo.getId(), schoolMstVo.getProfileStepSet());
			LOGGER.info("saveSchoolAcademicDtl updated : " + saved);
			response= new ResponseEntity<ApiResponse>(new ApiResponse(200, "Success", "School AcademicDtl has been saved", null),HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			response = new ResponseEntity<ApiResponse>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		LOGGER.info("SchoolMstService:: saveSchoolAcademicDtl:: Exiting :: ");

		return response;
	}

	public ResponseEntity<ApiResponse> saveSchoolTimingDtl(SchoolMstVo schoolMstVo) throws Exception {
		LOGGER.info("SchoolMstService:: saveSchoolTimingDtl:: Entering :: ");

		ApiResponse apiResponse = null;
		try {
			// Same school level cant repeat
			Map<String, Long> counting = schoolMstVo.getSchoolTimingDtlVo().stream().collect(
					Collectors.groupingBy(SchoolTimingDtlVo::getSchoolLevelName, Collectors.counting()));
			for (Map.Entry<String, Long> val : counting.entrySet()) {
				LOGGER.info("Key = " + val.getKey() + ", Value = " + val.getValue());
				if (val.getValue() > 1) {
					apiResponse = new ApiResponse(406, "Success", "School Level must be unique.", null);
					return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.NOT_ACCEPTABLE);
				}
			}

			List<SchoolTimingDtlVo> saved = schoolTimingDtlDao.saveAll(schoolMstVo.getSchoolTimingDtlVo());
			LOGGER.info("saveSchoolTimingDtl updated : " + saved.get(0));
		} catch (Exception e) {
			e.printStackTrace();
			apiResponse = new ApiResponse(401, "ERROR", "");
			return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.BAD_REQUEST);
		}
		LOGGER.info("SchoolMstService:: saveSchoolTimingDtl:: Exiting :: ");

		updateProfileSteps(schoolMstVo.getId(), schoolMstVo.getProfileStepSet());
		apiResponse = new ApiResponse(200, "Success", "School Timing details updated successfully", schoolMstVo);
		return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
		// return schoolMstVo;
	}

	/*
	 * public SchoolStudentEligibilityDtlVo
	 * findEligibility(SchoolStudentEligibilityDtlVo vo) throws Exception {
	 * LOGGER.info("SchoolMstService:: findEligibility:: Entering :: ");
	 * 
	 * try {
	 * SchoolStudentEligibilityDtlVo saved = null;
	 * if(null != vo.getClassStream() && !vo.getClassStream().trim().isEmpty()){
	 * //saved = schoolStudentEligibilityDtlDao.
	 * findBySchoolMstVoIdAndAcademicYearAndBoardNameAndClassRangeAndClassStream(vo.
	 * getSchoolMstVo().getId(), vo.getAcademicYear(), vo.getBoardName(),
	 * vo.getUptoClass(), vo.getClassStream().trim());
	 * } else {
	 * //saved = schoolStudentEligibilityDtlDao.
	 * findBySchoolMstVoIdAndAcademicYearAndBoardNameAndClassRange(vo.getSchoolMstVo
	 * ().getId(), vo.getAcademicYear(), vo.getBoardName(), vo.getUptoClass());
	 * }
	 * // SchoolStudentEligibilityDtlVo saved =
	 * schoolStudentEligibilityDtlDao.findById(vo.getId()).get();
	 * //findByAcademicYearAndBoardNameAndClassRange
	 * LOGGER.info("findEligibility updated : "+saved);
	 * return saved;
	 * } catch (Exception e) {
	 * e.printStackTrace();
	 * }
	 * LOGGER.info("SchoolMstService:: findEligibility:: Exiting :: ");
	 * 
	 * return null;
	 * }
	 */

	public int deleteSchoolTimingDtl(SchoolTimingDtlVo vo) throws Exception {
		LOGGER.info("SchoolMstService:: deleteSchoolTimingDtl:: Entering :: ");
		int res = 0;
		try {
			schoolTimingDtlDao.deleteById(vo.getId());
			LOGGER.info("deleteSchoolTimingDtl success : ");
			res = 1;
		} catch (Exception e) {
			e.printStackTrace();
		}
		LOGGER.info("SchoolMstService:: deleteSchoolTimingDtl:: Exiting :: ");

		return res;
	}

	/*
	 * public int deleteSchoolStudentEligibilityDtl(SchoolStudentEligibilityDtlVo
	 * vo) throws Exception {
	 * LOGGER.
	 * info("SchoolMstService:: deleteSchoolStudentEligibilityDtl:: Entering :: ");
	 * int res = 0;
	 * try {
	 * schoolStudentEligibilityDtlDao.deleteById(vo.getId());
	 * LOGGER.info("deleteSchoolStudentEligibilityDtl success : ");
	 * res = 1;
	 * } catch (Exception e) {
	 * e.printStackTrace();
	 * }
	 * LOGGER.
	 * info("SchoolMstService:: deleteSchoolStudentEligibilityDtl:: Exiting :: ");
	 * 
	 * return res;
	 * }
	 */

	public int deleteSchoolAcademicDtl(SchoolAcademicDtlVo vo) throws Exception {
		LOGGER.info("SchoolMstService:: deleteSchoolAcademicDtl:: Entering :: ");
		int res = 0;
		try {
			schoolAcademicDtlDao.deleteById(vo.getId());
			LOGGER.info("deleteSchoolAcademicDtl success : ");
			res = 1;
		} catch (Exception e) {
			e.printStackTrace();
		}
		LOGGER.info("SchoolMstService:: deleteSchoolAcademicDtl:: Exiting :: ");

		return res;
	}

	@Transactional
	public int deleteSchoolBoardClassDtl(SchoolLevelDtlVo vo) throws Exception {
		LOGGER.info("SchoolMstService:: deleteSchoolBoardClassDtl:: Entering :: ");
		int res = 0;
		try {
			vo = schoolLevelDtlDao.findById(vo.getId()).orElse(null);
			if (null != vo) {
				/*
				 * //first check if any detail exist
				 * List<Long> noOfAdmissionData =
				 * schoolAdmissionDtlDao.noOfAdmissionData(vo.getSchoolMstVo().getId(),
				 * vo.getAcademicYear(), vo.getBoard());
				 * List<Long> noOfEligibilityData =
				 * schoolStudentEligibilityDtlDao.noOfEligibilityData(vo.getSchoolMstVo().getId(
				 * ), vo.getAcademicYear(), vo.getBoard());
				 * List<Long> noOfTimingData =
				 * schoolTimingDtlDao.noOfTimingData(vo.getSchoolMstVo().getId(),
				 * vo.getAcademicYear(), vo.getBoard());
				 * 
				 * schoolLevelDtlDao.deleteById(vo.getId());
				 * LOGGER.info("deleteSchoolBoardClassDtl success : ");
				 * 
				 * //force delete other details
				 * if(noOfAdmissionData.size() > 0){
				 * schoolAdmissionDtlDao.deleteAdmissionData(noOfAdmissionData);
				 * LOGGER.info("deleteAdmissionData success : ");
				 * }
				 * 
				 * if(noOfEligibilityData.size() > 0){
				 * schoolStudentEligibilityDtlDao.deleteEligibilityData(noOfEligibilityData);
				 * LOGGER.info("deleteEligibilityData success : ");
				 * }
				 * 
				 * if(noOfTimingData.size() > 0){
				 * schoolTimingDtlDao.deleteTimingData(noOfTimingData);
				 * LOGGER.info("deleteTimingData success : ");
				 * }
				 * res = 1;
				 */}
		} catch (Exception e) {
			e.printStackTrace();
		}
		LOGGER.info("SchoolMstService:: deleteSchoolBoardClassDtl:: Exiting :: ");

		return res;
	}

	public int checkSchoolBoardDetail(SchoolLevelDtlVo vo) throws Exception {
		LOGGER.info("SchoolMstService:: checkSchoolBoardDetail:: Entering :: ");
		int res = 0;
		try {
			vo = schoolLevelDtlDao.findById(vo.getId()).orElse(null);
			if (null != vo) {
				/*
				 * //first check if any detail exist
				 * List<Long> noOfAdmissionData =
				 * schoolAdmissionDtlDao.noOfAdmissionData(vo.getSchoolMstVo().getId(),
				 * vo.getAcademicYear(), vo.getBoard());
				 * List<Long> noOfEligibilityData =
				 * schoolStudentEligibilityDtlDao.noOfEligibilityData(vo.getSchoolMstVo().getId(
				 * ), vo.getAcademicYear(), vo.getBoard());
				 * List<Long> noOfTimingData =
				 * schoolTimingDtlDao.noOfTimingData(vo.getSchoolMstVo().getId(),
				 * vo.getAcademicYear(), vo.getBoard());
				 * 
				 * if(noOfAdmissionData.size() > 0 || noOfEligibilityData.size() > 0 ||
				 * noOfTimingData.size() > 0){
				 * res = 1;
				 * } else {
				 * res = 2;
				 * }
				 */}
		} catch (Exception e) {
			e.printStackTrace();
		}
		LOGGER.info("SchoolMstService:: checkSchoolBoardDetail:: Exiting :: ");

		return res;
	}

	public int deleteSchoolAdmissionDtl(SchoolAdmissionDtlVo vo) throws Exception {
		LOGGER.info("SchoolMstService:: deleteSchoolAdmissionDtl:: Entering :: ");
		int res = 0;
		try {
			schoolAdmissionDtlDao.deleteById(vo.getId());
			LOGGER.info("deleteSchoolAdmissionDtl success : ");
			res = 1;
		} catch (Exception e) {
			e.printStackTrace();
		}
		LOGGER.info("SchoolMstService:: deleteSchoolAdmissionDtl:: Exiting :: ");

		return res;
	}

	public int deleteSchoolInfraDtl(SchoolInfraDtlVo vo) throws Exception {
		LOGGER.info("SchoolMstService:: deleteSchoolInfraDtl:: Entering :: ");
		int res = 0;
		try {
			schoolInfraDtlDao.deleteById(vo.getId());
			LOGGER.info("deleteSchoolInfraDtl success : ");
			res = 1;
		} catch (Exception e) {
			e.printStackTrace();
		}
		LOGGER.info("SchoolMstService:: deleteSchoolInfraDtl:: Exiting :: ");

		return res;
	}

	public ResponseEntity<ApiResponse> getClassListOfSchool(SchoolLevelDtlVo vo) {
		LOGGER.info("SchoolMstService:: getClassListOfSchool:: Entering :: ");
		ApiResponse apiResponse = null;
		try {
			// List<String> dataList =
			// classMstDao.getClassListOfSchool(vo.getSchoolMstVo().getId(), vo.getBoard());
			List<SchoolClassDtlVo> res = new ArrayList<>();
			/*
			 * for(String str : dataList){
			 * LOGGER.info(str);
			 * SchoolClassDtlVo single = new SchoolClassDtlVo();
			 * String[] arr = str.split(Pattern.quote("||"));
			 * single.setClassDisplay(arr[0]);
			 * single.setClassName(arr[1]);
			 * if(single.getClassName().equals("11") || single.getClassName().equals("12")){
			 * single.setClassStream(Arrays.asList(arr[2].split(",")));
			 * }
			 * res.add(single);
			 * }
			 * 
			 * LOGGER.info("SchoolMstService:: getClassListOfSchool:: Exiting :: ");
			 */
			apiResponse = new ApiResponse(200, "success", "get successfully", res, res.size());
			return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			apiResponse = new ApiResponse(400, "error", null, 0);
			return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.BAD_REQUEST);
		}
	}

	@Transactional
	public ResponseEntity<ApiResponse> updateSchoolStatus(SchoolMstVo vo, UserVo user) {
		LOGGER.info("SchoolMstService:: updateSchoolStatus:: Entering :: ");
		ApiResponse apiResponse = null;
		try {
			schoolMstDao.updateSchoolStatus(vo.getIdList(), vo.getSchoolStatus(), user.getUserId()/* , new Date() */);
			
			SchoolStatusThreadClass threadClass = new SchoolStatusThreadClass(vo.getIdList(), vo.getSchoolStatus(), user.getEmail());
			threadClass.start();
			
			LOGGER.info("SchoolMstService:: updateSchoolStatus:: Exiting :: ");
			apiResponse = new ApiResponse(200, "success", "Updated successfully", null, 1);
			return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			apiResponse = new ApiResponse(400, "error", null, 0);
			return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.BAD_REQUEST);
		}
	}

	public ResponseEntity<ApiResponse> saveSchoolAdmissionEligibilityDtl(SchoolMstVo schoolMstVo) throws Exception {
		LOGGER.info("SchoolMstService:: saveSchoolAdmissionEligibilityDtl:: Entering :: ");

		ApiResponse apiResponse = null;
		SchoolAdmissionDtlVo saved = null;
		try {
			// data will save in master form, single data save
			SchoolAdmissionDtlVo schoolAdmissionDtlVo = schoolMstVo.getSchoolAdmissionDtlVo().get(0);
			if (null == schoolAdmissionDtlVo.getId()) {
				// Same school level cant repeat
				String academicYear = "2021-2022"; // NEED TO BE DECIDED *****************************************
				String existindData = schoolAdmissionDtlDao.ifDataExist(schoolMstVo.getId(),
						academicYear/* schoolAdmissionDtlVo.getAcademicYear() */, schoolAdmissionDtlVo.getBoard(),
						schoolAdmissionDtlVo.getClassRange());
				if (null != existindData) {
					apiResponse = new ApiResponse(200, "Success",
							"School admission details already exist for class : " + schoolAdmissionDtlVo.getClassRange()
									+ "(" + schoolAdmissionDtlVo.getBoard() +
									") for this academic year.",
							schoolMstVo);
					return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
				}
			}

			 saved = schoolAdmissionDtlDao.save(schoolAdmissionDtlVo);
			LOGGER.info("saveSchoolAdmissionEligibilityDtl saved : " + saved);

		} catch (Exception e) {
			e.printStackTrace();
			apiResponse = new ApiResponse(401, "ERROR", "");
			return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.BAD_REQUEST);
		}
		LOGGER.info("SchoolMstService:: saveSchoolAdmissionEligibilityDtl:: Exiting :: ");

		apiResponse = new ApiResponse(200, "Success", "School Admission details updated successfully", saved);
		return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
		// return schoolMstVo;
	}

	public ResponseEntity<ApiResponse> getClassRange(SchoolMstVo vo) {
		LOGGER.info("SchoolMstService:: getClassRange:: Entering :: ");
		ApiResponse apiResponse = null;
		try {
			String classRange = schoolLevelDtlDao.getClassRange(vo.getId());
			String[] arr = classRange.split(",");
			List<CodeValueVo> getIntToRoman = RomanNumber.getIntToRoman(Integer.valueOf(arr[0]),
					Integer.valueOf(arr[1]));
			LOGGER.info("SchoolMstService:: getClassRange:: Exiting :: ");
			apiResponse = new ApiResponse(200, "success", "Fetch successfully", getIntToRoman, getIntToRoman.size());
			return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			apiResponse = new ApiResponse(400, "error", null, 0);
			return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.BAD_REQUEST);
		}
	}

	// ========================= private method area ====================

	public SchoolMstVo getSchoolInfo(SchoolMstVo entry) {
		// also get attachment files
		List<AttachmentVo> docList = attachmentDao.findByTxId(entry.getId());
		entry.setDocList(docList);

		for (AttachmentVo doc : docList) {
			if (doc.getDocType().equals("school_logo")) {
				entry.setSchoolLogoPath(doc.getFilePath());
			}
			if (doc.getDocType().equals("school_banner")) {
				entry.setSchoolBannerPath(doc.getFilePath());
			}
			if (doc.getDocType().equals("school_Brochure")) {
				entry.setSchoolBrochurePath(doc.getFilePath());
			}
		}

		/*
		 * if(null != entry.getAdmissionStartDate() && null !=
		 * entry.getAdmissionEndDate()){
		 * boolean res = CommmonFunction.isDateBetween(entry.getAdmissionStartDate(),
		 * entry.getAdmissionEndDate());
		 * entry.setAdmissionOpen(res);
		 * }
		 */
		// get max start and end date from admission dtl
		String getAdmissionDates = schoolAdmissionDtlDao.getAdmissionDates(entry.getId());
		if (null != getAdmissionDates && !getAdmissionDates.contains("null")) {
			String[] dates = getAdmissionDates.split(",");
			String startDate = dates[0];
			String endDate = dates[1];

			if (null != startDate && !startDate.equals("null") && null != endDate && !endDate.equals("null")) {
				boolean res = CommmonFunction.isDateBetween(startDate, endDate);
				entry.setAdmissionOpen(res);
			}
		}

		/*
		 * List<String> boards = entry.getSchoolBoardClassDtlVo().stream()
		 * .map(SchoolBoardClassDtlVo::getBoard).distinct()
		 * .collect(Collectors.toList());
		 * 
		 * List<String> mediums = entry.getSchoolBoardClassDtlVo().stream()
		 * .map(SchoolBoardClassDtlVo::getMedium).distinct()
		 * .collect(Collectors.toList());
		 * 
		 * entry.setBoard((null != boards && boards.size()>0) ? String.join(",", boards)
		 * : "");
		 * entry.setMedium((null != mediums && mediums.size()>0) ? String.join(",",
		 * mediums) : "");
		 */
		entry.setDistrictName((null != entry.getDistrict()) ? districtMstDao.getName(entry.getDistrict()) : "");
		entry.setStateName((null != entry.getState()) ? stateMstDao.getName(entry.getState()) : "");

		return entry;
	}

	public ResponseEntity<ApiResponse> getAppliedStudent(SchoolStudentMappingVo vo) {
		ApiResponse apiResponse = null;
//		String whereClause = "";
		String limitStr = "";
		String orderByStr = "";
		if (null == vo.getPage() && null == vo.getSize()) {
			// do nothing
		} else {
			Long size = (null != vo.getSize()) ? vo.getSize() : GlobalConstants.DEFAULT_DATA_SIZE_PAGINATION;
			Long page = (null != vo.getPage() && vo.getPage() > 1) ? (vo.getPage() - 1) * size : 0;
			limitStr = " limit " + size + " offset " + page;
			LOGGER.info("limitStr..." + limitStr);
		}
		if (null != vo.getOrderByColName() && !vo.getOrderByColName().trim().isEmpty() &&
				null != vo.getOrderBy() && !vo.getOrderBy().trim().isEmpty()) {
			orderByStr = " order by " + vo.getOrderByColName().trim() + " " + vo.getOrderBy();
		} else {
			orderByStr = " order by ID ASC";
		}
		
		try {
			String whereClause = "and Academic_Year = '" + vo.getAcademicYear() + "' and School_Id = "
					+ vo.getSchoolId();
			LOGGER.info(" -------whereClause---------->" + whereClause);
			if (null != vo.getAdmissionForClass() && !vo.getAdmissionForClass().trim().isEmpty()) {
				whereClause += " and Admission_For_Class = '" + vo.getAdmissionForClass() + "'";
			}
			if (null != vo.getAdmissionForBoard() && !vo.getAdmissionForBoard().trim().isEmpty()) {
				whereClause += " and Admission_For_Board = '" + vo.getAdmissionForBoard() + "'";
			}
			if (null != vo.getAdmissionForStream() && !vo.getAdmissionForStream().trim().isEmpty()) {
				whereClause += " and Admission_For_stream = '" + vo.getAdmissionForStream() + "'";
			}
			if (null != vo.getAdmissionStatus() && !vo.getAdmissionStatus().trim().isEmpty()) {
				whereClause += " and Admissionion_Status = '" + vo.getAdmissionStatus() + "'";
			}
			List<SchoolStudentMappingVo> entry = schoolStudentMappingDao.findAllgetAppliedStudent(whereClause,limitStr, orderByStr);
			
			int totalNo = schoolStudentMappingDao.getTotalCountByInput(whereClause, "SCHOOL_STUDENT_MAPPING").intValue();
			LOGGER.info("SchoolUserService:: getEntryById:: Exiting getEntryById method:: ");
			apiResponse = new ApiResponse(200, "success", "Admission List fetched Successfully", entry, totalNo);
			LOGGER.info("NearMisssReportService::findById::Exiting...");
			return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);

//			apiResponse = new ApiResponse(200, "data_found", "data_found successfully", entry, entry.size()); // Added
//			return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			apiResponse = new ApiResponse(404, "data_not_found", "data_not_found", null, 0); // Added
			return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.NOT_FOUND);
		}
	}

	public ResponseEntity<ApiResponse> getAdminDashBoard() {
		ApiResponse apiResponse = null;
		try {
			SystemAdminDashboardVo res = new SystemAdminDashboardVo();
			List<SchoolMstVo> inactiveSchoolList = schoolMstDao.findBySchoolStatus("In-Active");
			res.setInactiveSchoolList(inactiveSchoolList);
			res.setInactiveSchoolCount(Long.parseLong(String.valueOf(inactiveSchoolList.size())));
			res.setActiveSchoolCount(schoolMstDao.getSchoolCountByStatus("Active"));
			res.setFeaturedSchoolCount(schoolMstDao.getFeaturedSchoolCount());

			apiResponse = new ApiResponse(200, "data_found", "data_found successfully", res); // Added
			return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			apiResponse = new ApiResponse(404, "data_not_found", "data_not_found", null); // Added
			return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.NOT_FOUND);
		}
	}

	public ResponseEntity<ApiResponse> getActiveSchoolCount() {
		ApiResponse apiResponse = null;

		try {
			Long entry = schoolMstDao.getActiveSchoolCount();

			apiResponse = new ApiResponse(200, "data_found", "data_found successfully", entry); // Added
			return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			apiResponse = new ApiResponse(404, "data_not_found", "data_not_found", null); // Added
			return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.NOT_FOUND);
		}
	}

	public ResponseEntity<ApiResponse> getSchoolCoutByStatusNew(String status) {
		ApiResponse apiResponse = null;
		try {
			Long entry = schoolMstDao.getSchoolCountByStatus(status);

			apiResponse = new ApiResponse(200, "data_found", "data_found successfully", entry); // Added
			return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			apiResponse = new ApiResponse(404, "data_not_found", "data_not_found", null); // Added
			return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.NOT_FOUND);
		}

	}

	@Transactional
	public void updateProfileSteps(Long id, Set<String> profileStep) {
		LOGGER.info("SchoolMstService:: updateProfileSteps:: Entering :: ");
		try {
			Arrays.asList(profileStep);
			String stringSteps = String.join(", ", profileStep);
			schoolMstDao.updateProfileStep(id, stringSteps);
//			int sumRes = 0;
//			for(String str: profileStep) {
//				sumRes += Integer.parseInt(str);
//			}
//			If sum of Steps >= 23 then notify admin to active that school
			if(profileStep.containsAll(Arrays.asList("1","2","3","4","6","7"))
					|| profileStep.containsAll(Arrays.asList("1","2","3","4","5","6","7"))) {
				String emailTo = GlobalConstants.adminEmail;
				String subjectEmail = "Activate School";
		    	String bodyEmail = "School user has successfully completed all the steps. <br>"
		    			+ "This school - " + schoolMstDao.findSchoolNameById(id) + " is ready to activate.";
		    		// Send Mail
					mailService.sendMail(emailTo, subjectEmail, bodyEmail, null);
					LOGGER.info("SchoolStatusThreadClass running: mail sent to: "+emailTo);
			}
			LOGGER.info("SchoolMstService:: updateProfileSteps:: Exiting :: ");
			// return "Step Updated successfully";
		} catch (Exception e) {
			e.printStackTrace();
			// return "Error Step Updated";
		}
	}

	@Transactional
	public ResponseEntity<ApiResponse> updatePublishStatus(SchoolAdmissionDtlVo vo) {
		LOGGER.info("SchoolMstService:: updatePublishStatus:: Entering :: ");
		ApiResponse apiResponse = null;
		try {
			schoolAdmissionDtlDao.updatePublishStatus(vo.getIdList(), vo.getPublishStatus());

			LOGGER.info("SchoolMstService:: updatePublishStatus:: Exiting :: ");
			apiResponse = new ApiResponse(200, "success", "Published successfully", null, 1);
			return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			apiResponse = new ApiResponse(400, "error", null, 0);
			return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.BAD_REQUEST);
		}
	}

	public List<CodeValueVo> getClassList(Long schoolID) {
		List<CodeValueVo> result = new ArrayList<>();
		try {
			List<SchoolLevelDtlVo> schoolLevelDtlVoList = schoolLevelDtlDao.findBySchoolMstVoId(schoolID);

			if (schoolLevelDtlVoList.size() > 0) {
				for (SchoolLevelDtlVo vo : schoolLevelDtlVoList) {
					List<CodeValueVo> fetchRomanValue = RomanNumber.getIntToRoman(vo.getFromClassNo().intValue(),
							vo.getToClassNo().intValue());
					result.addAll(fetchRomanValue);
				}
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		return result;
	}

	@Transactional
	public ResponseEntity<ApiResponse> deleteStream(SchoolStreamDtlVo vo) {
		LOGGER.info("SchoolMstService:: deleteStream:: Entering :: ");
		ApiResponse apiResponse = null;
		try {
			schoolStreamDtlDao.deleteById(vo.getId());

			LOGGER.info("SchoolMstService:: deleteStream:: Exiting :: ");
			apiResponse = new ApiResponse(200, "success", "Deleted successfully", null, 1);
			return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			apiResponse = new ApiResponse(400, "error", null, 0);
			return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.BAD_REQUEST);
		}
	}

	@Transactional
	public ResponseEntity<ApiResponse> deleteSubject(SchoolStreamDtlVo vo) {
		LOGGER.info("SchoolMstService:: deleteSubject:: Entering :: ");
		ApiResponse apiResponse = null;
		try {
			schoolStreamSubjectDtlDao.deleteById(vo.getId());

			LOGGER.info("SchoolMstService:: deleteSubject:: Exiting :: ");
			apiResponse = new ApiResponse(200, "success", "Deleted successfully", null, 1);
			return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			apiResponse = new ApiResponse(400, "error", null, 0);
			return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.BAD_REQUEST);
		}
	}

	@Transactional
	public ResponseEntity<ApiResponse> deleteSubjectByType(SchoolStreamSubjectDtlVo vo) {
		LOGGER.info("SchoolMstService:: deleteSubjectByType:: Entering :: ");
		ApiResponse apiResponse = null;
		try {
			schoolStreamSubjectDtlDao.deleteBySchoolStreamDtlVoIdAndSubjectType(vo.getSchoolStreamDtlVo().getId(),
					vo.getSubjectType());

			LOGGER.info("SchoolMstService:: deleteSubjectByType:: Exiting :: ");
			apiResponse = new ApiResponse(200, "success", "Deleted successfully", null, 1);
			return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			apiResponse = new ApiResponse(400, "error", null, 0);
			return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.BAD_REQUEST);
		}
	}

	@Transactional
	public ResponseEntity<ApiResponse> admissionFindById(Long id) {
		LOGGER.info("SchoolMstService:: admissionFindById:: Entering :: ");
		ApiResponse apiResponse = null;
		try {
			SchoolAdmissionDtlVo data = schoolAdmissionDtlDao.findById(id).orElse(null);
			if (null != data) {
				for (SchoolStreamDtlVo vo : data.getSchoolStreamDtlVo()) {
					Map<String, List<SchoolStreamSubjectDtlVo>> grouped = vo.getSchoolStreamSubjectDtlVo().stream()
							.collect(Collectors.groupingBy(SchoolStreamSubjectDtlVo::getSubjectType));
					vo.setSubjectOptionsVo(grouped);
					// for(SchoolStreamSubjectDtlVo voDtl :vo.getSchoolStreamSubjectDtlVo()){
					// voDtl.setSchoolStreamSubjectDtlVoGrp(list);
					/*
					 * Map<String, List<SchoolStreamSubjectDtlVo>> schoolStreamSubjectDtlVoGrp = new
					 * HashMap<>();
					 * for (Map.Entry<String, List<SchoolStreamSubjectDtlVo>> entry :
					 * list.entrySet()) {
					 * List<SchoolStreamSubjectDtlVo> dtl = new ArrayList<>();
					 * LOGGER.info(entry.getKey() + ":" + entry.getValue());
					 * }
					 */
					// }

				}

			}

			AttachmentVo doc = attachmentDao.findByTxIdAndFormCodeSingle(data.getId(), "notice_media");
			data.setDocument(doc);
			LOGGER.info("SchoolMstService:: admissionFindById:: Exiting :: ");
			apiResponse = new ApiResponse(200, "success", "Fetched successfully", data, 1);
			return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			apiResponse = new ApiResponse(400, "error", null, 0);
			return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.BAD_REQUEST);
		}
	}

	// Added By Akash -- updated by SD
	@Transactional
	public ResponseEntity<ApiResponse> activateUser(SchoolMstVo vo, Long createdByUserId) {
		ApiResponse apiResponse = null;
		// If any user is active for this School
		UserVo existingContactPerson = userDao.findByIsActiveAndSchoolIdAndUserType(true, vo.getId(), "SCHOOL_USER"); // findByIsActiveAndEmail
		// Q: what if a person changes school.. unique email id hole unique key te jhar
		// debe..

		UserVo user = new UserVo();
		// vo.setCreatedBy(createdByUserId);
		try {
			// check if any user with same email/phone , then update -- Added by SD
			List<UserVo> existingUserList = userDao.findByEmailOrPhone(vo.getContactEmail(), vo.getContactPhone());
			if (null != existingUserList/* && existingUserList.size() == 1 */) {
				for (UserVo existingUser : existingUserList) {
					// UserVo existingUser = existingUserList.get(0);
					// check if an active user with same or other school
					if (existingUser.getIsActive() && vo.getId() == existingUser.getSchoolId()) {
						apiResponse = new ApiResponse(406, "duplicate_entry",
								"User already exist with this email or phone number as a contact person of this school.",
								user); // Added
						return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.NOT_ACCEPTABLE);
					} else if (existingUser.getIsActive() && vo.getId() != existingUser.getSchoolId()) {
						apiResponse = new ApiResponse(406, "duplicate_entry",
								"User already exist with this email or phone number as a contact person of another school.",
								user); // Added
						return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.NOT_ACCEPTABLE);
					} else if (!existingUser.getIsActive()) {
						// make old inactive user to-> new active user with new school
						user.setUserId(existingUser.getUserId());
						// break;
					}
				}
			}
			UUID uuid = UUID.randomUUID();
			// Save in User table
			user.setFirstName(vo.getContactPersonFirstName());
			user.setLastName(vo.getContactPersonLastName());
			user.setEmail(vo.getContactEmail());
			user.setPhoneCountryCode(vo.getPhoneCountryCode());
			user.setPhone(vo.getContactPhone());
			String otp = String.valueOf(UniqueIDGenerator.OTP(6));
			LOGGER.info("otp ------------- " + otp);
			user.setOtp(otp);
			user.setUserType("SCHOOL_USER");
			user.setToken(uuid.toString());
			user.setIsActive(true);
			user.setSchoolId(vo.getId());

			UserVo userSaved = userDao.save(user);
			LOGGER.info("Contact person details saved --------------- " + userSaved);

			// If present then inactive that user in user table
			if (null != existingContactPerson) {
				userDao.updateActiveStatusOfUser(existingContactPerson.getUserId());
				LOGGER.info("================== EXISTING USER DEACTIVATED ===================");
			}

			// Also delete association with new persons contact list
			List<Long> exsitingSchoolUsers = schoolMstDao.exsitingSchoolUsers(userSaved.getUserId());
			if (null != exsitingSchoolUsers && exsitingSchoolUsers.size() > 0) {
				schoolMstDao.updateExsitingSchoolUsers(exsitingSchoolUsers);
				LOGGER.info("========== EXISTING SCHOOL ASSICIATION " + exsitingSchoolUsers
						+ " IS SET TO NULL FOR USER :" + userSaved.getUserId());
			}

			// save new association in schoolMst
			SchoolMstVo existingEntry = schoolMstDao.findById(vo.getId()).get();
			SchoolMstVo schoolSaved = null;
			if (null != existingEntry) {
				existingEntry.setContactUser(userSaved);
				existingEntry.setCreatedBy(createdByUserId);
				existingEntry.setContactPersonFirstName(vo.getContactPersonFirstName());
				existingEntry.setContactPersonLastName(vo.getContactPersonLastName());
				existingEntry.setContactEmail(vo.getContactEmail());
				existingEntry.setContactPhone(vo.getContactPhone());
				schoolSaved = schoolMstDao.save(existingEntry);
			}
			LOGGER.info("SchoolMstVo saved --------------- " + schoolSaved);

			// send mail with OTP , this will send to Mobile no later , after mobile otp
			// integration
			StringBuilder mailBody = new StringBuilder();

			String subject = ExtPropertyReader.registerInitSubject;
			String mainBody = ExtPropertyReader.registerInitBody;
			mailBody.append(mainBody.replace("<otp>", otp));
			// Send Mail
			mailService.sendMail(vo.getContactEmail(), subject, mailBody.toString(), null);

			mailBody = new StringBuilder();
			subject = ExtPropertyReader.updatePwdSubject;
			mainBody = ExtPropertyReader.updatePwdBody;
			mailBody.append(mainBody.replace("<uid>", uuid.toString()));
			// Send Mail
			mailService.sendMail(vo.getContactEmail(), subject, mailBody.toString(), null);

			// get roleId for student in DB
			String roleId = roleMstDao.getRoleIdByName("SCHOOL_USER");
			if (null != roleId) {
				// save a data in UserRoleMappingVo
				UserRoleMappingVo userRoleMappingVo = new UserRoleMappingVo();
				userRoleMappingVo.setRoleId(Long.parseLong(roleId));
				userRoleMappingVo.setUserId(user.getUserId());
				userRoleMappingVo.setSchoolId(schoolSaved.getId());
				userRoleMappingDao.save(userRoleMappingVo);
				LOGGER.info("DATA SAVED in UserRoleMappingVo : " + userRoleMappingVo);
			}

			apiResponse = new ApiResponse(200, "School User Saved", "Successfully saved", user); // Added
			return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
		} catch (DataIntegrityViolationException div) {
			LOGGER.error("DataIntegrityViolationException ---------- " + div.getMessage());
			user.setOtp(null);
			apiResponse = new ApiResponse(406, "duplicate_entry", "User already exist with this email or phone number.",
					user); // Added
			return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.NOT_ACCEPTABLE);
		} catch (Exception e) {
			e.printStackTrace();
			apiResponse = new ApiResponse(406, "registration not initialized", "registration init un-successful", user); // Added
			return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.NOT_ACCEPTABLE);
		}
	}

	public List<AdmissionStatusMstVo> getAllEntriesAdmissionStatus() {
		LOGGER.info("SchoolUserService:: getAllEntriesAdmissionStatus:: "
				+ "Entering getAllEntriesAdmissionStatus method::");
	
		try {
			List<AdmissionStatusMstVo> entry = (List<AdmissionStatusMstVo>) admissionStatusMstDao.findAll();
			if (null != entry) {
				return entry;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	public ResponseEntity<ApiResponse> getClassListOfSchool() {
		List<String> result = schoolStreamDtlDao.getClassListOfSchool();
		if (result.size() > 0) {
			ApiResponse apiResponse = new ApiResponse(200, "Stream Fetched", "Stream Fetched Successfully", result); // Added
			return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
		} else {
			ApiResponse apiResponse = new ApiResponse(400, "Not Found", "Stream Not Fetched", result); // Added
			return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.NOT_FOUND);
		}
	}

	public ResponseEntity<ApiResponse> getSchoolByCustomSearch(SchoolSearchInputVo inputVo) {
		LOGGER.info("SchoolMstService:: getSchoolByCustomSearch:: Entering getSchoolByCustomSearch method:: ");
		ApiResponse apiResponse = null;
		List<SchoolMstVo> entry = null;
		String whereClause = "";
		String limitStr = "";
		String orderByStr = "";
		if (null != inputVo.getSort() && !inputVo.getSort().trim().isEmpty()) {
			orderByStr = " order by School_Name " + inputVo.getSort().trim();
		} else {
			orderByStr = " order by School_Name ASC";
		}
		LOGGER.info("orderByStr ------------ " + orderByStr);
		// limitStr = " limit 20 offset 0";
		if (null == inputVo.getPage() && null == inputVo.getSize()) {
			// do nothing
		} else {
			Integer size = (null != inputVo.getSize()) ? inputVo.getSize()
					: GlobalConstants.DEFAULT_DATA_SIZE_PAGINATION;
			Integer page = (null != inputVo.getPage() && inputVo.getPage() > 1) ? (inputVo.getPage() - 1) * size : 0;
			limitStr = " limit " + size + " offset " + page;
			LOGGER.info("limitStr..." + limitStr);
		}
		if (null != inputVo.getSchoolName() && !inputVo.getSchoolName().trim().isEmpty()) {
			whereClause += " and (School_Name like '%" + inputVo.getSchoolName().trim() + "%' or School_Address like '%"
					+ inputVo.getSchoolName().trim() + "%' "
					+ " or Address_Line_Two like '%" + inputVo.getSchoolName().trim() + "%' or Land_Mark like '%"
					+ inputVo.getSchoolName().trim() + "%' "
					+ " or City like '%" + inputVo.getSchoolName().trim() + "%' or Postal_Code like '%"
					+ inputVo.getSchoolName().trim() + "%' )";
		}

		if (null != inputVo.getBoard() && inputVo.getBoard().size() > 0) {
			List<String> wh = new ArrayList<>();
			for (String board : inputVo.getBoard()) {
				wh.add(" School_Board like '%" + board.trim() + "%' ");
			}
			whereClause += " and (" + String.join(" or ", wh) + ")";
		}

		if (null != inputVo.getMedium() && inputVo.getMedium().size() > 0) {
			List<String> wh = new ArrayList<>();
			for (String medium : inputVo.getMedium()) {
				wh.add(" School_Medium like '%" + medium.trim() + "%' ");
			}
			whereClause += " and (" + String.join(" or ", wh) + ")";
		}

		if (null != inputVo.getState() && inputVo.getState().size() > 0) {
			List<String> wh = new ArrayList<>();
			for (Long state : inputVo.getState()) {
				wh.add(" State = " + state);
			}
			whereClause += " and (" + String.join(" or ", wh) + ")";
		}

		if (null != inputVo.getDistrict() && inputVo.getDistrict().size() > 0) {
			List<String> wh = new ArrayList<>();
			for (Long district : inputVo.getDistrict()) {
				wh.add(" District = " + district);
			}
			whereClause += " and (" + String.join(" or ", wh) + ")";
		}

		if (null != inputVo.getSchoolType() && inputVo.getSchoolType().size() > 0) {
			List<String> wh = new ArrayList<>();
			for (String schoolType : inputVo.getSchoolType()) {
				wh.add(" School_Type = '" + schoolType + "'");
			}
			whereClause += " and (" + String.join(" or ", wh) + ")";
		}

		LOGGER.info("whereClause ------------- " + whereClause);
		entry = schoolMstDao.customSearchSchoolByinput(whereClause, orderByStr, limitStr);

		for (SchoolMstVo vo : entry) {
			vo = getSchoolInfo(vo);
		}

		// To fetch total no of data which satisfy this where clause
		whereClause += " and School_Status = 'Active' ";
		int totalNo = schoolMstDao.getTotalCountByInput(whereClause, "SCHOOL_MST").intValue();
		apiResponse = new ApiResponse(200, "success", "success", entry, totalNo);
		LOGGER.info("SchoolMstService:: getSchoolBySearch:: Exiting getSchoolBySearch method:: ");
		return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
	}

	public ResponseEntity<ApiResponse> getUsersByCustomSearch(UserSearchInputVo inputVo) {
		LOGGER.info("SchoolMstService:: getUserByCustomSearch:: Entering getUserByCustomSearch method:: ");

		ApiResponse apiResponse = null;
		List<UserVo> entry = null;
		String whereClause = " where 1";
		String limitStr = "";
		String orderByStr = "";
		if (null != inputVo.getOrderByColumn() && !inputVo.getOrderByColumn().trim().isEmpty()) {
			orderByStr = " order by " + inputVo.getOrderByColumn().trim() + " " + inputVo.getSort();
		} else {
			orderByStr = " order by first_name ASC";
		}
		LOGGER.info("orderByStr ------------ " + orderByStr);

		// limitStr = " limit 20 offset 0";
		if (null == inputVo.getPage() && null == inputVo.getSize()) {
			// do nothing
		} else {
			Integer size = (null != inputVo.getSize()) ? inputVo.getSize()
					: GlobalConstants.DEFAULT_DATA_SIZE_PAGINATION;
			Integer page = (null != inputVo.getPage() && inputVo.getPage() > 1) ? (inputVo.getPage() - 1) * size : 0;
			limitStr = " limit " + size + " offset " + page;
			LOGGER.info("limitStr..." + limitStr);
		}

		if (null != inputVo.getUserName() && !inputVo.getUserName().trim().isEmpty()) {
			whereClause += " and (first_name like '%" + inputVo.getUserName().trim() + "%' or last_name like '%"
					+ inputVo.getUserName().trim() + "%') ";
		}

		if (null != inputVo.getUserEmail() && !inputVo.getUserEmail().trim().isEmpty()) {
			whereClause += " and email like '%" + inputVo.getUserEmail().trim() + "%' "; // IN OPERATOR
		}

		if (null != inputVo.getUserPhone() && !inputVo.getUserPhone().trim().isEmpty()) {
			whereClause += " and phone like '%" + inputVo.getUserPhone().trim() + "%' "; // IN OPERATOR
		}

		if (null != inputVo.getUserType() && !inputVo.getUserType().trim().isEmpty()) {
			whereClause += " and user_type like '%" + inputVo.getUserType().trim() + "%' "; // IN OPERATOR
		}
		LOGGER.info("whereClause ------------- " + whereClause);

		entry = userDao.customSearchUserByinput(whereClause, orderByStr, limitStr);

		// for(UserVo vo : entry){
		// vo = getSchoolInfo(vo);
		// }

		// To fetch total no of data which satisfy this where clause

		int totalNo = userDao.getTotalActiveUsersCountByInput(whereClause, "USER_DETAILS").intValue();
		apiResponse = new ApiResponse(200, "success", "success", entry, totalNo);
		LOGGER.info("SchoolMstService:: getUserBySearch:: Exiting getUserBySearch method:: ");
		return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
	}

	public UserVo getUserByUserId(long id) throws Exception {
		LOGGER.info("SchoolUserService:: getUserByUserId:: " + "Entering getUserByUserId method:: ID:: " + id);

		UserVo entry = null;

		entry = userDao.findByUserId(id);

		if (entry == null) {
			throw new Exception("Entry with id " + id + " not found");
		}
		return entry;
	}
	
	public class SchoolStatusThreadClass extends Thread {
    	
    	List<Long> idListLoc = new ArrayList<>();
    	String statusNameLoc = "";
    	String adminEmailLoc = "";

	    public SchoolStatusThreadClass(List<Long> idList, String statusName, String adminEmail) {
			super();
			this.idListLoc = idList;
			this.statusNameLoc = statusName;
			this.adminEmailLoc = adminEmail;
		}



		public void run(){
	    	LOGGER.info("SchoolStatusThreadClass running");	    	
	    	
	    	List<String> emailList = schoolMstDao.getEmailListById(idListLoc);
	    	
	    	if(emailList.size() > 0) {
	    		String subjectEmail = "School status has been changed";
		    	String bodyEmail = "Your school is " + statusNameLoc + " now.";
		    	//Send email to the contact person of the school. 
		    	for(String emailStr: emailList) {
		    		// Send Mail
					// mailService.sendMail(emailStr, subjectEmail, bodyEmail, null);
					LOGGER.info("SchoolStatusThreadClass running: mail sent to: "+emailStr);
		    	}
		    	
		    	
//		    	Send one to mail to admin notifying 'All mail sent to school users.'
		    	// String bodyEmailAdmin = "Hello Admin<br><br> Thread has completed and mail sent to school users.";
		    	// mailService.sendMail(adminEmailLoc, subjectEmail, bodyEmailAdmin, null);
		    	LOGGER.info("SchoolStatusThreadClass running: mail sent to Admin: "+adminEmailLoc);
	    	}

	    }
	}

	public ResponseEntity<ApiResponse> findBySlug(String slug) {
		ApiResponse apiResponse = null;
		try {
			SchoolMstVo entry = schoolMstDao.findBySchoolNameSlug(slug);
			LOGGER.info("SchoolMstVo found --------------- " + slug);
			if (entry == null) {
				throw new Exception("Entry with slug " + slug + " not found");
			}
			entry = getSchoolInfo(entry);
			if (null != entry.getProfileStep()) {
				Set<String> setSteps = Stream.of(entry.getProfileStep().trim().split("\\s*,\\s*"))
						.collect(Collectors.toSet());
				entry.setProfileStepSet(setSteps);
			}

			apiResponse = new ApiResponse(200, "school_found", "school_found", entry);
			return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);

		} catch (Exception e) {
			e.printStackTrace();
			apiResponse = new ApiResponse(406, "school_not_found", "school_not_found", null); 
			return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.NOT_ACCEPTABLE);
		}
	}

	public ResponseEntity<ApiResponse> saveRating(SchoolMstVo schoolMstVo, Long userId) {
		ApiResponse apiResponse = null;
		if(null != userId) {
			try {
				SchoolMstVo entry = schoolMstDao.findById(schoolMstVo.getId()).get();
				entry.setSchoolRating(schoolMstVo.getSchoolRating());
				LOGGER.info("SchoolMstVo rating --------------- " + schoolMstVo.getSchoolRating());
				if (entry != null) {
					schoolMstDao.save(entry);
				}
				apiResponse = new ApiResponse(200, "school_rating_saved", "successful", entry);
				return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);

			} catch (Exception e) {
				e.printStackTrace();
				apiResponse = new ApiResponse(406, "school_not_found", "school_not_found", null); 
				return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.NOT_ACCEPTABLE);
			}
		}else {
			apiResponse = new ApiResponse(401, "Un-Authorized", "User Not Found", null); 
			return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.UNAUTHORIZED);
		
		}
	}
	
	public ResponseEntity<ApiResponse> saveFavorite(SchoolMstVo schoolMstVo) {
		ApiResponse apiResponse = null;
		if(null != schoolMstVo.getSchoolUserId()) {
			try {
				SchoolMstVo entry = schoolMstDao.findById(schoolMstVo.getId()).get();
				
				Set<String> strSet = schoolMstVo.getFavUsersSet(); 
				
				Arrays.asList(strSet);
				String stringSteps = String.join(",", strSet);
				schoolMstDao.updateFavoriteUsers(entry.getId(), stringSteps);
				
				apiResponse = new ApiResponse(200, "fav_user_saved", "successful", entry);
				return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);

			} catch (Exception e) {
				e.printStackTrace();
				apiResponse = new ApiResponse(406, "fav_user_not_found", "fav_user_not_found", null); 
				return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.NOT_ACCEPTABLE);
			}
		}else {
			apiResponse = new ApiResponse(401, "Un-Authorized", "User Not Found", null); 
			return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.UNAUTHORIZED);
		
		}
	}

	public ResponseEntity<ApiResponse> getAllPublishedNotice() {
		ApiResponse apiResponse = null;
			try {
				List<SchoolAdmissionDtlVo> entry = schoolAdmissionDtlDao.findAllPublishNotice();
				List<SchoolAdmissionDtlVo> entryResult = new ArrayList<>();
				for(SchoolAdmissionDtlVo tempRes: entry) {
					String str = schoolMstDao.findSchoolFromNotice(tempRes.getId());
					
					if(!str.isEmpty()) {
						tempRes.setSchoolName(str.split(",")[0]);
						tempRes.setSchoolSlugName(str.split(",")[1]);
					}
					entryResult.add(tempRes);
				}
				apiResponse = new ApiResponse(200, "Notice Fetched", "successful", entryResult);
				return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);

			} catch (Exception e) {
				e.printStackTrace();
				apiResponse = new ApiResponse(406, "Notice_not_found", "Notice_not_found", null); 
				return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.NOT_ACCEPTABLE);
			}
	}

	public ResponseEntity<ApiResponse> getPublishedNoticeDtl(Long noticeId) {
		ApiResponse apiResponse = null;
		try {
			SchoolAdmissionDtlVo entry = schoolAdmissionDtlDao.findById(noticeId).get();
			AttachmentVo doc = attachmentDao.findByTxIdAndFormCodeSingle(noticeId, "notice_media");
			entry.setDocument(doc);
			String str = schoolMstDao.findSchoolFromNotice(entry.getId());
			
			if(!str.isEmpty()) {
				entry.setSchoolName(str.split(",")[0]);
				entry.setSchoolSlugName(str.split(",")[1]);
			}
			
			apiResponse = new ApiResponse(200, "Notice Dtl Fetched", "successful", entry);
			return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);

		} catch (Exception e) {
			e.printStackTrace();
			apiResponse = new ApiResponse(406, "Notice_not_found", "Notice_not_found", null); 
			return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.NOT_ACCEPTABLE);
		}
	}

	public ResponseEntity<ApiResponse> findFavourieSchools(String userId) {
		ApiResponse apiResponse = null;
		try {
			
			List<String> resultSchools = schoolMstDao.findFavourieSchools(userId);
			List<CodeValuesVo> codeValues = new ArrayList<>();
			for (String strSchool : resultSchools) {
				CodeValuesVo tempCodeValue = new CodeValuesVo();
				String[] splitStrSchool = strSchool.split(",");
				tempCodeValue.setCode(splitStrSchool[0]);
				tempCodeValue.setValueOne(splitStrSchool[1]);
				
				List<AttachmentVo> docList = attachmentDao.findByTxId(Long.parseLong(splitStrSchool[0]));

				for (AttachmentVo doc : docList) {
					if (doc.getDocType().equals("school_banner")) {
						tempCodeValue.setValueTwo(doc.getFilePath());
					}
				}
				
				codeValues.add(tempCodeValue);
			}
			
			apiResponse = new ApiResponse(200, "Favourite School Fetched", "successful", codeValues);
			return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);

		} catch (Exception e) {
			e.printStackTrace();
			apiResponse = new ApiResponse(406, "Favourite_School_not_found", "Favourite_School_not_found", null); 
			return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.NOT_ACCEPTABLE);
		}
	}

	public ResponseEntity<ApiResponse> removeFavourieSchools(String schoolId, String userId) {
		ApiResponse apiResponse = null;
		try {
			
			String favSChoolStr = schoolMstDao.findFavourieSchools(schoolId, userId);
			List<String> favSChoolStrArr = null;
			if(null != favSChoolStr) {
				favSChoolStrArr = Arrays.asList(favSChoolStr.split(","));
				List<String> resultStr  = favSChoolStrArr.stream()
					    .filter(p -> !p.equals(userId)).collect(Collectors.toList());
				LOGGER.info("resultStr.toString()--------------- " + resultStr.toString());
				schoolMstDao.updateFavourieSchools(resultStr.stream().collect(Collectors.joining(",")), Long.parseLong(schoolId));
			}
			
			apiResponse = new ApiResponse(200, "Favourite School Removed", "successful", null);
			return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);

		} catch (Exception e) {
			e.printStackTrace();
			apiResponse = new ApiResponse(406, "Favourite_School_not_removed", "Favourite_School_not_removed", null); 
			return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.NOT_ACCEPTABLE);
		}
	}

    public ResponseEntity<ApiResponse> getAcademicDtlBYId(long parseLong) {
		ResponseEntity<ApiResponse> apiResponse = null;

		try {
			SchoolAcademicDtlVo vo=schoolAcademicDtlDao.findById(parseLong).orElseThrow(()->new ResourceNotFound(String.format("School academic details not found with id %s", parseLong)));
			apiResponse =new ResponseEntity<ApiResponse>(new ApiResponse(200, "FOUND", "School Academic details found.", vo), HttpStatus.OK);

		} catch (Exception e) {
				e.printStackTrace();
			apiResponse = new ResponseEntity<ApiResponse>(new ApiResponse(500, "Not Found", "INTERNAL SERVER ERROR", 0) , HttpStatus.INTERNAL_SERVER_ERROR);
		}
        return apiResponse;
    }

	public ResponseEntity<ApiResponse> updateSchoolAcademicDtl(SchoolMstVo schoolMstVo) {
		LOGGER.info("SchoolMstService:: updateSchoolAcademicDtl:: Entering :: ");
        ResponseEntity<ApiResponse> response=null;
		try {
			SchoolMstVo school=schoolMstDao.findById(schoolMstVo.getId()).orElseThrow(()->new ResourceNotFound(String.format("School not found with id: %s", schoolMstVo.getId())));
			List<SchoolAcademicDtlVo> saved = schoolAcademicDtlDao.saveAll(schoolMstVo.getSchoolAcademicDtlVo());
			updateProfileSteps(schoolMstVo.getId(), schoolMstVo.getProfileStepSet());
			LOGGER.info("saveSchoolAcademicDtl updated : " + saved);
			response= new ResponseEntity<ApiResponse>(new ApiResponse(200, "Success", "School AcademicDtl has been updated.", null),HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			response = new ResponseEntity<ApiResponse>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		LOGGER.info("SchoolMstService:: updateSchoolAcademicDtl:: Exiting :: ");

		return response;

	}
}
