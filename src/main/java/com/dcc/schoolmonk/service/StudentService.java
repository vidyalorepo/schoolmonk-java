package com.dcc.schoolmonk.service;

import com.dcc.schoolmonk.common.ApiResponse;
import com.dcc.schoolmonk.common.UniqueIDGenerator;
import com.dcc.schoolmonk.dao.AddressDao;
import com.dcc.schoolmonk.dao.AttachmentDao;
import com.dcc.schoolmonk.dao.DistrictMstDao;
import com.dcc.schoolmonk.dao.SchoolAdmissionDtlDao;
import com.dcc.schoolmonk.dao.SchoolMstDao;
import com.dcc.schoolmonk.dao.SchoolStudentEligibilityDtlDao;
import com.dcc.schoolmonk.dao.SchoolStudentMappingDao;
import com.dcc.schoolmonk.dao.StateMstDao;
import com.dcc.schoolmonk.dao.StudentDao;
import com.dcc.schoolmonk.dao.SudentSiblingDao;
import com.dcc.schoolmonk.dao.UserDao;
import com.dcc.schoolmonk.vo.AddressVo;
import com.dcc.schoolmonk.vo.AttachmentVo;
import com.dcc.schoolmonk.vo.CodeValuesVo;
import com.dcc.schoolmonk.vo.SchoolAdmissionDtlVo;
import com.dcc.schoolmonk.vo.SchoolMstVo;
import com.dcc.schoolmonk.vo.SchoolStudentMappingVo;
import com.dcc.schoolmonk.vo.StudentMstVo;
import com.dcc.schoolmonk.vo.UserVo;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class StudentService {

	private static final Logger LOGGER = Logger.getLogger(StudentService.class);

	@Autowired
	StudentDao studentDao;

	@Autowired
	UserDao userDao;

	@Autowired
	SchoolStudentMappingDao schoolStudentMappingDao;

	@Autowired
	SchoolMstDao schoolMstDao;

	@Autowired
	SudentSiblingDao sudentSiblingDao;

	@Autowired
	AttachmentDao attachmentDao;

	@Autowired
	SchoolAdmissionDtlDao schoolAdmissionDtlDao;

	@Autowired
	UniqueIDGenerator gen;

	@Autowired
	SchoolStudentEligibilityDtlDao schoolStudentEligibilityDtlDao;

	@Autowired
	SchoolUserService schoolUserService;

	@Autowired
	DistrictMstDao districtMstDao;

	@Autowired
	StateMstDao stateMstDao;

	@Autowired
	MailService mailService;

	@Autowired
	AddressDao addressDao;



	public List<StudentMstVo> getAllEntries() {
		// TODO Auto-generated method stub
		return null;
	}

	public ResponseEntity<ApiResponse> saveStudent(StudentMstVo studentMstVo, Long userId) {
		ApiResponse apiResponse = null;
		UserVo user = new UserVo();
		studentMstVo.setCreatedBy(userId);

		Long tempStudId = studentMstVo.getId();

		try {
			// LOGGER.info("conditions --------------- " + studentMstVo.getSchoolId() +
			// studentMstVo.getWizardFormNo() + studentMstVo.getWizardFormNo().equals("1") +
			// studentMstVo.getId());

			// StudentMstVo studentSaved = null;

			StudentMstVo studentSaved = studentDao.save(studentMstVo);
			// LOGGER.info("conditions --------------- " + studentMstVo.getSchoolId() +
			// studentMstVo.getWizardFormNo()
			// + studentMstVo.getWizardFormNo().equals("1") + studentMstVo.getId());
			// This will occur only for apply wizard form one
			SchoolStudentMappingVo vo = new SchoolStudentMappingVo();
			if (null != studentMstVo.getSchoolId()
					&& null != studentMstVo.getWizardFormNo() && studentMstVo.getWizardFormNo().equals("1")
					&& null == tempStudId) {
//				SchoolStudentMappingVo vo = new SchoolStudentMappingVo();
				// LOGGER.info("print vo --------------- " + vo);
				UUID uuid = UUID.randomUUID();
				vo.setStudentMstVo(studentSaved);

				vo.setRegistrationToken(uuid.toString());
				vo.setAdmissionStatus("Applied");
				vo.setCreatedBy(userId);
				vo.setUpdatedBy(userId);

				SchoolMstVo refScl = new SchoolMstVo();
				refScl.setId(studentMstVo.getSchoolId());
				vo.setSchoolMstVo(refScl);
				vo.setAdmissionForClass(studentMstVo.getAdmissionForClass());
				vo.setAdmissionForBoard(studentMstVo.getAdmissionForBoard());
				vo.setAcademicYear(studentMstVo.getAcademicYear());
				vo.setAdmissionEndDate(studentMstVo.getAdmissionEndDate());

				vo = schoolStudentMappingDao.save(vo);
				LOGGER.info("applyInitiate saved --------------- " + vo);
			}
			LOGGER.info("StudentMstVo saved --------------- " + studentSaved);

			apiResponse = new ApiResponse(200, "success", "Student Saved Successfully", studentSaved, vo, 1); // Added
			return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			apiResponse = new ApiResponse(406, "registration not initialized", "registration init un-successful", user); // Added
			return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.NOT_ACCEPTABLE);
		}
	}

	// @Override
	public Page<StudentMstVo> findPaginated(int page, int size) {
		return studentDao.findAll(PageRequest.of(page, size));
	}

	public ResponseEntity<ApiResponse> applyInitiate(SchoolStudentMappingVo vo, Long userId) {
		ApiResponse apiResponse = null;
		try {
			// first check if eligible according to age
			/*
			 * if (!checkIfEligible(vo)) {
			 * apiResponse = new ApiResponse(406, "not_eligible",
			 * "Not eligible for this class due to age requirement .", null, 0); // Added
			 * return new ResponseEntity<ApiResponse>(apiResponse,
			 * HttpStatus.NOT_ACCEPTABLE);
			 * }
			 */

			// get the fees
			LOGGER.info(vo.getSchoolMstVo().getId() + "--" + vo.getAcademicYear() + "--" +
					vo.getAdmissionForBoard() + "--" + vo.getAdmissionForClass());
			String admissionDtl = schoolAdmissionDtlDao.getAdmissionFees(vo.getSchoolMstVo().getId(),
					vo.getAcademicYear(),
					vo.getAdmissionForClass());
			LOGGER.info("----------admission dtl----" + admissionDtl);
			String[] admissionDtlArr = admissionDtl.split(",");
			LOGGER.info(" -------admissionFees---------->" + admissionDtlArr);

      // First check it's new applicant(0) or not
      if (vo.getStudentMstVo().getId() != 0) {
        // check if already applied
        String whereClause =
            " and Student_Id = "
                + vo.getStudentMstVo().getId()
                + " and School_Id = "
                + vo.getSchoolMstVo().getId()
                + " "
                + "and Academic_Year = '"
                + vo.getAcademicYear()
                + "' and Admission_For_Class = '"
                + vo.getAdmissionForClass()
                + "'";
        LOGGER.info(" -------whereClause---------->" + whereClause);
        if (null != vo.getAdmissionForBoard() && !vo.getAdmissionForBoard().trim().isEmpty()) {
          whereClause += " and Admission_For_Board = '" + vo.getAdmissionForBoard() + "'";
        }
        if (null != vo.getAdmissionForStream() && !vo.getAdmissionForStream().trim().isEmpty()) {
          whereClause += " and Admission_For_stream = '" + vo.getAdmissionForStream() + "'";
        }
        String existingRegistrationToken =
            schoolStudentMappingDao.checkIfAlreadyApplied(whereClause);
        LOGGER.info("checkIfAlreadyApplied ===================== " + existingRegistrationToken);
        /*
         * if (null == existingRegistrationToken || existingRegistrationToken.isEmpty())
         * {
         * UUID uuid = UUID.randomUUID();
         * vo.setRegistrationToken(uuid.toString());
         * vo.setAdmissionStatus("Applied");
         * vo.setAdmissionEndDate(admissionDtlArr[1]);
         * vo.setCreatedBy(userId);
         * vo.setUpdatedBy(userId);
         * vo = schoolStudentMappingDao.save(vo);
         * LOGGER.info("applyInitiate saved --------------- " + vo);
         * vo.setStudentMstVo(studentDao.findById(vo.getStudentMstVo().getId()).orElse(
         * null));
         * vo.setSchoolMstVo(schoolMstDao.findById(vo.getSchoolMstVo().getId()).orElse(
         * null));
         * vo.setAdmissionFees(admissionDtlArr[0]);
         *
         * apiResponse = new ApiResponse(200, "success",
         * "Student application Initiated Successfully", vo, 1); // Added
         * } else {
         * vo.setRegistrationToken(existingRegistrationToken);
         * vo.setStudentMstVo(studentDao.findById(vo.getStudentMstVo().getId()).orElse(
         * null));
         * vo.setSchoolMstVo(schoolMstDao.findById(vo.getSchoolMstVo().getId()).orElse(
         * null));
         * vo.setAdmissionFees(admissionDtlArr[0]);
         * // vo.setAdmissionEndDate(admissionDtlArr[1]);
         * apiResponse = new ApiResponse(200, "already_applied",
         * "Student already applied to this school", vo, 1); // Added
         * }
         */

        // Newly added by kousik
        if (null != existingRegistrationToken) {
          vo.setRegistrationToken(existingRegistrationToken);
          vo.setStudentMstVo(studentDao.findById(vo.getStudentMstVo().getId()).orElse(null));
          vo.setSchoolMstVo(schoolMstDao.findById(vo.getSchoolMstVo().getId()).orElse(null));
          vo.setAdmissionFees(admissionDtlArr[0]);
          // vo.setAdmissionEndDate(admissionDtlArr[1]);
          apiResponse =
              new ApiResponse(
                  200, "already_applied", "Student already applied to this school", vo, 1); // Added
        } else {
          // Added for admissionEndDate
          // Need to check duplicate data for same applicant as new applicant

          SchoolStudentMappingVo vo1 = new SchoolStudentMappingVo();
          if (null != vo.getSchoolMstVo().getId() && null != vo.getStudentMstVo().getId()) {
            StudentMstVo studentSaved = this.getEntryById(vo.getStudentMstVo().getId());
            // LOGGER.info("print vo --------------- " + vo);
            UUID uuid = UUID.randomUUID();
            vo1.setStudentMstVo(studentSaved);

            vo1.setRegistrationToken(uuid.toString());
            vo1.setAdmissionStatus("Applied");
            vo1.setCreatedBy(userId);
            vo1.setUpdatedBy(userId);

            SchoolMstVo refScl = new SchoolMstVo();
            refScl.setId(vo.getSchoolMstVo().getId());
            vo1.setSchoolMstVo(refScl);
            vo1.setAdmissionForClass(vo.getAdmissionForClass());
            vo1.setAdmissionForBoard(vo.getAdmissionForBoard());
            vo1.setAcademicYear(vo.getAcademicYear());
            vo1.setAdmissionEndDate(vo.getAdmissionEndDate());

						vo1.setAdmissionEndDate(admissionDtlArr[1]);
						vo1 = schoolStudentMappingDao.save(vo1);
						LOGGER.info("applyInitiate saved --------------- " + vo);
					}

					apiResponse = new ApiResponse(200, "success", "Student application Initiated Successfully", vo1, 1);
				}
			} else {
				// Added for admissionEndDate
				// Need to check duplicate data for same applicant as new applicant
				vo.setAdmissionEndDate(admissionDtlArr[1]);
				apiResponse = new ApiResponse(200, "success", "Student application Initiated Successfully", vo, 1);
			}

			return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			apiResponse = new ApiResponse(406, "error", "Student apply init un-successful", null, 0); // Added
			return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.NOT_ACCEPTABLE);
		}
	}

  public StudentMstVo getEntryById(Long studentId) throws Exception {

    LOGGER.info(
        "StudentService:: getEntryById:: " + "Entering getEntry method:: ID:: " + studentId);

    StudentMstVo entry = studentDao.findById(studentId).get();

    if (entry == null) {
      throw new Exception("Entry with id " + studentId + " not found");
    }
    entry = getStudentInfo(entry);

    return entry;
  }

	private StudentMstVo getStudentInfo(StudentMstVo entry) {
		// also get attachment files
		List<AttachmentVo> docList = attachmentDao.findByTxId(entry.getId());
		entry.setDocList(docList);

		for (AttachmentVo doc : docList) {
			if (doc.getDocType().equals("student_photo")) {
				entry.setStudentPhotoPath(doc.getFilePath());
			}
			if (doc.getDocType().equals("guardian_photo")) {
				entry.setGuardianPhotoPath(doc.getFilePath());
      }
      if (doc.getDocType().equals("birth_certificate")) {
        entry.setBirthCertificatePath(doc.getFilePath());
      }
      if (doc.getDocType().equals("last_marksheet")) {
        entry.setLastMarksheetPath(doc.getFilePath());
      }
    }

    List<AddressVo> addresses = entry.getAddressVo();
    // for (AddressVo vo : addresses) {
    // if (null != vo.getState() && !vo.getState().isEmpty()) {
    // vo.setStateName(stateMstDao.getName(Long.parseLong(vo.getState())));
    //
    // }
    // if (null != vo.getDistrict() && !vo.getDistrict().isEmpty()) {
    // vo.setDistrictName(districtMstDao.getName(Long.parseLong(vo.getDistrict())));
    // }
    // }
    entry.setAddressVo(addresses);
    return entry;
  }

  public ResponseEntity<ApiResponse> sortlistedByStudent(Long studentId) {
    ApiResponse apiResponse = null;
    try {
      List<SchoolStudentMappingVo> dataList =
          schoolStudentMappingDao.sortlistedByStudent(studentId);

      apiResponse =
          new ApiResponse(
              200, "success", "Student applied list", dataList, dataList.size()); // Added
      return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
    } catch (Exception e) {
      e.printStackTrace();
      apiResponse = new ApiResponse(406, "error", "error", null, 0); // Added
      return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.NOT_ACCEPTABLE);
    }
  }

  public void deleteSibling(Long id) {
    LOGGER.info("StudentService: deleteSibling: deleteSibling Entering");

    try {

      try {
        sudentSiblingDao.deleteById(id);
      } catch (Exception exp) {
        throw new Exception("Not such object found in DB");
      }

    } catch (Exception e) {
      e.printStackTrace();
      LOGGER.error("StudentService: deleteSibling :: Technical problem in deleting file");
    }

    LOGGER.info("StudentService: deleteSibling: File deleteSibling successfully========");
  }

  public ResponseEntity<ApiResponse> updateShortlisted(List<Long> ids, Long loginUserId) {
    ApiResponse apiResponse = null;
    try {
      schoolStudentMappingDao.updateShortlisted(ids, loginUserId, new Date());

      apiResponse =
          new ApiResponse(200, "success", "Status updated successfully", ids, ids.size()); // Added
      return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
    } catch (Exception e) {
      e.printStackTrace();
      apiResponse = new ApiResponse(406, "error", "error", null, 0); // Added
      return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.NOT_ACCEPTABLE);
    }
  }

	public boolean checkIfEligible(SchoolStudentMappingVo vo) {
		boolean ret = false;
		// check if already applied
		String whereClause = " and School_Id = " + vo.getSchoolMstVo().getId() + " "
				+ "and Academic_Year = '" + vo.getAcademicYear() + "' and Class_Range = '" + vo.getAdmissionForClass()
				+ "'";
		LOGGER.info(" -------whereClause---------->" + whereClause);
		// if (null != vo.getAdmissionForBoard() &&
		// !vo.getAdmissionForBoard().trim().isEmpty()) {
		// whereClause += " and board_name = '" + vo.getAdmissionForBoard() + "'";
		// }
		// if (null != vo.getAdmissionForStream() &&
		// !vo.getAdmissionForStream().trim().isEmpty()) {
		// whereClause += " and Class_Stream = '" + vo.getAdmissionForStream() + "'";
		// }
		// SchoolStudentEligibilityDtlVo eligibility =
		// schoolStudentEligibilityDtlDao.checkIfEligible(whereClause);
		SchoolAdmissionDtlVo eligibility = schoolAdmissionDtlDao.checkIfEligible(whereClause);
		if (null != eligibility) {
			Date dobStartdate = eligibility.getMinDOB();
			Date dobEnddate = eligibility.getMaxDOB();
			if (null != dobStartdate && null != dobEnddate) {
				String dobStr = vo.getStudentDOB();
				Date dob;
				try {
					dob = new SimpleDateFormat("yyyy-MM-dd").parse(dobStr);
					if (dob.after(dobStartdate) && dob.before(dobEnddate))
						ret = true;
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
		}
		return ret;
	}

	public SchoolStudentMappingVo getSchoolStudentMappingVoById(String token) throws Exception {
		SchoolStudentMappingVo entry = schoolStudentMappingDao.findByRegistrationToken(token);

		if (entry == null) {
			throw new Exception("Entry with id " + token + " not found");
		}
		entry.setSchoolMstVo(schoolUserService.getSchoolInfo(entry.getSchoolMstVo()));
		entry.getSchoolMstVo().setDocList(null);
		entry.setStudentMstVo(getStudentInfo(entry.getStudentMstVo()));
		return entry;
	}

	public ResponseEntity<ApiResponse> saveStreamSubject(SchoolStudentMappingVo schoolStudentMappingVo,
			Long loginUserId) {
		ApiResponse apiResponse = null;
		try {
			SchoolStudentMappingVo resultVo = null;
			SchoolStudentMappingVo entry = null;
			if (null != schoolStudentMappingVo.getId()) {
				entry = schoolStudentMappingDao.findById(schoolStudentMappingVo.getId()).get();
			} else {
				apiResponse = new ApiResponse(406, "Error", "Id must not be null", ""); // Added
				return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.NOT_ACCEPTABLE);
			}
			entry.setAdmissionForStream(schoolStudentMappingVo.getAdmissionForStream());
			entry.setSubjectOne(schoolStudentMappingVo.getSubjectOne());
			entry.setSubjectTwo(schoolStudentMappingVo.getSubjectTwo());
			entry.setSubjectThree(schoolStudentMappingVo.getSubjectThree());
			entry.setSubjectFour(schoolStudentMappingVo.getSubjectFour());
			entry.setSubjectFive(schoolStudentMappingVo.getSubjectFive());
			entry.setSubjectSix(schoolStudentMappingVo.getSubjectSix());
			entry.setUpdatedBy(loginUserId);
			entry.setUpdatedOn(new Date());

			resultVo = schoolStudentMappingDao.save(entry);

			LOGGER.info("SchoolStudentMappingVo saved --------------- " + resultVo);
			apiResponse = new ApiResponse(200, "success", "Stream and Subjects Saved Successfully", resultVo); // Added
			return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			apiResponse = new ApiResponse(406, "Error", "Stream and Subjects saved un-successful", ""); // Added
			return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.NOT_ACCEPTABLE);
		}
	}

	public ResponseEntity<ApiResponse> getStreamSubjectData(Long studentId, String admissionClass, Long schoolId) {
		ApiResponse apiResponse = null;
		try {
			SchoolStudentMappingVo resultVo = schoolStudentMappingDao
					.findByAdmissionForClassAndStudentMstVoIdAndSchoolMstVoId(admissionClass, studentId, schoolId);
			// resultVo.setSchoolMstVo();
			// resultVo.setStudentMstVo();
			apiResponse = new ApiResponse(200, "success", "Student Mapping Data Fetched", resultVo, 1);
			return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			apiResponse = new ApiResponse(406, "error", "error", null, 0); // Added
			return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.NOT_ACCEPTABLE);
		}
	}

	public ResponseEntity<ApiResponse> updateStudentStatus(SchoolStudentMappingVo vo, UserVo userVo) {
		ApiResponse apiResponse = null;
		try {
			List<Long> getValidIds = schoolStudentMappingDao.getValidIdsStatus(vo.getAdmissionStatus(), vo.getIds());
			List<Long> notMatchedIds = new ArrayList<>();
			List<String> applicantNames = null;
			if (vo.getIds().size() != getValidIds.size()) {
				notMatchedIds = vo.getIds().stream()
						.filter(element -> !getValidIds.contains(element))
						.collect(Collectors.toList());
				LOGGER.info("notMatchedIds --------------->> " + notMatchedIds);
				if (notMatchedIds.size() > 0) {
					applicantNames = schoolStudentMappingDao.getApplicantNamesByIds(notMatchedIds);
				}
			}

			if (getValidIds.size() > 0) {
				if (vo.getAdmissionStatus().equalsIgnoreCase("Shortlisted")) {
					schoolStudentMappingDao.updateShortlisted(getValidIds, userVo.getUserId(), new Date());
				} else {
					schoolStudentMappingDao.updateStatus(getValidIds, userVo.getUserId(), new Date(),
							vo.getAdmissionStatus());
				}

			} else {
				apiResponse = new ApiResponse(400, "error", "Can not find valid Ids", null, 0); // Added by kousik
				return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.NOT_FOUND);
			}

			// Thread class
			StudentApplicationStatusThreadClass threadClass = new StudentApplicationStatusThreadClass(getValidIds,
					vo.getAdmissionStatus(), userVo.getEmail());
			threadClass.start();

			apiResponse = new ApiResponse(200, "success", "Status updated successfully" +
					(null != applicantNames && applicantNames.size() > 0
							? " & The following records are not in valid status for status change: " + applicantNames
							: ""),
					vo.getIds(), vo.getIds().size()); // Added
			return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			apiResponse = new ApiResponse(406, "error", "error", null, 0); // Added
			return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.NOT_ACCEPTABLE);
		}
	}

	public void deleteAddress(Long id) {
		LOGGER.info("StudentService: deleteAddress: deleteAddress Entering");

		try {

			try {
				addressDao.deleteById(id);
			} catch (Exception exp) {
				throw new Exception("Not such object found in DB");
			}

		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("StudentService: deleteAddress :: Technical problem in deleting deleteAddress");
		}

		LOGGER.info("StudentService: deleteAddress: deleteAddress successfully========");

	}

	public ResponseEntity<ApiResponse> checkForApply(SchoolAdmissionDtlVo vo) {
		ApiResponse apiResponse = null;
		try {
			// first check if eligible according to age
			if (!checkIfEligibleForApply(vo, vo.getApplicantDOB())) {
				apiResponse = new ApiResponse(406, "not_eligible",
						"Not eligible for this class due to age requirement .", null, 0); // Added
				return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.NOT_ACCEPTABLE);
			} else {
				apiResponse = new ApiResponse(200, "success", "Student is Eligible", true, 1);
				return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
			}

		} catch (Exception e) {
			e.printStackTrace();
			apiResponse = new ApiResponse(406, "error", "Student apply init un-successful", null, 0); // Added
			return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.NOT_ACCEPTABLE);
		}
	}

	public boolean checkIfEligibleForApply(SchoolAdmissionDtlVo vo, String stdDob) {
		boolean ret = false;
		// check if already applied
		String whereClause = " and School_Id = " + vo.getSchoolMstVo().getId() + " "
				+ "and Academic_Year = '" + vo.getAcademicYear()
				+ "' and Board = '" + vo.getBoard()
				+ "' and Class_Range = '" + vo.getClassRange()
				+ "'";
		LOGGER.info("whereClause---------->" + whereClause);
		SchoolAdmissionDtlVo eligibility = schoolAdmissionDtlDao.checkIfEligible(whereClause);
		LOGGER.info(" eligibility---------->" + eligibility);
		if (null != eligibility) {
			Date dobStartdate = eligibility.getMinDOB();
			LOGGER.info("getMinDOB---------->" + eligibility.getMinDOB());
			Date dobEnddate = eligibility.getMaxDOB();
			LOGGER.info("getMaxDOB---------->" + eligibility.getMaxDOB());
			if (null != dobStartdate && null != dobEnddate) {
				String dobStr = stdDob;
				LOGGER.info("stdDob---------->" + stdDob);
				Date dob;
				try {
					dob = new SimpleDateFormat("yyyy-MM-dd").parse(dobStr);
					LOGGER.info("dob---------->" + dob);
					LOGGER.info("dob---------->" + dob.getClass().getName());
					if (dob.after(dobStartdate) && dob.before(dobEnddate))
						ret = true;
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
		}
		LOGGER.info(" ret---------->" + ret);
		return ret;
	}

	public ResponseEntity<ApiResponse> createStudent(StudentMstVo studentMstVo, Long userId) {
		ApiResponse apiResponse = null;
		SchoolStudentMappingVo mappingVo = new SchoolStudentMappingVo();
		UserVo user = new UserVo();
		user.setUserId(userId);

		try {
			studentMstVo.setCreatedBy(userId);
			studentMstVo.setUpdatedBy(userId);
			studentMstVo.setParentUser(user);
			StudentMstVo studentSaved = studentDao.save(studentMstVo);

			LOGGER.info("StudentMstVo saved --------------- " + studentSaved);

			if (null != studentMstVo.getId() && null != studentSaved) {
				UUID uuid = UUID.randomUUID();
				mappingVo.setRegistrationToken(uuid.toString());
				mappingVo.setAdmissionStatus("Applied");
				mappingVo.setCreatedBy(userId);
				mappingVo.setUpdatedBy(userId);
				mappingVo.setStudentMstVo(studentSaved);
				mappingVo.setSchoolId(studentMstVo.getSchoolId());
				mappingVo.setAcademicYear(studentSaved.getAcademicYear());

				String admissionDtl = schoolAdmissionDtlDao.getAdmissionEndDateAndFee(studentMstVo.getSchoolId(),
						studentMstVo.getAcademicYear(),
						studentMstVo.getAdmissionForBoard(),
						studentMstVo.getAdmissionForClass());
				LOGGER.info("----------admission dtl----" + admissionDtl);
				String[] admissionDtlArr = admissionDtl.split(",");

				mappingVo.setAdmissionEndDate(admissionDtlArr[1]);
				mappingVo.setAdmissionForClass(studentMstVo.getAdmissionForClass());
				mappingVo.setAdmissionForBoard(studentMstVo.getAdmissionForBoard());
				mappingVo.setAdmissionForStream(studentMstVo.getAdmissionForStream());
				mappingVo.setAdmissionFees(admissionDtlArr[0]);

				mappingVo = schoolStudentMappingDao.save(mappingVo);
			}

			apiResponse = new ApiResponse(200, "success", "Student Saved Successfully", studentSaved); // Added
			return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			apiResponse = new ApiResponse(406, "Student not saved", "Save init un-successful", user); // Added
			return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.NOT_ACCEPTABLE);
		}
	}

	public ResponseEntity<ApiResponse> getStudentApplicants(Long studentUserId, UserVo userVo) {
		// TODO Auto-generated method stub
		List<CodeValuesVo> applicants = new ArrayList<>();
		if (null != userVo) {
			try {
				List<StudentMstVo> entry = studentDao.getApplicantByStudentUser(studentUserId);
				if (null != entry) {
					for (StudentMstVo resultStudent : entry) {
						CodeValuesVo codeValue = new CodeValuesVo();
						codeValue.setCode(resultStudent.getId().toString());
						codeValue.setValueOne(resultStudent.getStudentName());
						codeValue.setValueTwo(resultStudent.getDateOfBirth());

						applicants.add(codeValue);
					}

					ApiResponse apiResponse = new ApiResponse(200, "success", "Applicants Fetched Successfully",
							applicants);
					return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				ApiResponse apiResponse = new ApiResponse(406, "Student not Fetched", "un-successful", null); // Added
				return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.NOT_ACCEPTABLE);
			}
		}
		ApiResponse apiResponse = new ApiResponse(404, "Student not Found", "un-successful", null); // Added
		return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.NOT_FOUND);
	}

	public ResponseEntity<ApiResponse> getStudentByParentId(Long parentUserId) {
		ApiResponse apiResponse = null;
		try {
			List<StudentMstVo> studentList = studentDao.getStudentByParentId(parentUserId);

      apiResponse =
          new ApiResponse(
              200, "success", "Student applied list", studentList, studentList.size()); // Added
      return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
    } catch (Exception e) {
      e.printStackTrace();
      apiResponse = new ApiResponse(406, "error", "error", null, 0); // Added
      return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.NOT_ACCEPTABLE);
    }
  }

  public ResponseEntity<ApiResponse> getStudentSchoolMapping(
      Long studentId, Long schoolId, String board, String classStd) {
    ApiResponse apiResponse = null;
    try {
      String regToken =
          schoolStudentMappingDao.getStudentSchoolMapping(studentId, schoolId, board, classStd);

      apiResponse =
          new ApiResponse(
              200, "success", "Student School Mapping data fetched", regToken, 1); // Added
      return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
    } catch (Exception e) {
      e.printStackTrace();
      apiResponse = new ApiResponse(406, "error", "error", null, 0); // Added
      return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.NOT_ACCEPTABLE);
    }
  }

  // payments

  public ResponseEntity<ApiResponse> updatePaymentStatus(
      SchoolStudentMappingVo vo, Long loginUserId) {
    ApiResponse apiResponse = null;
    try {
      // UniqueIDGenerator gen = new UniqueIDGenerator();
      String admissionId = gen.getAdmissionId(vo.getStudentId(), vo.getSchoolId());
      String applicationId = vo.getSchoolId() + "_" + vo.getAcademicYear() + "_" + admissionId;
      LOGGER.info("admissionId --- " + admissionId);
      LOGGER.info("applicationId --- " + applicationId);
      schoolStudentMappingDao.updatePaymentStatus(
          vo.getRegistrationToken(),
          applicationId,
          vo.getPaymentAmount(),
          loginUserId,
          new Date(),
          vo.getActualConvenieceFee(),
          vo.getActualGst());

      apiResponse =
          new ApiResponse(
              200, "success", "Payment status updated successfully", applicationId); // Added
      return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
    } catch (Exception e) {
      e.printStackTrace();
      apiResponse = new ApiResponse(406, "error", "error", null, 0); // Added
      return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.NOT_ACCEPTABLE);
    }
  }

  public class StudentApplicationStatusThreadClass extends Thread {

    List<Long> idListLoc = new ArrayList<>();
    String statusNameLoc = "";
    String adminEmailLoc = "";

    public StudentApplicationStatusThreadClass(
        List<Long> idList, String statusName, String adminEmail) {
      super();
      this.idListLoc = idList;
      this.statusNameLoc = statusName;
      this.adminEmailLoc = adminEmail;
    }

    public void run() {
      LOGGER.info("StudentApplicationStatusThreadClass running");

      List<String> emailList = schoolStudentMappingDao.getApplicantEmailFromUserdtl(idListLoc);

      if (emailList.size() > 0) {
        String subjectEmail = "Application Status Changed";
        String bodyEmail = "Your sccurrent Application has been updated to " + statusNameLoc + ".";
        // Send email to the contact person of the school.
        for (String emailStr : emailList) {
          // Send Mail
          mailService.sendMail(emailStr, subjectEmail, bodyEmail, null);
          LOGGER.info("StudentApplicationStatusThreadClass running: mail sent to: " + emailStr);
        }

        // Send one to mail to admin notifying 'All mail sent to school users.'
        String bodyEmailAdmin =
            "Hello Admin<br><br> Application status Thread has completed and mails are sent to student users.";
        mailService.sendMail(adminEmailLoc, subjectEmail, bodyEmailAdmin, null);
        LOGGER.info(
            "StudentApplicationStatusThreadClass running: mail sent to Admin: " + adminEmailLoc);
      }
    }
  }

public ResponseEntity<ApiResponse> listOfSiblingBySchoolId(Long schoolId, Long createdBy) {
	ApiResponse apiResponse = null;
	
	try {
		
		List<SchoolStudentMappingVo> siblingList=schoolStudentMappingDao.getsiblinglist(schoolId,createdBy);
		apiResponse=new ApiResponse(
	              200, "success", "Fatch successfully", siblingList); // Added
	      return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
	} catch (Exception e) {
		e.printStackTrace();
	      apiResponse = new ApiResponse(406, "error", "error", null, 0); // Added
	      return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.NOT_ACCEPTABLE);
	}
  }
}
