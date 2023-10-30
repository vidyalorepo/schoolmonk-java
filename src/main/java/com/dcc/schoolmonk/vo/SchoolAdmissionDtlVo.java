package com.dcc.schoolmonk.vo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "SCHOOL_ADMISSION_DTL")
@EntityListeners(AuditingEntityListener.class)
public class SchoolAdmissionDtlVo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne( fetch = FetchType.EAGER )
    @JoinColumn(name = "School_Id", nullable = false )
    @JsonBackReference
	private SchoolMstVo schoolMstVo;
	
	@Column(name = "Academic_Year", length = 10)
	private String academicYear = "2023-2024";
	
	@Column(name = "Board")
	private String board;
	
	/*@Column(name = "Medium")
	private String medium;
	
	@Column(name = "stream", length = 20)
	private String stream;			//science, arts*/	
	
	@Column(name = "Class_Range", length = 100)
	private String classRange; // put place holder
	
	@Column(name = "Admission_Start_Date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date admissionStartDate;
	
	@Column(name = "Admission_End_Date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date admissionEndDate;
	
	@Column(name = "Fees_Start_Date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date feesStartDate;
	
	@Column(name = "Fees_End_Date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date feesEndDate;
	
	@Column(name = "Fees_Amount")
	private Double feesAmount;
	
	@Column(name = "Publish_Status", length = 1)
	private String publishStatus = "Y";
	
	@Column(name = "Min_DOB")
	@Temporal(TemporalType.TIMESTAMP)
	private Date minDOB;
	
	@Column(name = "Max_DOB")
	@Temporal(TemporalType.TIMESTAMP)
	private Date maxDOB;
	
	@Column(name = "Other_Charges_Amount")
	private Double otherChargesAmount;		//Any other charges [tax etc]
	
	@Column(name = "Conveniece_Fee")
	private String convenieceFee;
	
	@Column(name = "Actual_Conveniece_Fee")
	private String actualConvenieceFee;
	
	@Column(name = "Actual_Gst")
	private String actualGst;
	
	@Transient
	List<Long> idList = new ArrayList<Long>();
	
	@JsonManagedReference
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "schoolAdmissionDtlVo")
    private List<SchoolStreamDtlVo> schoolStreamDtlVo = new ArrayList<>();
	
	@Transient
	String applicantDOB;
	
	@Transient
	AttachmentVo document = new AttachmentVo();
	
	@Transient
	private String schoolName;
	
	@Transient
	private String schoolSlugName;

	@Transient
	private Integer page;
	@Transient
	private Integer size;
	@Transient
	private String orderByColName;
	@Transient
	private String orderBy;
	

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
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

	public String getSchoolName() {
		return schoolName;
	}

	public String getSchoolSlugName() {
		return schoolSlugName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	public void setSchoolSlugName(String schoolSlugName) {
		this.schoolSlugName = schoolSlugName;
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

	public String getAcademicYear() {
		return academicYear;
	}

	public void setAcademicYear(String academicYear) {
		this.academicYear = academicYear;
	}

	public String getClassRange() {
		return classRange;
	}

	public void setClassRange(String classRange) {
		this.classRange = classRange;
	}

	public Date getAdmissionStartDate() {
		return admissionStartDate;
	}

	public void setAdmissionStartDate(Date admissionStartDate) {
		this.admissionStartDate = admissionStartDate;
	}

	public Date getAdmissionEndDate() {
		return admissionEndDate;
	}

	public void setAdmissionEndDate(Date admissionEndDate) {
		this.admissionEndDate = admissionEndDate;
	}

	public Date getFeesStartDate() {
		return feesStartDate;
	}

	public void setFeesStartDate(Date feesStartDate) {
		this.feesStartDate = feesStartDate;
	}

	public Date getFeesEndDate() {
		return feesEndDate;
	}

	public void setFeesEndDate(Date feesEndDate) {
		this.feesEndDate = feesEndDate;
	}

	public Double getFeesAmount() {
		return feesAmount;
	}

	public void setFeesAmount(Double feesAmount) {
		this.feesAmount = feesAmount;
	}

	public String getPublishStatus() {
		return publishStatus;
	}

	public void setPublishStatus(String publishStatus) {
		this.publishStatus = publishStatus;
	}

	public String getBoard() {
		return board;
	}

	public void setBoard(String board) {
		this.board = board;
	}

	public Date getMinDOB() {
		return minDOB;
	}

	public void setMinDOB(Date minDOB) {
		this.minDOB = minDOB;
	}

	public Date getMaxDOB() {
		return maxDOB;
	}

	public void setMaxDOB(Date maxDOB) {
		this.maxDOB = maxDOB;
	}

	public List<Long> getIdList() {
		return idList;
	}

	public void setIdList(List<Long> idList) {
		this.idList = idList;
	}

	public List<SchoolStreamDtlVo> getSchoolStreamDtlVo() {
		return schoolStreamDtlVo;
	}

	public void setSchoolStreamDtlVo(List<SchoolStreamDtlVo> schoolStreamDtlVo) {
		this.schoolStreamDtlVo = schoolStreamDtlVo;
	}

	public Double getOtherChargesAmount() {
		return otherChargesAmount;
	}

	public void setOtherChargesAmount(Double otherChargesAmount) {
		this.otherChargesAmount = otherChargesAmount;
	}

	public String getApplicantDOB() {
		return applicantDOB;
	}

	public void setApplicantDOB(String applicantDOB) {
		this.applicantDOB = applicantDOB;
	}

	public AttachmentVo getDocument() {
		return document;
	}

	public void setDocument(AttachmentVo document) {
		this.document = document;
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
