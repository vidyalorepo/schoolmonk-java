package com.dcc.schoolmonk.vo;

import java.io.Serializable;
import java.util.List;

public class SchoolClassDtlVo implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String className;			
	private String classDisplay;
	private List<Object> classStream;
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
	public List<Object> getClassStream() {
		return classStream;
	}
	public void setClassStream(List<Object> classStream) {
		this.classStream = classStream;
	}
	
}
