package com.dcc.schoolmonk.exception;

/**
 * @author abarua
 *
 */
public class SchoolmonkAppDataProcessException extends SchoolmonkAppException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String exceptionCode = null;
	private String exceptionMessage = null;
	private String exceptionType = null;
	private String exceptionUniqueNumber = null;
	
	/**
	 * 
	 */
	public SchoolmonkAppDataProcessException() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param arg0
	 */
	public SchoolmonkAppDataProcessException(String arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param arg0
	 */
	public SchoolmonkAppDataProcessException(Throwable arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public SchoolmonkAppDataProcessException(String arg0, Throwable arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 * @param message
	 * @param code
	 * @param type
	 * @param cause
	 */
	public SchoolmonkAppDataProcessException(String message,String code,String type, Throwable cause){
		super(ExceptionMessageUtil.getExceptionMessage(message, code, type),cause);
		this.exceptionCode = code;
		this.exceptionMessage = message;
		this.exceptionType = type;	
	}
	
	@Override
	public String getExceptionCode() {
		// TODO Auto-generated method stub
		return this.exceptionCode;
	}

	@Override
	public String getExceptionMessage() {
		StringBuffer buff = new StringBuffer(super.getMessage());
		Throwable cause = this.getCause();
		if (cause != null)
		{
			buff.append("\nCause: ").append(cause.getMessage());
		}
		
		return buff.toString();
	}

	@Override
	public String getExceptionType() {
		// TODO Auto-generated method stub
		return this.exceptionType;
	}

	@Override
	public String getExceptionUniqueNumber() {
		// TODO Auto-generated method stub
		return this.exceptionUniqueNumber;
	}
	
	public String toString()
	{
		StringBuffer buff = new StringBuffer("HPLDataProcessException: [" + super.getMessage());
		Throwable cause = this.getCause();
		if (cause != null)
		{
			buff.append("\nCause: ").append(cause.toString());
		}
		buff.append("]");
		
		return buff.toString();
	}

	@Override
	public void setExceptionCode(String errorCode) {
		// TODO Auto-generated method stub
		
	}

}
