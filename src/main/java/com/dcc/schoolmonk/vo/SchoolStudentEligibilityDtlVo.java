package com.dcc.schoolmonk.vo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "SCHOOL_STUDENT_ELIGIBILITY_DTL")			// NOT NEEDED NOW *******************************
@EntityListeners(AuditingEntityListener.class)
public class SchoolStudentEligibilityDtlVo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne( fetch = FetchType.EAGER )
    @JoinColumn(name = "School_Id", nullable = false )
    @JsonBackReference
	private SchoolMstVo schoolMstVo;
	
	@Column(name = "Academic_Year", length = 10)
	private String academicYear;
	
	@Column(name = "board_name", length = 20)
	private String boardName;
	
	@Column(name = "upto_class" , length = 20)
	private String uptoClass;		//Roman - this takes the information for validation based on dob. ie. for 13 yr old student, can take admission upto class 8
	
	@Column(name = "upto_class_no" , length = 10)
	private Integer uptoClassNo;
	
	@Column(name = "Class_Stream", length = 20)
	private String classStream;
	
	/*@Column(name = "eligibility_type", length = 20)
	private String eligibilityType;*/
	
	@Column(name = "DOB_Start_Date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dOBStartDate;
	
	@Column(name = "DOB_End_Date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dOBEndDate;
	
	@Column(name = "eligibility_marks", length = 20)
	private String eligibilityMarks;
	
	
	
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

	public String getUptoClass() {
		return uptoClass;
	}

	public void setUptoClass(String uptoClass) {
		this.uptoClass = uptoClass;
	}

	public Integer getUptoClassNo() {
		return uptoClassNo;
	}

	public void setUptoClassNo(Integer uptoClassNo) {
		this.uptoClassNo = uptoClassNo;
	}

	public String getClassStream() {
		return classStream;
	}

	public void setClassStream(String classStream) {
		this.classStream = classStream;
	}

	public Date getdOBStartDate() {
		return dOBStartDate;
	}

	public void setdOBStartDate(Date dOBStartDate) {
		this.dOBStartDate = dOBStartDate;
	}

	public Date getdOBEndDate() {
		return dOBEndDate;
	}

	public void setdOBEndDate(Date dOBEndDate) {
		this.dOBEndDate = dOBEndDate;
	}

	/*public String getEligibilityType() {
		return eligibilityType;
	}

	public void setEligibilityType(String eligibilityType) {
		this.eligibilityType = eligibilityType;
	}*/

	public String getEligibilityMarks() {
		return eligibilityMarks;
	}

	public void setEligibilityMarks(String eligibilityMarks) {
		this.eligibilityMarks = eligibilityMarks;
	}

	public String getBoardName() {
		return boardName;
	}

	public void setBoardName(String boardName) {
		this.boardName = boardName;
	}

	@Override
	public String toString() {
		return "SchoolStudentEligibilityDtlVo [id=" + id + ", academicYear="
				+ academicYear + ", boardName=" + boardName + ", uptoClass=" + uptoClass + ", classStream="
				+ classStream + ", dOBStartDate=" + dOBStartDate + ", dOBEndDate=" + dOBEndDate + ", eligibilityMarks="
				+ eligibilityMarks + "]";
	}

}
