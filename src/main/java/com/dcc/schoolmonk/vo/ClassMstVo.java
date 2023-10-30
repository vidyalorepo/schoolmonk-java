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

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "CLASS_MST")
@EntityListeners(AuditingEntityListener.class)
public class ClassMstVo implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonIgnore
	private Long id;

	@Column(name = "sl_no")
	private long slNo;

	@Column(name = "class_name", length = 10)
	private String className;					
	
	@Column(name = "class_display", length = 10)
	private String classDisplay;
	
	@Column(name = "class_stream", length = 10)
	private String classStream;
	
	@Column(name = "start_class", length = 1)
	private boolean startClass = false;	
	
	@Column(name = "end_class", length = 1)
	private boolean endClass = false;	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public long getSlNo() {
		return slNo;
	}

	public void setSlNo(long slNo) {
		this.slNo = slNo;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getClassDisplay() {
		return classDisplay;
	}

	public void setClassDisplay(String classDisplay) {
		this.classDisplay = classDisplay;
	}

	public String getClassStream() {
		return classStream;
	}

	public void setClassStream(String classStream) {
		this.classStream = classStream;
	}

	public boolean isStartClass() {
		return startClass;
	}

	public void setStartClass(boolean startClass) {
		this.startClass = startClass;
	}

	public boolean isEndClass() {
		return endClass;
	}

	public void setEndClass(boolean endClass) {
		this.endClass = endClass;
	}

	@Override
	public String toString() {
		return "ClassMstVo [id=" + id + ", slNo=" + slNo + ", className=" + className + ", classDisplay=" + classDisplay
				+ ", classStream=" + classStream + ", startClass=" + startClass + ", endClass=" + endClass + "]";
	}

}
