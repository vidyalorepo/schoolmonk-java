package com.dcc.schoolmonk.vo;

public class PaymentCollVo {
	private long id;
	private String contactEmail;
	private String schoolPrincipalName;
	private String schoolName;
	private String contactPhone;
	private String schoolId;
	private String payment;
	private String academicYear;
	private String OrderByColName;
	private String OrderBy;
	private Integer page;
	private Integer size;
	private String fiscalYear;

	public PaymentCollVo(String payment) {
		this.payment = payment;
	}

	public PaymentCollVo() {
		super();
	}

	public String getPayment() {
		return payment;
	}

	public void setPayment(String payment) {
		this.payment = payment;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getContactEmail() {
		return contactEmail;
	}

	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}

	public String getSchoolPrincipalName() {
		return schoolPrincipalName;
	}

	public void setSchoolPrincipalName(String schoolPrincipalName) {
		this.schoolPrincipalName = schoolPrincipalName;
	}

	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	public String getContactPhone() {
		return contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	public String getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(String schoolId) {
		this.schoolId = schoolId;
	}

	public String getOrderByColName() {
		return OrderByColName;
	}

	public void setOrderByColName(String orderByColName) {
		OrderByColName = orderByColName;
	}

	public String getOrderBy() {
		return OrderBy;
	}

	public void setOrderBy(String orderBy) {
		OrderBy = orderBy;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public String getAcademicYear() {
		return academicYear;
	}

	public void setAcademicYear(String academicYear) {
		this.academicYear = academicYear;
	}

	public String getFiscalYear() {
		return fiscalYear;
	}

	public void setFiscalYear(String fiscalYear) {
		this.fiscalYear = fiscalYear;
	}
	
}
