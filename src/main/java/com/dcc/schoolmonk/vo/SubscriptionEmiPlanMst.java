package com.dcc.schoolmonk.vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "SUBSCRIPTION_EMI_MST")
@EntityListeners(AuditingEntityListener.class)
public class SubscriptionEmiPlanMst {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "EMI_PLAN_TAG")
	private String emiPlanTag;
	
	@Column(name = "EMI_PLAN_TYPE")
	private String emiPlanType;
	
	@Column(name = "NO_OF_MONTHS")
	private String noOfMonths;
	
	@Column(name = "EMI_INTERESTS")
	private String emiInterest;
	
	@Column(name = "EMI_CALCULATION_FORMULA")
	private String emiCalculationFormula;
	
	@Column(name = "EMI_PLAN_DISCOUNT")
	private String emiPlanDiscount;

	public Long getId() {
		return id;
	}

	public String getEmiPlanTag() {
		return emiPlanTag;
	}

	public String getEmiPlanType() {
		return emiPlanType;
	}

	public String getNoOfMonths() {
		return noOfMonths;
	}

	public String getEmiInterest() {
		return emiInterest;
	}

	public String getEmiCalculationFormula() {
		return emiCalculationFormula;
	}

	public String getEmiPlanDiscount() {
		return emiPlanDiscount;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setEmiPlanTag(String emiPlanTag) {
		this.emiPlanTag = emiPlanTag;
	}

	public void setEmiPlanType(String emiPlanType) {
		this.emiPlanType = emiPlanType;
	}

	public void setNoOfMonths(String noOfMonths) {
		this.noOfMonths = noOfMonths;
	}

	public void setEmiInterest(String emiInterest) {
		this.emiInterest = emiInterest;
	}

	public void setEmiCalculationFormula(String emiCalculationFormula) {
		this.emiCalculationFormula = emiCalculationFormula;
	}

	public void setEmiPlanDiscount(String emiPlanDiscount) {
		this.emiPlanDiscount = emiPlanDiscount;
	}

	@Override
	public String toString() {
		return "SubscriptionEmiPlanMst [id=" + id + ", emiPlanTag=" + emiPlanTag + ", emiPlanType=" + emiPlanType
				+ ", noOfMonths=" + noOfMonths + ", emiInterest=" + emiInterest + ", emiCalculationFormula="
				+ emiCalculationFormula + ", emiPlanDiscount=" + emiPlanDiscount + "]";
	}
	
	
	
}
