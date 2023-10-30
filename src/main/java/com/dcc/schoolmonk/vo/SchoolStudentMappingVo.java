package com.dcc.schoolmonk.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "SCHOOL_STUDENT_MAPPING")
@EntityListeners(AuditingEntityListener.class)
public class SchoolStudentMappingVo implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne
    @JoinColumn(name = "School_Id")
	private SchoolMstVo schoolMstVo;

	@OneToOne
    @JoinColumn(name = "Student_Id")
	private StudentMstVo studentMstVo;
	
//	@ManyToOne( fetch = FetchType.EAGER )
//    @JoinColumn(name = "Student_Id", nullable = false )
//    @JsonBackReference
//    public StudentMstVo studentMstVo;
	
	@Column(name = "Academic_Year", length = 10)
	private String academicYear;
	
	@Column(name = "Admission_For_Board", length = 50)
	private String admissionForBoard;
	
	@Column(name = "Admission_For_Class", length = 50)
	private String admissionForClass;
	
	@Column(name = "Admission_For_stream", length = 50)
	private String admissionForStream;
	
	@Column(name = "Admissionion_Status", length = 25)
	private String admissionStatus;		//Admitted,	Interview Pending,	Waitlist, Denied

  @Column(name = "Admissionion_Id", length = 100, unique=true)
  private String admissionId;

  @Column(name = "payment_purpose", length = 60)
  private String paymentPurpose = "Application Fees";

  @Column(name = "payment_amount", length = 25)
  private String paymentAmount;

  @Column(name = "payment_by")
  private Long paymentBy;

  @Column(name = "order_id")
  private String orderId;

  @Column(name = "payment_on")
  private Date paymentOn;

  @Column(name = "shorlisted_by")
  private Long shorlistedBy;

  @Column(name = "shorlisted_on")
  private Date shorlistedOn;

	@Column(name = "registration_token")
	private String registrationToken;
	
	@Column(name = "marks_criteria_match", length = 1)
	private String marksCriteriaMatch;
	/*@Column(name = "Registration_Initiated", length = 1)
	private String regiatrstionInitiated = "Y";
	
	@Column(name = "Payment_Status", length = 1)
	private String paymentStatus = "N";
	
	@Column(name = "Registration_Completed", length = 1)
	private String registrationCompleted = "N";
	
	@Column(name = "Admissionion_Completed", length = 1)
	private String admissionCompleted = "N";*/
	
	@Column(name = "subject_one")
	private String subjectOne;
	
	@Column(name = "subject_two")
	private String subjectTwo;
	
	@Column(name = "subject_three")
	private String subjectThree;
	
	@Column(name = "subject_four")
	private String subjectFour;
	
	@Column(name = "subject_five")
	private String subjectFive;
	
	@Column(name = "subject_six")
	private String subjectSix;
	
	@Column(name = "admission_end_date")
	private String admissionEndDate;
	
	@Column(name = "created_on", nullable = false, updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@CreatedDate
	private Date createdOn = new Date();

	@Column(name = "created_by", nullable = false, updatable = false)
	private Long createdBy;

	@Transient
	private UserVo createdByDetails;

	@Column(name = "updated_on", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
//	@LastModifiedDate
	private Date updatedOn = new Date();

	@Column(name = "updated_by")
	private Long updatedBy;
	
	@Column(name = "Conveniece_Fee")
	private String convenieceFee;
	
	@Column(name = "Actual_Conveniece_Fee")
	private String actualConvenieceFee;
	
	@Column(name = "Actual_Gst")
	private String actualGst;

  @Transient private String admissionFees;

  @Transient private Long studentId;

  @Transient private Long schoolId;

  @Transient private String studentDOB;

  @Transient private String classRange;

  @Transient private List<Long> ids;

  // add by sukdeb
  @Transient private Long page;

  @Transient private Long size;

  @Transient private String orderByColName;

  @Transient private String orderBy;

  public String getOrderId() {
    return orderId;
  }

  public void setOrderId(String orderId) {
    this.orderId = orderId;
  }

  public String getClassRange() {
    return classRange;
  }

  public void setClassRange(String classRange) {
    this.classRange = classRange;
  }

  public Long getPage() {
    return page;
  }

  public void setPage(Long page) {
    this.page = page;
  }

  public Long getSize() {
    return size;
  }

	public void setSize(Long size) {
		this.size = size;
	}

	public String getOrderByColName() {
		return orderByColName;
	}

	public void setOrderByColName(String orderByColName) {
		this.orderByColName = orderByColName;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	
	public SchoolMstVo getSchoolMstVo() {
		return schoolMstVo;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setSchoolMstVo(SchoolMstVo schoolMstVo) {
		this.schoolMstVo = schoolMstVo;
	}

	public StudentMstVo getStudentMstVo() {
		return studentMstVo;
	}

	public void setStudentMstVo(StudentMstVo studentMstVo) {
		this.studentMstVo = studentMstVo;
	}
	public String getAdmissionForClass() {
		return admissionForClass;
	}

	public void setAdmissionForClass(String admissionForClass) {
		this.admissionForClass = admissionForClass;
	}

	public String getAdmissionForBoard() {
		return admissionForBoard;
	}

	public void setAdmissionForBoard(String admissionForBoard) {
		this.admissionForBoard = admissionForBoard;
	}

	public String getAdmissionForStream() {
		return admissionForStream;
	}

	public void setAdmissionForStream(String admissionForStream) {
		this.admissionForStream = admissionForStream;
	}

	public String getAdmissionFees() {
		return admissionFees;
	}

	public void setAdmissionFees(String admissionFees) {
		this.admissionFees = admissionFees;
	}

	public String getAcademicYear() {
		return academicYear;
	}

	public void setAcademicYear(String academicYear) {
		this.academicYear = academicYear;
	}

	public String getAdmissionEndDate() {
		return admissionEndDate;
	}

	public void setAdmissionEndDate(String admissionEndDate) {
		this.admissionEndDate = admissionEndDate;
	}

	public String getAdmissionStatus() {
		return admissionStatus;
	}

	public void setAdmissionStatus(String admissionStatus) {
		this.admissionStatus = admissionStatus;
	}

	public String getAdmissionId() {
		return admissionId;
	}

	public void setAdmissionId(String admissionId) {
		this.admissionId = admissionId;
	}

	public Long getStudentId() {
		return studentId;
	}

	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}

	public Long getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(Long schoolId) {
		this.schoolId = schoolId;
	}

	public String getPaymentAmount() {
		return paymentAmount;
	}

	public void setPaymentAmount(String paymentAmount) {
		this.paymentAmount = paymentAmount;
	}

	public Long getShorlistedBy() {
		return shorlistedBy;
	}

	public void setShorlistedBy(Long shorlistedBy) {
		this.shorlistedBy = shorlistedBy;
	}

	public Date getShorlistedOn() {
		return shorlistedOn;
	}

	public void setShorlistedOn(Date shorlistedOn) {
		this.shorlistedOn = shorlistedOn;
	}

	public String getRegistrationToken() {
		return registrationToken;
	}

	public void setRegistrationToken(String registrationToken) {
		this.registrationToken = registrationToken;
	}

	public String getPaymentPurpose() {
		return paymentPurpose;
	}

	public void setPaymentPurpose(String paymentPurpose) {
		this.paymentPurpose = paymentPurpose;
	}

	public Long getPaymentBy() {
		return paymentBy;
	}

	public void setPaymentBy(Long paymentBy) {
		this.paymentBy = paymentBy;
	}

	public Date getPaymentOn() {
		return paymentOn;
	}

	public void setPaymentOn(Date paymentOn) {
		this.paymentOn = paymentOn;
	}

	public String getMarksCriteriaMatch() {
		return marksCriteriaMatch;
	}

	public void setMarksCriteriaMatch(String marksCriteriaMatch) {
		this.marksCriteriaMatch = marksCriteriaMatch;
	}

	public String getStudentDOB() {
		return studentDOB;
	}

	public void setStudentDOB(String studentDOB) {
		this.studentDOB = studentDOB;
	}

	public String getSubjectOne() {
		return subjectOne;
	}

	public String getSubjectTwo() {
		return subjectTwo;
	}

	public String getSubjectThree() {
		return subjectThree;
	}

	public String getSubjectFour() {
		return subjectFour;
	}

	public String getSubjectFive() {
		return subjectFive;
	}

	public String getSubjectSix() {
		return subjectSix;
	}

	public void setSubjectOne(String subjectOne) {
		this.subjectOne = subjectOne;
	}

	public void setSubjectTwo(String subjectTwo) {
		this.subjectTwo = subjectTwo;
	}

	public void setSubjectThree(String subjectThree) {
		this.subjectThree = subjectThree;
	}

	public void setSubjectFour(String subjectFour) {
		this.subjectFour = subjectFour;
	}

	public void setSubjectFive(String subjectFive) {
		this.subjectFive = subjectFive;
	}

	public void setSubjectSix(String subjectSix) {
		this.subjectSix = subjectSix;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public Date getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}

	public Long getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
	}

	public List<Long> getIds() {
		return ids;
	}

	public void setIds(List<Long> ids) {
		this.ids = ids;
	}

	public String getConvenieceFee() {
		return convenieceFee;
	}

	public String getActualConvenieceFee() {
		return actualConvenieceFee;
	}

	public String getActualGst() {
		return actualGst;
	}

	public void setConvenieceFee(String convenieceFee) {
		this.convenieceFee = convenieceFee;
	}

	public void setActualConvenieceFee(String actualConvenieceFee) {
		this.actualConvenieceFee = actualConvenieceFee;
	}

	public void setActualGst(String actualGst) {
		this.actualGst = actualGst;
	}
	
	
}
