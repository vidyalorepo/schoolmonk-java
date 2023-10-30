/*package com.dcc.schoolmonk.vo;

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
@Table(name = "SCHOOL_COURSE_FEES_DTL")
@EntityListeners(AuditingEntityListener.class)
public class SchoolCourseFeesDtlVo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne( fetch = FetchType.EAGER )
    @JoinColumn(name = "School_Id", nullable = false )
    @JsonBackReference
	private SchoolMstVo schoolMstVo;
	
	@Column(name = "Academic_Year", length = 10)
	private String academicYear;
	
	@Column(name = "Class_Range", length = 10)
	private String classRange;
	
	@Column(name = "Chosen_Stream", length = 10)
	private String chosenStream;
	
	@Column(name = "Fees_Start_Date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date feesStartDate;
	
	@Column(name = "Fees_End_Date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date feesEndDate;
	
	@Column(name = "Fees_Amount")
	private Long feesAmount;

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

	public String getChosenStream() {
		return chosenStream;
	}

	public void setChosenStream(String chosenStream) {
		this.chosenStream = chosenStream;
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

	public Long getFeesAmount() {
		return feesAmount;
	}

	public void setFeesAmount(Long feesAmount) {
		this.feesAmount = feesAmount;
	}

	@Override
	public String toString() {
		return "SchoolCourseFeesDtlVo [id=" + id + ", schoolMstVo=" + schoolMstVo + ", academicYear=" + academicYear
				+ ", classRange=" + classRange + ", chosenStream=" + chosenStream + ", feesStartDate=" + feesStartDate
				+ ", feesEndDate=" + feesEndDate + ", feesAmount=" + feesAmount + "]";
	}
}
*/