package com.dcc.schoolmonk.vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "ASSOCIATE_SUBSCRIPTION")
@EntityListeners(AuditingEntityListener.class)
public class SubscriptionAssociatedVo {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "Subscription_Validity_Type")
	private String subscriptionValidityType;
	
	@Column(name = "Subscription_Validity")
	private String subscriptionValidity;
	
	@Column(name = "Subscription_Price")
	private String subscriptionPrice;
	
	@Column(name = "Subscription_Discount")
	private String subscriptionDiscount;
	
	@Column(name = "Student_Admission_Range")
	private String studentAdmissionRange;
	
	@ManyToOne( fetch = FetchType.EAGER )
    @JoinColumn(name = "Subscription_Id", nullable = false )
    @JsonBackReference
	private SubcriptionMstVo subcriptionMstVo;

	public Long getId() {
		return id;
	}

	public String getSubscriptionValidityType() {
		return subscriptionValidityType;
	}

	public String getSubscriptionValidity() {
		return subscriptionValidity;
	}

	public String getSubscriptionPrice() {
		return subscriptionPrice;
	}

	public String getSubscriptionDiscount() {
		return subscriptionDiscount;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setSubscriptionValidityType(String subscriptionValidityType) {
		this.subscriptionValidityType = subscriptionValidityType;
	}

	public void setSubscriptionValidity(String subscriptionValidity) {
		this.subscriptionValidity = subscriptionValidity;
	}

	public void setSubscriptionPrice(String subscriptionPrice) {
		this.subscriptionPrice = subscriptionPrice;
	}

	public void setSubscriptionDiscount(String subscriptionDiscount) {
		this.subscriptionDiscount = subscriptionDiscount;
	}

	public String getStudentAdmissionRange() {
		return studentAdmissionRange;
	}

	public SubcriptionMstVo getSubcriptionMstVo() {
		return subcriptionMstVo;
	}

	public void setStudentAdmissionRange(String studentAdmissionRange) {
		this.studentAdmissionRange = studentAdmissionRange;
	}

	public void setSubcriptionMstVo(SubcriptionMstVo subcriptionMstVo) {
		this.subcriptionMstVo = subcriptionMstVo;
	}

	@Override
	public String toString() {
		return "SubscriptionAssociatedVo [id=" + id + ", subscriptionValidityType=" + subscriptionValidityType
				+ ", subscriptionValidity=" + subscriptionValidity + ", subscriptionPrice=" + subscriptionPrice
				+ ", subscriptionDiscount=" + subscriptionDiscount + "]";
	}
	
	
	
}
