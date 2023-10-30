package com.dcc.schoolmonk.dao;

import com.dcc.schoolmonk.vo.SchoolAdmissionDtlVo;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SchoolAdmissionDtlDao extends JpaRepository<SchoolAdmissionDtlVo, Long> {

  @Query(
      nativeQuery = true,
      value = "select * from SCHOOL_ADMISSION_DTL where School_Id = :schoolId")
  List<SchoolAdmissionDtlVo> findBySchoolId(Long schoolId);

  @Query(
      nativeQuery = true,
      value =
          "SELECT class_range FROM SCHOOL_ADMISSION_DTL where School_Id = :schoolId and Board = :boardName and class_range in :clsList")
  List<String> getExistingClass(long schoolId, String boardName, List<String> clsList);

  @Query(
      nativeQuery = true,
      value =
          "SELECT ifnull(Fees_Amount, 0), DATE_FORMAT(Admission_End_Date,'%d-%m-%Y') "
              + "FROM SCHOOL_ADMISSION_DTL where School_Id = :schoolId and Academic_Year = :academicYear "
              + "and class_range = :classRange  ") // and stream like %:classStream% "and Board =
                                                   // :boardName
  String getAdmissionFees(
      @Param("schoolId") long schoolId,
      @Param("academicYear") String academicYear,
      // @Param("boardName") String boardName,
      @Param("classRange") String classRange); // , @Param("classStream") String classStream);

  @Query(
      nativeQuery = true,
      value =
          "SELECT * "
              + "FROM SCHOOL_ADMISSION_DTL where School_Id = :schoolId and Academic_Year = :academicYear "
              + "and class_range = :classRange  ") // and stream like %:classStream% "and Board =
                                                   // :boardName
  Optional<SchoolAdmissionDtlVo> findBySchoolIdAndAcademicYearAndClassRange(
      @Param("schoolId") long schoolId,
      @Param("academicYear") String academicYear,
      // @Param("boardName") String boardName,
      @Param("classRange") String classRange); // , @Param("classStream") String classStream);

  @Query(
      nativeQuery = true,
      value =
          "SELECT id FROM SCHOOL_ADMISSION_DTL where School_Id = :schoolId and Academic_Year = :academicYear and board_name = :boardName ")
  List<Long> noOfAdmissionData(
      @Param("schoolId") long schoolId,
      @Param("academicYear") String academicYear,
      @Param("boardName") String boardName);

	@Transactional
	@Modifying
	@Query(nativeQuery = true, value = "delete FROM SCHOOL_ADMISSION_DTL where id in :idList ")
	void deleteAdmissionData(@Param("idList") List<Long> idList);

	@Query(nativeQuery = true, value = "SELECT min(Admission_Start_Date), max(Admission_End_Date) FROM SCHOOL_ADMISSION_DTL where School_Id = :schoolId and Publish_Status = 'Y' ")
	String getAdmissionDates(long schoolId);

	@Query(nativeQuery = true, value = "SELECT Publish_Status "
			+ "FROM SCHOOL_ADMISSION_DTL where School_Id = :schoolId and Academic_Year = :academicYear "
			+ "and Board = :boardName and class_range = :classRange")
	public String ifDataExist(@Param("schoolId") long schoolId, @Param("academicYear") String academicYear,
			@Param("boardName") String boardName, @Param("classRange") String classRange);

	@Modifying
	@Transactional
	@Query(nativeQuery = true, value = "update SCHOOL_ADMISSION_DTL set Publish_Status = :publishStatus where id in :idList ")
	void updatePublishStatus(List<Long> idList, String publishStatus);

	@Query(nativeQuery = true, value = "CALL checkIfEligible(:whereClause);")
	SchoolAdmissionDtlVo checkIfEligible(@Param("whereClause") String whereClause);

	@Query(nativeQuery = true, value = "SELECT ifnull(Fees_Amount, 0), DATE_FORMAT(Admission_End_Date,'%d-%m-%Y') "
			+ "FROM SCHOOL_ADMISSION_DTL where School_Id = :schoolId and Academic_Year = :academicYear "
			+ "and class_range = :classRange  and Board = :boardName") // and stream like %:classStream% "and Board =
																		// :boardName
	String getAdmissionEndDateAndFee(@Param("schoolId") long schoolId, @Param("academicYear") String academicYear,
			@Param("boardName") String boardName, @Param("classRange") String classRange); // , @Param("classStream")
																							// String classStream);

	@Query(nativeQuery = true, value = "SELECT * FROM SCHOOL_ADMISSION_DTL WHERE Publish_Status = 'Y' ")
	List<SchoolAdmissionDtlVo> findAllPublishNotice();

	// add by mouli

	@Query(nativeQuery = true, value = "CALL getAdmissionListById(:orderByClause, :limitClause, :whereClause);")
	List<SchoolAdmissionDtlVo> findAdmissionNoticeById(@Param("whereClause") String whereClause,
			@Param("limitClause") String limitClause, @Param("orderByClause") String orderByClause);

	@Query(nativeQuery = true, value = "CALL countTotalByinput(:whereClause, :tableName);")
	Long getTotalCountByInput(@Param("whereClause") String whereClause, @Param("tableName") String tableName);
}
