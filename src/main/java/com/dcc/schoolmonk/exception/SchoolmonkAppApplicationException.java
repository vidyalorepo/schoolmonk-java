/**
 * 
 */
package com.dcc.schoolmonk.exception;

/**
 * ApplicationException - Generic Application Exception with useful
 * enforce method commonly used for checking validations.
 * 
 */
public class SchoolmonkAppApplicationException extends SchoolmonkAppException
{
	private static final long serialVersionUID = 1L;
	
	private String exceptionCode = null;
	private String exceptionMessage = null;
	private String exceptionType = null;
	private String exceptionUniqueNumber = null;

	/**
	 * Default constructor
	 */
	public SchoolmonkAppApplicationException()
	{
		super();
	}
	/**
	 * Constructor for ApplicationException
	 * @param message String
	 */
	public SchoolmonkAppApplicationException(String message)
	{
		super(message);
	}

	/**
	 * Constructor for ApplicationException
	 * @param cause Throwable
	 */
	public SchoolmonkAppApplicationException(Throwable cause)
	{
		super(cause);
	}

	/**
	 * Constructor for ApplicationException
	 * @param message String
	 * @param cause Throwable
	 */
	public SchoolmonkAppApplicationException(String message, Throwable cause)
	{
		super(message, cause);
	}
	
	/**
	 * 
	 * @param message
	 * @param code
	 * @param type
	 * @param cause
	 */
	public SchoolmonkAppApplicationException(String message,String code,String type,Throwable cause){
		super(ExceptionMessageUtil.getExceptionMessage(message, code, type),cause);
		this.exceptionCode = code;
		this.exceptionMessage = message;
		this.exceptionType = type;	
	}

	/**
	 * Method: HPLApplicationException.enforce
	 * @param expression boolean indicating whether or not to throw a new Exception
	 * @param message String
	 * @param code String
	 * @param type String
	 */
	public static void enforce(boolean expression, String message,String code,String type)
	{
		if (!expression)
		{
			throw new SchoolmonkAppApplicationException(ExceptionMessageUtil.getExceptionMessage(message, code, type));
		}
	}
	
	/**
	 * Method: HPLApplicationException.toString
	 * @return
	 * @see java.lang.Throwable#toString()
	 */
	public String toString()
	{
		StringBuffer buff = new StringBuffer("HPLApplicationException: [" + super.getMessage());
		Throwable cause = this.getCause();
		if (cause != null)
		{
			buff.append("\nCause: ").append(cause.toString());
		}
		buff.append("]");
		
		return buff.toString();
	}

	/**
	 * Method: HPLApplicationException.getExceptionMessage
	 * @return
	 * @see java.lang.Throwable#getMessage()
	 */
	public String getExceptionMessage()
	{
		StringBuffer buff = new StringBuffer(super.getMessage());
		Throwable cause = this.getCause();
		if (cause != null)
		{
			buff.append("\nCause: ").append(cause.getMessage());
		}
		
		return buff.toString();
	}

	/**
	 * @return the exceptionCode
	 */
	public String getExceptionCode() {
		return exceptionCode;
	}
	
	/**
	 * @return the exceptionType
	 */
	public String getExceptionType() {
		return exceptionType;
	}

	@Override
	public String getExceptionUniqueNumber() {
		// TODO Auto-generated method stub
		return this.exceptionUniqueNumber;
	}

	@Override
	public void setExceptionCode(String errorCode) {
		// TODO Auto-generated method stub
		
	}
	
	public void setCustomMsgs(String custMsg) {
		
	}

	public void setExceptionMsgs(String excepMsg) {
		
		this.exceptionMessage = excepMsg; 
	}

	

}
