package com.dcc.schoolmonk.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.dcc.schoolmonk.SchoolmonkApp;
import com.dcc.schoolmonk.common.ApiResponse;
import com.dcc.schoolmonk.common.CommmonFunction;
import com.dcc.schoolmonk.common.GlobalConstants;
import com.dcc.schoolmonk.common.UniqueIDGenerator;
import com.dcc.schoolmonk.dao.ClassMstDao;
import com.dcc.schoolmonk.dao.CountriesDao;
import com.dcc.schoolmonk.dao.DistrictMstDao;
import com.dcc.schoolmonk.dao.DropdownMasterDao;
import com.dcc.schoolmonk.dao.FaqDao;
import com.dcc.schoolmonk.dao.FeedBackDao;
import com.dcc.schoolmonk.dao.HelpDeskDao;
import com.dcc.schoolmonk.dao.InfrastructureMstDao;
import com.dcc.schoolmonk.dao.SchoolAddDao;
import com.dcc.schoolmonk.dao.SchoolLevelMstDao;
import com.dcc.schoolmonk.dao.SchoolMstDao;
import com.dcc.schoolmonk.dao.StateDao;
import com.dcc.schoolmonk.dao.StateMstDao;
import com.dcc.schoolmonk.dao.UserDao;
import com.dcc.schoolmonk.exception.ResourceNotFound;
import com.dcc.schoolmonk.vo.ClassMstVo;
import com.dcc.schoolmonk.vo.CountriesVo;
import com.dcc.schoolmonk.vo.DistrictMstVo;
import com.dcc.schoolmonk.vo.DropdownMasterVo;
import com.dcc.schoolmonk.vo.FaqMstVo;
import com.dcc.schoolmonk.vo.FeedbackVo;
import com.dcc.schoolmonk.vo.InfrastructureMstVo;
import com.dcc.schoolmonk.vo.IssueStateVo;
import com.dcc.schoolmonk.vo.NewsArticlesVo;
import com.dcc.schoolmonk.vo.SchoolAddVo;
import com.dcc.schoolmonk.vo.SchoolLevelMstVo;
import com.dcc.schoolmonk.vo.SchoolMstVo;
import com.dcc.schoolmonk.vo.StateMstVo;
import com.dcc.schoolmonk.vo.StatesVo;
import com.dcc.schoolmonk.vo.UserTypeVo;
import com.dcc.schoolmonk.vo.UserVo;

@Service
public class CommonMasterService {

	private static final Logger LOGGER = Logger.getLogger(CommonMasterService.class);

	@Autowired
	UserDao userDao;

	@Autowired
	DistrictMstDao districtMstDao;

	@Autowired
	StateMstDao stateMstDao;

	@Autowired
	InfrastructureMstDao infrastructureMstDao;

	@Autowired
	ClassMstDao classMstDao;

	@Autowired
	DropdownMasterDao dropdownMasterDao;

	@Autowired
	SchoolLevelMstDao schoolLevelMstDao;
	
	@Autowired
	HelpDeskDao helpDeskDao;
	
	@Autowired
	StateDao stateDao;
	
	@Autowired
	CountriesDao countriesDao;
	
	@Autowired
	SchoolAddDao schoolAddDao;
	
	@Autowired
	FeedBackDao feedBackDao;
	
	@Autowired
	FaqDao faqDao;

	@Autowired
	SchoolMstDao schoolMstDao;

	@Autowired
	MailService mailService;

	@Autowired
	UniqueIDGenerator uniqueIDGenerator;

	public List<StateMstVo> getStateList() {
		LOGGER.info("CommonMasterService::getStateList::Entering.");
		return stateMstDao.findByOrderByStateName();
	}

	public List<DistrictMstVo> getDistrictListByState(String stateName) {
		LOGGER.info("CommonMasterService :: getDistrictListByState :: Entering stateId : " + stateName);
		return districtMstDao.findByStateName(stateName);
	}

	public List<InfrastructureMstVo> saveInfra(List<InfrastructureMstVo> voList) throws Exception {
		LOGGER.info("CommonMasterService:: saveInfra:: Entering :: ");

		List<InfrastructureMstVo> saved = null;
		try {
			saved = (List<InfrastructureMstVo>) infrastructureMstDao.saveAll(voList);
			LOGGER.info("saveInfra updated : " + saved);
		} catch (Exception e) {
			e.printStackTrace();
		}
		LOGGER.info("CommonMasterService:: saveInfra:: Exiting :: ");

		return saved;
	}

	public List<InfrastructureMstVo> getInfraList() {
		LOGGER.info("CommonMasterService::getInfraList::Entering.");
		return infrastructureMstDao.findByOrderByInfraName();
	}

