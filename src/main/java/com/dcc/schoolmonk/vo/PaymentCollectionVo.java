package com.dcc.schoolmonk.vo;

import java.math.BigInteger;

import javax.persistence.Entity;
import javax.persistence.EntityResult;
import javax.persistence.FieldResult;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SqlResultSetMapping;

@Entity
@SqlResultSetMapping(name = "PaymentCollectionVo", entities = {

		@EntityResult(entityClass = com.dcc.schoolmonk.vo.PaymentCollectionVo.class, fields = {
				// @FieldResult(name = "schoolId", column = "schoolId"),
				// @FieldResult(name = "id", column = "id"),
				// @FieldResult(name = "contactEmail", column = "contactEmail"),
				//@FieldResult(name = "schoolPrincipalName", column = "schoolPrincipalName"),
				// @FieldResult(name = "schoolName", column = "schoolName"),
				// @FieldResult(name = "contactPhone", column = "contactPhone"),

				@FieldResult(name = "payment", column = "payment")

		}) })

public class PaymentCollectionVo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	//private String schoolPrincipalName;
	// private String contactEmail;
	private Double payment;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Double getPayment() {
		return payment;
	}

	public void setPayment(Double payment) {
		this.payment = payment;
	}

	@Override
	public String toString() {
		return "PaymentCollectionVo [id=" + id + ", payment=" + payment + "]";
	}

	

	// public String getContactEmail() {
	// return contactEmail;
	// }
	//
	// public void setContactEmail(String contactEmail) {
	// this.contactEmail = contactEmail;
	// }

	// public BigInteger getSchoolId() {
	// return schoolId;
	// }
	//
	// public void setSchoolId(BigInteger schoolId) {
	// this.schoolId = schoolId;
	// }

	/*public String getSchoolPrincipalName() {
		return schoolPrincipalName;
	}

	public void setSchoolPrincipalName(String schoolPrincipalName) {
		this.schoolPrincipalName = schoolPrincipalName;
	}*/

	/*
	 * 
	 * 
	 * 
	 * 
	 * private String schoolName;
	 * 
	 * private String contactPhone;
	 * 
	 * 
	 * 
	 * public Long getId() { return id; }
	 * 
	 * public void setId(Long id) { this.id = id; }
	 * 
	 * public String getContactEmail() { return contactEmail; }
	 * 
	 * public void setContactEmail(String contactEmail) { this.contactEmail =
	 * contactEmail; }
	 * 
	 *
	 * 
	 * public String getSchoolName() { return schoolName; }
	 * 
	 * public void setSchoolName(String schoolName) { this.schoolName =
	 * schoolName; }
	 * 
	 * public String getContactPhone() { return contactPhone; }
	 * 
	 * public void setContactPhone(String contactPhone) { this.contactPhone =
	 * contactPhone; }
	 * 
	 * public String getPayment() { return payment; }
	 * 
	 * public void setPayment(String payment) { this.payment = payment; }
	 */

	/**
	 * // @Column(name = "Contact_Email")
	 * 
	 * 
	 * // @Column(name = "School_Principal_Name")
	 * 
	 * 
	 * // @Column(name = "School_Name")
	 * 
	 * 
	 * // @Column(name = "Contact_Phone")
	 * 
	 * 
	 * // @Column(name = "School_Id")
	 * 
	 * 
	 * // @Column(name = "Payment")
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * public String getSchoolId() { return schoolId; }
	 * 
	 * public void setSchoolId(String schoolId) { this.schoolId = schoolId; }
	 * 
	 **/

}
