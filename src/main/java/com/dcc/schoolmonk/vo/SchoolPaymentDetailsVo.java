package com.dcc.schoolmonk.vo;

public class SchoolPaymentDetailsVo {
	private long id;
	private String academicYear;
	private String studentName;
	private String admissionForClass;
	private String paymentOn;
	private String payment;
	private String schoolId;
	private String studentId;
	private String OrderByColName;
	private String OrderBy;
	private Integer page;
	private Integer size;
	private String actualGst;
	private String actualConvenieceFee;
	//Added by kousik ==>> for detail data
	
	private String paymentStartDate;
	private String paymentEndDate;
	private String fiscalYear;
	
	public SchoolPaymentDetailsVo(String payment) {
		this.payment = payment;
	}

	public SchoolPaymentDetailsVo() {
		super();
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getAcademicYear() {
		return academicYear;
	}
	public void setAcademicYear(String academicYear) {
		this.academicYear = academicYear;
	}
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public String getAdmissionForClass() {
		return admissionForClass;
	}

	public void setAdmissionForClass(String admissionForClass) {
		this.admissionForClass = admissionForClass;
	}

	public String getPaymentOn() {
		return paymentOn;
	}

	public void setPaymentOn(String paymentOn) {
		this.paymentOn = paymentOn;
	}

	public String getPayment() {
		return payment;
	}
	public void setPayment(String payment) {
		this.payment = payment;
	}
	public String getSchoolId() {
		return schoolId;
	}
	public void setSchoolId(String schoolId) {
		this.schoolId = schoolId;
	}
	
	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
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
	

	public String getPaymentStartDate() {
		return paymentStartDate;
	}

	public String getPaymentEndDate() {
		return paymentEndDate;
	}

	public String getFiscalYear() {
		return fiscalYear;
	}

	public void setPaymentStartDate(String paymentStartDate) {
		this.paymentStartDate = paymentStartDate;
	}

	public void setPaymentEndDate(String paymentEndDate) {
		this.paymentEndDate = paymentEndDate;
	}

	public void setFiscalYear(String fiscalYear) {
		this.fiscalYear = fiscalYear;
	}

	public String getActualGst() {
		return actualGst;
	}

	public String getActualConvenieceFee() {
		return actualConvenieceFee;
	}

	public void setActualGst(String actualGst) {
		this.actualGst = actualGst;
	}

	public void setActualConvenieceFee(String actualConvenieceFee) {
		this.actualConvenieceFee = actualConvenieceFee;
	}

	@Override
	public String toString() {
		return "SchoolPaymentDetailsVo [id=" + id + ", academicYear=" + academicYear + ", studentName=" + studentName
				+ ", admissionForClass=" + admissionForClass + ", paymentOn=" + paymentOn + ", payment=" + payment
				+ ", schoolId=" + schoolId + ", studentId=" + studentId + ", OrderByColName=" + OrderByColName
				+ ", OrderBy=" + OrderBy + ", page=" + page + ", size=" + size + "]";
	}

}
