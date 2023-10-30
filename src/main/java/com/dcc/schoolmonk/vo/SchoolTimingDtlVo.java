package com.dcc.schoolmonk.vo;

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

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "SCHOOL_TIMING_DTL")
@EntityListeners(AuditingEntityListener.class)
public class SchoolTimingDtlVo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne( fetch = FetchType.EAGER )
    @JoinColumn(name = "School_Id", nullable = false )
    @JsonBackReference
	private SchoolMstVo schoolMstVo;
	
	@Column(name = "Academic_Year", length = 10)
	private String academicYear = "2021-2022";
	
	/*@Column(name = "board_name", length = 20)
	private String boardName;*/
	
	/*@Column(name = "Class_Range", length = 100)
	private String classRange;*/ // put place holder
	
	@Column(name = "school_level_name", length = 50)
	private String schoolLevelName;	
	
	@Column(name = "Start_Time", length = 10)
	private String startTime;
	
	@Column(name = "End_Time", length = 10)
	private String endTime;

	@Column(name = "additional_info")
	private String additionalInfo;

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

	public String getSchoolLevelName() {
		return schoolLevelName;
	}

	public void setSchoolLevelName(String schoolLevelName) {
		this.schoolLevelName = schoolLevelName;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getAdditionalInfo() {
		return additionalInfo;
	}

	public void setAdditionalInfo(String additionalInfo) {
		this.additionalInfo = additionalInfo;
	}

	@Override
	public String toString() {
		return "SchoolTimingDtlVo [id=" + id + ", academicYear=" + academicYear
				+ ", additionalInfo=" + additionalInfo + ", schoolLevelName=" + schoolLevelName + ", startTime=" + startTime + ", endTime="
				+ endTime + "]";
	}
}
