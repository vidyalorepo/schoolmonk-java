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
@Table(name = "SCHOOL_LEVEL_DTL")
@EntityListeners(AuditingEntityListener.class)
public class SchoolLevelDtlVo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne( fetch = FetchType.EAGER )
    @JoinColumn(name = "School_Id", nullable = false )
    @JsonBackReference
	private SchoolMstVo schoolMstVo;
	
	/*@Column(name = "Academic_Year", length = 10)
	private String academicYear;
	
	@Column(name = "Board")
	private String board;
	
	@Column(name = "Medium")
	private String medium;*/
	
	@Column(name = "stream", length = 50)
	private String stream;			//science, arts
	
	@Column(name = "school_level_name", length = 50)
	private String schoolLevelName;		//primary, secondary
	
	@Column(name = "from_class" , length = 20)
	private String fromClass;	//Roman	

	@Column(name = "to_class" , length = 20)
	private String toClass;		//Roman
	
	@Column(name = "from_class_no" , length = 10)
	private Integer fromClassNo;		

	@Column(name = "to_class_no" , length = 10)
	private Integer toClassNo;
	
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

	public String getStream() {
		return stream;
	}

	public void setStream(String stream) {
		this.stream = stream;
	}

	public String getSchoolLevelName() {
		return schoolLevelName;
	}

	public void setSchoolLevelName(String schoolLevelName) {
		this.schoolLevelName = schoolLevelName;
	}

	public String getFromClass() {
		return fromClass;
	}

	public void setFromClass(String fromClass) {
		this.fromClass = fromClass;
	}

	public String getToClass() {
		return toClass;
	}

	public void setToClass(String toClass) {
		this.toClass = toClass;
	}

	public Integer getFromClassNo() {
		return fromClassNo;
	}

	public void setFromClassNo(Integer fromClassNo) {
		this.fromClassNo = fromClassNo;
	}

	public Integer getToClassNo() {
		return toClassNo;
	}

	public void setToClassNo(Integer toClassNo) {
		this.toClassNo = toClassNo;
	}

	public String getAdditionalInfo() {
		return additionalInfo;
	}

	public void setAdditionalInfo(String additionalInfo) {
		this.additionalInfo = additionalInfo;
	}

	@Override
	public String toString() {
		return "SchoolLevelDtlVo [id=" + id + ", stream=" + stream
				+ ", schoolLevelName=" + schoolLevelName + ", fromClass=" + fromClass + ", toClass=" + toClass
				+ ", fromClassNo=" + fromClassNo + ", toClassNo=" + toClassNo + ", additionalInfo=" + additionalInfo
				+ "]";
	}
}
