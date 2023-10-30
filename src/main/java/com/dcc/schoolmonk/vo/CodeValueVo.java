package com.dcc.schoolmonk.vo;

public class CodeValueVo {

	private String code;
	private String value;

	public String getCode() {
		return code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
		
	@Override
	public String toString() {
		return "CodeValueVo [code=" + code + ", value=" + value + "]";
	}	
}
