package com.dcc.schoolmonk.common;

public class ApiResponse {

	private int status;
	private String code;
	private String message;
	private Object result;
	private Object result2;
	private Object result3;
	private int noOfData;

	public ApiResponse(int status, String code, String message) {
		this.status = status;
		this.code = code;
		this.message = message;
		this.result = null;
	}

	public ApiResponse(String code, int status, String message) {
		this.status = status;
		this.code = code;
		this.message = message;
		this.result = null;
	}

	public ApiResponse(int status, String code, String message, Object result) {
		this.status = status;
		this.code = code;
		this.message = message;
		this.result = result;
	}

	public ApiResponse(int status, String code, String message, Object result, int noOfData) {
		this.status = status;
		this.code = code;
		this.message = message;
		this.result = result;
		this.noOfData = noOfData;
	}

	public ApiResponse(int status, String code, String message, Object result, Object result2, int noOfData) {
		this.status = status;
		this.code = code;
		this.message = message;
		this.result = result;
		this.result2 = result2;
		this.noOfData = noOfData;
	}

	public ApiResponse(int status, String code, String message, Object result, Object result2, Object result3,
			int noOfData) {
		this.status = status;
		this.code = code;
		this.message = message;
		this.result = result;
		this.result2 = result2;
		this.result3 = result3;
		this.noOfData = noOfData;
	}

	public int getStatus() {
		return status;
	}

	public String getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

	public Object getResult() {
		return result;
	}

	public Object getResult2() {
		return result2;
	}

	public Object getResult3() {
		return result3;
	}

	public int getNoOfData() {
		return noOfData;
	}

	@Override
	public String toString() {
		return "ApiResponse [status=" + status + ", code=" + code + ", message=" + message + ", result=" + result + "]";
	}

}
