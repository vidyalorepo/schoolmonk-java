package com.dcc.schoolmonk.vo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "SCHOOL_LEVEL_MST")
@EntityListeners(AuditingEntityListener.class)
public class SchoolLevelMstVo implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "school_level_name", length = 50)
	private String schoolLevelName;		//primary, secondary
	
	@Column(name = "from_class" , length = 20)
	private String fromClass;	//Roman	

	@Column(name = "to_class" , length = 20)
	private String toClass;		//Roman
	
	@Column(name = "from_class_no" , length = 10)
	private String fromClassNo;		

	@Column(name = "to_class_no" , length = 10)
	private String toClassNo;
	
	@Column(name = "additional_desc")
	private String additionalDesc;
	
	@Column(name = "sl_no")
	private int slNo;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getFromClassNo() {
		return fromClassNo;
	}

	public void setFromClassNo(String fromClassNo) {
		this.fromClassNo = fromClassNo;
	}

	public String getToClassNo() {
		return toClassNo;
	}

	public void setToClassNo(String toClassNo) {
		this.toClassNo = toClassNo;
	}

	public String getAdditionalDesc() {
		return additionalDesc;
	}

	public void setAdditionalDesc(String additionalDesc) {
		this.additionalDesc = additionalDesc;
	}

	public int getSlNo() {
		return slNo;
	}

	public void setSlNo(int slNo) {
		this.slNo = slNo;
	}

	@Override
	public String toString() {
		return "SchoolLevelMstVo [id=" + id + ", schoolLevelName=" + schoolLevelName + ", fromClass=" + fromClass
				+ ", toClass=" + toClass + ", fromClassNo=" + fromClassNo + ", toClassNo=" + toClassNo
				+ ", additionalDesc=" + additionalDesc + "]";
	}
}
