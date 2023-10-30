package com.dcc.schoolmonk.vo;

public class CodeValuesVo {

	private String code;
	private String valueOne;
	private String valueTwo;
	public String getCode() {
		return code;
	}
	public String getValueOne() {
		return valueOne;
	}
	public String getValueTwo() {
		return valueTwo;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public void setValueOne(String valueOne) {
		this.valueOne = valueOne;
	}
	public void setValueTwo(String valueTwo) {
		this.valueTwo = valueTwo;
	}
	@Override
	public String toString() {
		return "CodeValuesVo [code=" + code + ", valueOne=" + valueOne + ", valueTwo=" + valueTwo + "]";
	}
		
	
}
