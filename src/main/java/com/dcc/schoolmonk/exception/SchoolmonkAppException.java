package com.dcc.schoolmonk.exception;

import java.io.Serializable;

public abstract class SchoolmonkAppException extends RuntimeException implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public SchoolmonkAppException()
	{

	}

	/**
	 * @param arg0
	 */
	public SchoolmonkAppException(String arg0)
	{
		super(arg0);

	}

	/**
	 * @param arg0
	 */
	public SchoolmonkAppException(Throwable arg0)
	{
		super(arg0);

	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public SchoolmonkAppException(String message, Throwable cause)
	{
		super(message, cause);

	}

	// list of utility methods
	public abstract String getExceptionCode();
	public abstract void setExceptionCode(String errorCode);

	public abstract String getExceptionType();

	public abstract String getExceptionUniqueNumber();

	public abstract String getExceptionMessage();

	public abstract String toString();


}
