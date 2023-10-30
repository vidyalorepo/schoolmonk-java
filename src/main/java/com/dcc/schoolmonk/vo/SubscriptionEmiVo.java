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
@Table(name = "SUBSCRIPTION_EMI")
@EntityListeners(AuditingEntityListener.class)
public class SubscriptionEmiVo {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "EMI_PLAN_NAME")
	private String emiPlanName;
	
	@Column(name = "EMI_TYPE")
	private String emiType;
	
	@Column(name = "NEXT_EMI_DATE")
	private String nextEmiDate;
	
	@Column(name = "EMI_PRICE")
	private String emiPrice;
	
	@Column(name = "EMI_PAYMENT_ON")
	private String emiPaymentOn;
	
	@Column(name = "EMI_PAYMENT_STATUS")
	private String emiPaymentStatus;
	
	@Column(name = "EMI_REMINDER_DATE")
	private String emiReminderDate; // Remind user if emi validity end soon 
	
	@Column(name = "EMI_NOTIFICATION_MODE")
	private String emiNotificationMode;
	
	@ManyToOne( fetch = FetchType.EAGER )
    @JoinColumn(name = "UserSubcriptionLog_Id", nullable = false )
    @JsonBackReference
	private UserSubcriptionLogVo userSubcriptionLogVo;

	public Long getId() {
		return id;
	}

	public String getEmiPlanName() {
		return emiPlanName;
	}

	public String getEmiType() {
		return emiType;
	}

	public String getNextEmiDate() {
		return nextEmiDate;
	}

	public String getEmiPrice() {
		return emiPrice;
	}

	public String getEmiPaymentOn() {
		return emiPaymentOn;
	}

	public String getEmiPaymentStatus() {
		return emiPaymentStatus;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setEmiPlanName(String emiPlanName) {
		this.emiPlanName = emiPlanName;
	}

	public void setEmiType(String emiType) {
		this.emiType = emiType;
	}

	public void setNextEmiDate(String nextEmiDate) {
		this.nextEmiDate = nextEmiDate;
	}

	public void setEmiPrice(String emiPrice) {
		this.emiPrice = emiPrice;
	}

	public void setEmiPaymentOn(String emiPaymentOn) {
		this.emiPaymentOn = emiPaymentOn;
	}

	public void setEmiPaymentStatus(String emiPaymentStatus) {
		this.emiPaymentStatus = emiPaymentStatus;
	}

	public UserSubcriptionLogVo getUserSubcriptionLogVo() {
		return userSubcriptionLogVo;
	}

	public void setUserSubcriptionLogVo(UserSubcriptionLogVo userSubcriptionLogVo) {
		this.userSubcriptionLogVo = userSubcriptionLogVo;
	}

	public String getEmiReminderDate() {
		return emiReminderDate;
	}

	public String getEmiNotificationMode() {
		return emiNotificationMode;
	}

	public void setEmiReminderDate(String emiReminderDate) {
		this.emiReminderDate = emiReminderDate;
	}

	public void setEmiNotificationMode(String emiNotificationMode) {
		this.emiNotificationMode = emiNotificationMode;
	}

	@Override
	public String toString() {
		return "SubscriptionEmiVo [id=" + id + ", emiPlanName=" + emiPlanName + ", emiType=" + emiType
				+ ", nextEmiDate=" + nextEmiDate + ", emiPrice=" + emiPrice + ", emiPaymentOn=" + emiPaymentOn
				+ ", emiPaymentStatus=" + emiPaymentStatus + "]";
	}
	
	

}