	public List<ClassMstVo> saveClass(List<ClassMstVo> voList) throws Exception {
		LOGGER.info("CommonMasterService:: saveClass:: Entering :: ");

		List<ClassMstVo> saved = null;
		try {
			saved = (List<ClassMstVo>) classMstDao.saveAll(voList);
			LOGGER.info("savesaveClassInfra updated : " + saved);
		} catch (Exception e) {
			e.printStackTrace();
		}
		LOGGER.info("CommonMasterService:: saveClass:: Exiting :: ");

		return saved;
	}

	public List<ClassMstVo> getClassList() {
		LOGGER.info("CommonMasterService::getClassList::Entering.");
		return classMstDao.findByOrderBySlNo();
	}

	public List<ClassMstVo> getClassFromList() {
		LOGGER.info("CommonMasterService::getClassFromList::Entering.");
		return classMstDao.findByStartClassOrderBySlNo(true);
	}

	public List<ClassMstVo> getClassUptoList() {
		LOGGER.info("CommonMasterService::getClassUptoList::Entering.");
		return classMstDao.findByEndClassOrderBySlNo(true);
	}

	public List<String> getClassStreams(String className) {
		LOGGER.info("CommonMasterService::getClassStreams::Entering.");
		return classMstDao.getStreams(className);
	}

	public List<String> getBoards() {
		LOGGER.info("CommonMasterService::getBoards::Entering.");
		return dropdownMasterDao.findListValue(1, "Board", 1);
	}

	public List<String> getMediums() {
		LOGGER.info("CommonMasterService::getBoards::Entering.");
		return dropdownMasterDao.findListValue(1, "Medium", 1);
	}

	public List<String> getSchoolTypes() {
		LOGGER.info("CommonMasterService::SchoolType::Entering.");
		return dropdownMasterDao.findListValue(1, "SchoolType", 1);
	}

	public List<String> getDDValuesByFieldName(String fieldName) {
		LOGGER.info("CommonMasterService::getDDValuesByFieldName::Entering : fieldName === " + fieldName);
		return dropdownMasterDao.findListValue(1, fieldName, 1);
	}

	public List<DropdownMasterVo> getBoardInSearch() {
		LOGGER.info("CommonMasterService::getBoardInSearch::Entering.");
		return dropdownMasterDao.findBoardList(1, "Board", 1);
	}

	public List<SchoolLevelMstVo> getSchoolLevels() {
		LOGGER.info("CommonMasterService::getgetSchoolLevelsClassList::Entering.");
		return schoolLevelMstDao.findByOrderBySlNo();
	}

	public List<DistrictMstVo> getDistrictListByStateMulti(List<Long> stateId) {
		LOGGER.info("CommonMasterService :: getDistrictListByStateMulti :: Entering stateId : " + stateId);
		return districtMstDao.findByStateMstVoIdInOrderByDistrictName(stateId);
	}

	public List<UserTypeVo> getActiveUsersTypes() {
		LOGGER.info("CommonMasterService :: getActiveUsersTypes :: entering getActiveUsersTypes method");

		return userDao.getTotalActiveUserTypes();
	}

	public List<IssueStateVo> getIssueCount() {
		LOGGER.info("CommonMasterService :: getIssueCount :: entering getIssueCount method");

		return helpDeskDao.getIssueCount();
	}

	@Cacheable(value = "getCountries")
	public List<CountriesVo> getAllCountries() {
		LOGGER.info("CommonMasterService::getAllCountries::Entering.");
		return  (List<CountriesVo>) countriesDao.findAll();
	}

