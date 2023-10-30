package com.dcc.schoolmonk.dao;

import com.dcc.schoolmonk.vo.SchoolStudentMappingVo;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface SchoolStudentMappingDao extends CrudRepository<SchoolStudentMappingVo, Long> {

	@Query(nativeQuery = true,value = "select * from SCHOOL_STUDENT_MAPPING where Student_Id = ?1 ")
	List<SchoolStudentMappingVo> sortlistedByStudent(Long studentId);
	
	//@Query(nativeQuery = true,value = "select registration_token from SCHOOL_STUDENT_MAPPING where Student_Id = ?1 and School_Id = ?2")
	@Query(nativeQuery = true, value = "CALL checkIfAlreadyApplied(:whereClause);")
	String checkIfAlreadyApplied(@Param("whereClause") String whereClause);

  // fetch applied student list of a academic year
  // @Query(nativeQuery = true,value = "select * from SCHOOL_STUDENT_MAPPING where School_Id =
  // :schoolId and Academic_Year = :academicYear ")
  @Query(nativeQuery = true, value = "CALL getAppliedStudent(:whereClause);")
  List<SchoolStudentMappingVo> appliedStudent(@Param("whereClause") String whereClause);

  @Query(
      nativeQuery = true,
      value =
          "update SCHOOL_STUDENT_MAPPING set Admissionion_Status = 'Payment Completed', Admissionion_Id = :admissionId,"
              + "payment_amount = :paymentAmount, payment_by = :paymentBy, payment_on = :paymentOn,  Actual_Conveniece_Fee = :actualCon, Actual_Gst = :actualGst"
              + " where registration_token = :token")
  @Modifying
  @Transactional
  void updatePaymentStatus(
      String token,
      String admissionId,
      String paymentAmount,
      Long paymentBy,
      Date paymentOn,
      String actualCon,
      String actualGst);

  @Query(
      nativeQuery = true,
      value =
          "update SCHOOL_STUDENT_MAPPING set Admissionion_Id=:admissionId, "
              + "payment_amount = :paymentAmount, payment_by = :paymentBy, payment_on = :paymentOn,  Actual_Conveniece_Fee = :actualCon, Actual_Gst = :actualGst"
              + " where registration_token = :token")
  @Modifying
  @Transactional
  void updatePayment(
      String token,
      String admissionId,
      String paymentAmount,
      Long paymentBy,
      Date paymentOn,
      String actualCon,
      String actualGst);

  @Query(
      nativeQuery = true,
      value =
          "update SCHOOL_STUDENT_MAPPING set Admissionion_Status = 'Shortlisted', shorlisted_by = :shorlistedBy, shorlisted_on = :shorlistedOn where id in :ids")
  @Modifying
  @Transactional
  void updateShortlisted(List<Long> ids, Long shorlistedBy, Date shorlistedOn);

  @Query(nativeQuery = true, value = "select count(*) from SCHOOL_STUDENT_MAPPING ")
  Long noOfData();

  SchoolStudentMappingVo findByRegistrationToken(String registrationToken);

	List<SchoolStudentMappingVo> findByAdmissionStatus(String admissionStatus);
	
	@Query(nativeQuery = true,value = "select count(*) from SCHOOL_STUDENT_MAPPING where Admissionion_Status = :admissionStatus")
	Long noOfDataByAdmissionStatus(String admissionStatus);

	SchoolStudentMappingVo findByAdmissionForClassAndStudentMstVoIdAndSchoolMstVoId(String admissionClass, Long studentId, Long schoolId);
	
	@Query(nativeQuery = true,value = "update SCHOOL_STUDENT_MAPPING set Admissionion_Status = :admissionStatus, updated_by = :updatedBy, updated_on = :updatedOn where id in :ids")
	@Modifying
	@Transactional
	void updateStatus(List<Long> ids, Long updatedBy, Date updatedOn, String admissionStatus);
	
	@Query(nativeQuery = true,value = "select id from (SELECT id, Student_Id, Admissionion_Status,"
			+ "ifnull((select order_no from ADMISSION_STATUS_MST where status_name = Admissionion_Status AND Admissionion_Status <> 'Applied'),0)"
			+ "as order_no, (select order_no from ADMISSION_STATUS_MST where status_name = :admissionStatus) as next_order_no FROM SCHOOL_STUDENT_MAPPING where id in :inputIds) inter_table "
			+ "where order_no < (select order_no from ADMISSION_STATUS_MST where status_name = :admissionStatus)")
	List<Long> getValidIdsStatus(String admissionStatus, List<Long> inputIds);

	@Query(nativeQuery = true,value = "select distinct Student_Name from STUDENT_MST where id in (select Student_Id from SCHOOL_STUDENT_MAPPING where id in :notMatchedIds)")
	List<String> getApplicantNamesByIds(List<Long> notMatchedIds);
	
	
	@Query(nativeQuery = true,value = "select email from USER_DETAILS where userId in(select Student_User_Id from STUDENT_MST where id in (select Student_Id from SCHOOL_STUDENT_MAPPING where id in :validIds))")
	List<String> getApplicantEmailFromUserdtl(List<Long> validIds);
	
	// added by Akash
	
	@Query(nativeQuery = true, value = "Select SCHOOL_MST.Contact_Email,SCHOOL_MST.School_Principal_Name, SCHOOL_MST.School_Name, SCHOOL_MST.Contact_Phone, \n"
			+ "SCHOOL_STUDENT_MAPPING.School_Id, SUM(SCHOOL_STUDENT_MAPPING.payment_amount) as Payment \n"
			+ "from SCHOOL_MST, SCHOOL_STUDENT_MAPPING where SCHOOL_STUDENT_MAPPING.School_Id = SCHOOL_MST.id GROUP BY SCHOOL_STUDENT_MAPPING.School_Id")
	List<String> getPaymentCollectionSum();
	
	@Query(nativeQuery = true, value = "CALL schoolPaymentProc(:whereClause, :limitClause);")	
	List<String> getSchoolPayCollectionProc(@Param("whereClause") String whereClause, @Param("limitClause") String limitClause);
	
	@Query(nativeQuery = true, value = "CALL schoolPaymentDetailsProc(:whereClause, :limitClause);")	
	List<String> getSchoolPayDetailsProc(@Param("whereClause") String whereClause, @Param("limitClause") String limitClause);

	
	@Query(nativeQuery = true, value = "SELECT registration_token FROM SCHOOL_STUDENT_MAPPING where Student_Id = :studentId AND School_Id = :schoolId AND Admission_For_Board = :board AND Admission_For_Class = :classStd")
	String getStudentSchoolMapping(Long studentId, Long schoolId, String board, String classStd);

  /*//@Query(nativeQuery = true, value = "CALL schoolPaymentProc();")		//:limitClause, :whereClause
  //	List<String> getSchoolPayCollectionProc(@Param("whereClause") String whereClause, @Param("limitClause") String limitClause);
  	@Query(nativeQuery = true, value = "Select SUM(ssm.payment_amount) payment "
  			+ "from SCHOOL_MST sm, SCHOOL_STUDENT_MAPPING ssm where ssm.School_Id = sm.id GROUP BY ssm.School_Id")
  	List<PaymentCollectionVo> getSchoolPayCollectionProc(@Param("whereClause") String whereClause, @Param("limitClause") String limitClause);

  	*/
  /*@Query("Select new com.dcc.schoolmonk.vo.PaymentCollVo(SUM(ssm.payment_amount)) "
  		+ "from SCHOOL_MST sm, SCHOOL_STUDENT_MAPPING ssm where ssm.School_Id = sm.id GROUP BY ssm.School_Id")
  List<PaymentCollVo> getSchoolPayCollProc(@Param("whereClause") String whereClause, @Param("limitClause") String limitClause);*/

  @Query(
      nativeQuery = true,
      value = "CALL findAllgetAppliedStudent( :orderByClause, :limitClause, :whereClause);")
  List<SchoolStudentMappingVo> findAllgetAppliedStudent(
      @Param("whereClause") String whereClause,
      @Param("limitClause") String limitClause,
      @Param("orderByClause") String orderByClause);

  @Query(nativeQuery = true, value = "CALL countTotalByinput(:whereClause, :tableName);")
  Long getTotalCountByInput(
      @Param("whereClause") String whereClause, @Param("tableName") String tableName);

  @Transactional
  @Modifying
  @Query(
      nativeQuery = true,
      value = "update SCHOOL_STUDENT_MAPPING set order_id=?1 where registration_token=?2")
  int updateOrderId(String orderId, String registrationToken);

  @Query(
      nativeQuery = true,
      value =
          "update SCHOOL_STUDENT_MAPPING set Admissionion_Status = 'Payment Completed' "
              + " where order_id = :orderId")
  @Modifying
  @Transactional
  void updateSuccessfulPaymentStatus(@Param("orderId") String orderId);

  @Query(nativeQuery = true, value = "select * from SCHOOL_STUDENT_MAPPING where order_id=?1")
  Optional<SchoolStudentMappingVo> findByOrderId(String orderId);

  @Query(nativeQuery = true, value = "SELECT * FROM SCHOOL_STUDENT_MAPPING where School_id = ?1 and (Admissionion_status ='Admission Offered' and created_by =?2)")
  List<SchoolStudentMappingVo> getsiblinglist(Long schoolId, Long createdBy);
  
  @Query(nativeQuery = true, value = "SELECT * FROM SCHOOL_STUDENT_MAPPING where Admissionion_Id =?1")
  SchoolStudentMappingVo getDetailsById(String admissionId);
}