	@Cacheable(value = "getStates", key = "#countryCode")
	public List<StatesVo> getStatesByCountries(UserVo userVo, String countryCode) {
		// TODO Auto-generated method stub
		
		List<StatesVo> entry = new ArrayList<>();
		try {
			entry = stateDao.findAllByCountryIdOrderByStateNameAsc(Long.parseLong(countryCode));
			LOGGER.info("Retrieving from Database size check: " + entry.size());
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return entry;
	}

	public ResponseEntity<ApiResponse> saveSchoolDtl(SchoolMstVo schoolMstVo) {
		LOGGER.info("CommonMasterService:: saveSchoolDtl:: Entering :: ");
		ResponseEntity<ApiResponse> responseEntity = null;
		ApiResponse apiResponse = null;
		SchoolMstVo saved = null;
		try {
			List<SchoolAddVo> school=schoolAddDao.findByschoolName(schoolMstVo.getSchoolName());
			if(school.isEmpty()){
			String uniqueID = uniqueIDGenerator.generateSchoolID();
			schoolMstVo.setSchoolId(uniqueID);
			schoolMstVo.setSchoolNameSlug(CommmonFunction.slugNameGeneration(schoolMstVo.getSchoolName()));	
			schoolMstVo.setSchoolStatus("In-Active");
			schoolMstVo.setCreationMode("Join-Request");
			schoolMstVo.setCreatedBy(0L);
			schoolMstVo.setState(37L);
			saved = schoolMstDao.save(schoolMstVo);

			String emailTo = SchoolmonkApp.ExtPropertyReader.emailToDcc;
			String subject = SchoolmonkApp.ExtPropertyReader.newSchoolAddSubject;
			String mainBody = SchoolmonkApp.ExtPropertyReader.newSchoolAddBody;

			mailService.sendMail(emailTo, subject, mainBody.toString(), null);
				
			apiResponse = new ApiResponse(200, "Success", "Data saved successfully", saved);
			responseEntity = new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
			// LOGGER.info("saveSchoolDtl updated : " + saved);
			}else{
				apiResponse = new ApiResponse(400, "ERROR", "School already exist in our website.");
				responseEntity = new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.BAD_REQUEST);
			}
			
		} catch(DataIntegrityViolationException e){
			apiResponse = new ApiResponse(400, "error", "School already exist in our website.", saved);
			responseEntity = new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.BAD_REQUEST);
		}catch (Exception e) {
			responseEntity = new ResponseEntity<ApiResponse>(new ApiResponse(500, "error", "INTERNAL SERVER ERROR.", saved),HttpStatus.INTERNAL_SERVER_ERROR);
			e.printStackTrace();
		}
		LOGGER.info("CommonMasterService:: saveSchoolDtl:: Exiting :: ");

		return responseEntity;
	}

	public FeedbackVo saveFeedback(FeedbackVo feedbackVo) {
		LOGGER.info("CommonMasterService:: saveFeedback:: Entering :: ");

		FeedbackVo saved = null;
		try {
			feedbackVo.setFeedbackStatus("Action Pending");
			saved = feedBackDao.save(feedbackVo);
			LOGGER.info("saveFeedback updated : " + saved);
		} catch (Exception e) {
			e.printStackTrace();
		}
		LOGGER.info("CommonMasterService:: saveFeedback:: Exiting :: ");

		return saved;
	}

	public ResponseEntity<ApiResponse> getFeedBack(FeedbackVo feedbackVo) {
		LOGGER.info("CommonMasterService:: getFeedBack:: Entering :: ");
		ApiResponse apiResponse = null;
		String whereClause = "";
		String limitStr = "";
		String orderByStr = "";
		
		if (null == feedbackVo.getPage() && null == feedbackVo.getSize()) {
			// do nothing
		} else {
			Long size = (null != feedbackVo.getSize()) ? feedbackVo.getSize() : GlobalConstants.DEFAULT_DATA_SIZE_PAGINATION;
			Long page = (null != feedbackVo.getPage() && feedbackVo.getPage() > 1) ? (feedbackVo.getPage() - 1) * size : 0;
			limitStr = " limit " + size + " offset " + page;
			LOGGER.info("limitStr..." + limitStr);
		}
		if (null != feedbackVo.getUserName() && !feedbackVo.getUserName().trim().isEmpty()) {
			LOGGER.info("feedbackVo.getUserName() ------------ " + feedbackVo.getUserName());
			whereClause += " and User_Name like '%" + feedbackVo.getUserName().trim() + "%'";
		}
		if (null != feedbackVo.getUserEmail() && !feedbackVo.getUserEmail().trim().isEmpty()) {
			LOGGER.info("feedbackVo.getUserEmail() ------------ " + feedbackVo.getUserEmail());
			whereClause += " and User_Email like '%" + feedbackVo.getUserEmail().trim() + "%'";
		}
		if (null != feedbackVo.getUserPhone() && !feedbackVo.getUserPhone().trim().isEmpty()) {
			LOGGER.info("feedbackVo.getUserPhone() ------------ " + feedbackVo.getUserPhone());
			whereClause += " and User_Phone like '%" + feedbackVo.getUserPhone().trim() + "%'";
		}
		if (null != feedbackVo.getUserFeedback() && !feedbackVo.getUserFeedback().trim().isEmpty()) {
			LOGGER.info("feedbackVo.getUserFeedback() ------------ " + feedbackVo.getUserFeedback());
			whereClause += " and User_Feedback like '%" + feedbackVo.getUserFeedback().trim() + "%'";
		}
		List<FeedbackVo>  saved = feedBackDao.findAll(whereClause, limitStr, orderByStr);

			LOGGER.info("saveSchoolDtl updated : " + saved);
			
			int totalNo = feedBackDao.getTotalCountByInput(whereClause, "FEEDBACK_MST").intValue();
			LOGGER.info("CommonMasterService:: getFeedBack:: Exiting getFeedBack method:: ");
			apiResponse = new ApiResponse(200, "success", "Feedback List fetched Successfully", saved, totalNo);
			LOGGER.info("NearMisssReportService::findAll::Exiting...");
			return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
	
	}

	public List<FaqMstVo> getFaqs() {
		LOGGER.info("CommonMasterService:: getFaqs:: Entering :: ");

		List<FaqMstVo>  saved = null;
		try {
			saved = (List<FaqMstVo>) faqDao.findAll();
			LOGGER.info("saveSchoolDtl updated : " + saved);
		} catch (Exception e) {
			e.printStackTrace();
		}
		LOGGER.info("CommonMasterService:: getFaqs:: Exiting :: ");

		return saved;
	}

	
	public ResponseEntity<ApiResponse> updateFeedbackStatus(FeedbackVo vo, UserVo userVo) {
		LOGGER.info("CommonMasterService:: updateFeedbackStatus:: Entering :: ");
		ApiResponse apiResponse = null;
		if(userVo != null) {
			try {
				feedBackDao.updateFeedbackStatus(vo.getIdLists(), vo.getFeedbackStatus());
				
				LOGGER.info("CommonMasterService:: updateFeedbackStatus:: Exiting :: ");
				apiResponse = new ApiResponse(200, "success", "Updated successfully", null, 1);
				return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
			} catch (Exception e) {
				e.printStackTrace();
				apiResponse = new ApiResponse(400, "error", null, 0);
				return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.BAD_REQUEST);
			}
		}else {
			apiResponse = new ApiResponse(401, "Un-Authorized", null, 0);
			return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.UNAUTHORIZED);
		}
	}

	public ResponseEntity<ApiResponse> getallfaqs() {
		List<FaqMstVo> listofFaqMstVos=faqDao.findAll();
		ResponseEntity<ApiResponse> responseEntity = null;
		try {
		       responseEntity = new ResponseEntity<ApiResponse>(
	                    new ApiResponse(200, "success", "FAQs Fetch Successful.",listofFaqMstVos),
	                    HttpStatus.OK);
	        } catch (Exception e) {
	            e.printStackTrace();
	            responseEntity = new ResponseEntity<ApiResponse>(new ApiResponse(500, "error", "INTERNAL SERVER ERROR"),
	                    HttpStatus.INTERNAL_SERVER_ERROR);
	            
	        }
			
		return responseEntity ;
	}

	public ResponseEntity<ApiResponse> updateFaqsByid( FaqMstVo faqMstVo){
		 ResponseEntity<ApiResponse> responseEntity = null;
		  try {
			     FaqMstVo faq = faqDao.findById(faqMstVo.getId())
	                    .orElseThrow(() -> new ResourceNotFound(String.format("FAQs With Id Not Found"))); 
			     FaqMstVo faqs = faqDao.save(faqMstVo);
			   responseEntity=new ResponseEntity<ApiResponse> (new ApiResponse (200, "success","FAQs Updated success",faqs ),
					   HttpStatus.OK) ;
		 
		   } catch (Exception e) {
	            e.printStackTrace();
	            responseEntity = new ResponseEntity<ApiResponse>(new ApiResponse(500, "error", "INTERNAL SERVER ERROR"),
	                    HttpStatus.INTERNAL_SERVER_ERROR);
		 
		   }
		 
		return responseEntity;
	}

	public ResponseEntity<ApiResponse> deleteByid(long id) {
		 ResponseEntity<ApiResponse> responseEntity = null;
		  try {
			  faqDao.findById(id)
	                    .orElseThrow(() -> new ResourceNotFound(String.format("FAQs With Id %s Not Found", id)));
			  faqDao.deleteById(id);
	            responseEntity = new ResponseEntity<ApiResponse>(
	                    new ApiResponse(200, "success", "FAQs Deleted Success"),
	                    HttpStatus.OK);
	        } catch (Exception e) {
	            e.printStackTrace();
	            responseEntity = new ResponseEntity<ApiResponse>(new ApiResponse(500, "error", "INTERNAL SERVER ERROR"),
	                    HttpStatus.INTERNAL_SERVER_ERROR);
	        }
		 return responseEntity;
	}

	public ResponseEntity<ApiResponse> fetchallCategory() {
		 ResponseEntity<ApiResponse> responseEntity = null;
		 try {
            List<String> BlogCategory=dropdownMasterDao.findListValue(1, "Blog", 1);

	     responseEntity = new ResponseEntity<ApiResponse>(
	                    new ApiResponse(200, "success", "FAQs Deleted Success",BlogCategory),
	                    HttpStatus.OK);		
		 } catch (Exception e) {
		e.printStackTrace();
	    responseEntity = new ResponseEntity<ApiResponse>(new ApiResponse(500, "error", "INTERNAL SERVER ERROR"),
	                    HttpStatus.INTERNAL_SERVER_ERROR);
		 }

		return responseEntity;
	}

	
	
}
	


